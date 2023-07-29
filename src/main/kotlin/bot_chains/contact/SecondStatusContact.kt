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
import sLabelForSecondContactStatus
import staging.safetyBoolean
import staging.safetyString
import translations.domain.ContextString.Base.Strings
import updating.UpdatingMessage

class SecondStatusContact : Chain(OnTextGotten()) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("waitForFirstContact")) {
            val name = updating.map(UpdatingMessage())
            mStates.state(updating).editor(mStates).apply {
                deleteValue("waitForFirstContact")
                putString("name", name)
                putBoolean("waitForSecondContact", true)
            }.commit()
            listOf(
                DeleteMessage(mKey, updating),
                EditTextMessage(
                    mKey,
                    Strings().string(sLabelForSecondContactStatus, updating),
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