package bot_chains.contact

import chain.Chain
import commons.BackButton
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sLabelForFirstContactStatus
import staging.safetyString
import translations.domain.ContextString.Base.Strings

class BeginFillContactForm : Chain(OnCallbackGotten("contactRequest")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("waitForFirstContact", true)
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            EditTextMessage(
                mKey,
                Strings().string(sLabelForFirstContactStatus, updating),
                -1,
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
    }
}