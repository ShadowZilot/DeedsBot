package bot_chains.categories

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.PoemToMessage
import updating.UpdatingCallbackInt
import updating.UpdatingLanguageCode

class GoToPoemByCategory : Chain(OnCallbackDataGotten("poemByCategory")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val categoryCode = updating.map(UpdatingCallbackInt("poemByCategory"))
        mStates.state(updating).editor(mStates).apply {
            putInt("verseCategory", categoryCode)
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
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
        )
    }
}