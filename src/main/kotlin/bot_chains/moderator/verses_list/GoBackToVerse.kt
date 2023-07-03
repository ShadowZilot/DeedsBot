package bot_chains.moderator.verses_list

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.moderator.PoemContentManageMessage
import staging.safetyBoolean
import updating.UpdatingCallbackInt

class GoBackToVerse : Chain(OnCallbackDataGotten("goBackToVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val verseId = updating.map(UpdatingCallbackInt("goBackToVerse"))
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            PoemStorage.Base.Instance().poemById(verseId).map(
                PoemContentManageMessage(
                    mKey,
                    updating,
                    mStates.state(updating).safetyBoolean("isEditPoem")
                ) {
                    mStates.state(updating).editor(mStates).apply {
                        putInt("mainMessageId", it)
                    }.commit()
                }
            )
        )
    }
}