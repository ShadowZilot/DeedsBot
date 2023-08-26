package bot_chains.random_poem

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.PoemToMessage
import staging.NotFoundStateValue
import updating.UpdatingLanguageCode

class RandomPoemChain : Chain(OnCallbackGotten("randomPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val categoryCode = try {
            mStates.state(updating).int("verseCategory")
        } catch (e: NotFoundStateValue) {
            -1
        }
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            if (categoryCode != -1) {
                PoemStorage.Base.Instance().randomPoem(
                    updating.map(UpdatingLanguageCode()),
                    categoryCode
                ).map(
                    PoemToMessage(
                        mKey,
                        updating,
                        categoryCode
                    ) {
                        mStates.state(updating).editor(mStates).apply {
                            putInt("mainMessageId", it)
                        }.commit()
                    }
                )
            } else {
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
            }
        )
    }
}