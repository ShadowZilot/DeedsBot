package bot_chains.contact

import chain.Chain
import commons.BackButton
import core.Updating
import executables.DeleteMessage
import executables.EditTextMessage
import executables.Executable
import handlers.OnTextGotten
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sLabelFormBeforeSubmit
import sSubmitLabel
import staging.safetyBoolean
import staging.safetyString
import translations.domain.ContextString
import translations.domain.ContextString.Base.Strings
import updating.UpdatingMessage

class FinalForm : Chain(OnTextGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("waitForThirdContact")) {
            val contact = updating.map(UpdatingMessage())
            mStates.state(updating).editor(mStates).apply {
                deleteValue("waitForThirdContact")
                putString("contact", contact)
            }.commit()
            val state = mStates.state(updating)
            val name = state.string("name")
            val reason = state.string("reason")
            listOf(
                DeleteMessage(mKey, updating),
                EditTextMessage(
                    mKey,
                    Strings().string(
                        sLabelFormBeforeSubmit, updating,
                        ToMarkdownSupported.Base(name).convertedString(),
                        ToMarkdownSupported.Base(reason).convertedString(),
                        ToMarkdownSupported.Base(contact).convertedString()
                    ),
                    mStates.state(updating).int("mainMessageId").toLong(),
                    InlineKeyboardMarkup(
                        listOf(
                            InlineButton(
                                Strings().string(sSubmitLabel, updating),
                                mCallbackData = "submitRequestForm"
                            ),
                            BackButton.Base(
                                mStates.state(updating).safetyString("contactBackParameter"),
                                updating
                            ).button()
                        ).convertToVertical()
                    )
                )
            )
        } else {
            emptyList()
        }
    }
}