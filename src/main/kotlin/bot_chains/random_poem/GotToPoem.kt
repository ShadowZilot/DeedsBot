package bot_chains.random_poem

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.PoemToMessage
import updating.UpdatingCallbackInt

class GotToPoem : Chain(OnCallbackDataGotten("goPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val poemId = updating.map(UpdatingCallbackInt("goPoem"))
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            PoemStorage.Base.Instance().poemById(
                poemId
            ).map(
                PoemToMessage(mKey, updating) {
                    mStates.state(updating).editor(mStates).apply {
                        putInt("mainMessageId", it)
                    }.commit()
                }
            )
        )
    }
}