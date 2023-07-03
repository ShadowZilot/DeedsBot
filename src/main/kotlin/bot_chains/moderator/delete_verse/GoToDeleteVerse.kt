package bot_chains.moderator.delete_verse

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackDataGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sBackLabel
import sDeleteWarningLabel
import sSubmitDeleteLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingCallbackInt

class GoToDeleteVerse : Chain(OnCallbackDataGotten("deleteVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val verseId = updating.map(UpdatingCallbackInt("deleteVerse"))
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            SendMessage(
                mKey,
                Strings().string(sDeleteWarningLabel, updating),
                InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            Strings().string(sSubmitDeleteLabel, updating),
                            mCallbackData = "submitVerseDelete=$verseId"
                        ),
                        InlineButton(
                            Strings().string(sBackLabel, updating),
                            mCallbackData = "goBackToVerse=$verseId"
                        )
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