package bot_chains.random_poem

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.PoemToMessage
import updating.UpdatingLanguageCode

class RandomPoemChain : Chain(OnCallbackGotten("randomPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            PoemStorage.Base.Instance().randomPoem(
                updating.map(UpdatingLanguageCode())
            ).map(
                PoemToMessage(
                    mKey,
                    updating
                ) {
                    mStates.state(updating).editor(mStates).apply {
                        putInt("mainMessageId", it)
                    }.commit()
                }
            )
        )
    }
}