package bot_chains.moderator.add_verse

import chain.Chain
import commons.BackButton
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sInputDataLabel
import translations.domain.ContextString.Base.Strings

class StartInputImage : Chain(OnCallbackGotten("inputImage")) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("waitForImage", true)
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            SendMessage(
                mKey,
                Strings().string(sInputDataLabel, updating, "image\\_source"),
                InlineKeyboardMarkup(
                    listOf(
                        BackButton.Base("moderatorBackToVerse", updating).button()
                    ).convertToVertical()
                )
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }
        )
    }
}