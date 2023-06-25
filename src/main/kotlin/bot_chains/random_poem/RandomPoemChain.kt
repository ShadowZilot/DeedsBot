package bot_chains.random_poem

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import handlers.OnTextGotten
import messages.PoemToMessage
import sRandomPoemLabel
import translations.domain.ContextString
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode
import updating.UpdatingMessage

class RandomPoemChain : Chain(OnCallbackGotten("randomPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            PoemStorage.Base.Instance().randomPoem(
                updating.map(UpdatingLanguageCode())
            ).map(
                PoemToMessage(
                    mKey,
                    updating
                ) {
                    mStates.state(updating).editor(mStates).apply {
                        putInt("mainMessageId", it)
                    }
                }
            )
        )
    }
}