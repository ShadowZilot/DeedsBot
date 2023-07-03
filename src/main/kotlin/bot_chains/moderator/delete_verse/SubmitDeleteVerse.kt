package bot_chains.moderator.delete_verse

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.moderator.ModeratorMenu
import sVerseDeletedMessage
import translations.domain.ContextString.Base.Strings
import updating.UpdatingCallbackInt

class SubmitDeleteVerse : Chain(OnCallbackDataGotten("submitVerseDelete")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val verseId = updating.map(UpdatingCallbackInt("submitVerseDelete"))
        PoemStorage.Base.Instance().deletePoem(verseId)
        return listOf(
            AnswerToCallback(mKey, Strings().string(sVerseDeletedMessage, updating), true),
            ModeratorMenu.Base(
                mKey,
                updating,
                false
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message()
        )
    }
}