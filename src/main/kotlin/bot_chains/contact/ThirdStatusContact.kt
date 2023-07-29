package bot_chains.contact

import chain.Chain
import commons.BackButton
import core.Updating
import executables.DeleteMessage
import executables.EditTextMessage
import executables.Executable
import handlers.OnTextGotten
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sLabelForThirdContactStatus
import staging.safetyBoolean
import staging.safetyString
import translations.domain.ContextString
import updating.UpdatingMessage

class ThirdStatusContact : Chain(OnTextGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("waitForSecondContact")) {
            val reason = updating.map(UpdatingMessage())
            mStates.state(updating).editor(mStates).apply {
                deleteValue("waitForSecondContact")
                putString("reason", reason)
                putBoolean("waitForThirdContact", true)
            }.commit()
            listOf(
                DeleteMessage(mKey, updating),
                EditTextMessage(
                    mKey,
                    ContextString.Base.Strings().string(sLabelForThirdContactStatus, updating),
                    mStates.state(updating).int("mainMessageId").toLong(),
                    InlineKeyboardMarkup(
                        listOf(
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