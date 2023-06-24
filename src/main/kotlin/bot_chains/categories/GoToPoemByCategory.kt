package bot_chains.categories

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.PoemToMessage
import updating.UpdatingCallbackInt
import updating.UpdatingLanguageCode

class GoToPoemByCategory : Chain(OnCallbackDataGotten("poemByCategory")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val categoryCode = updating.map(UpdatingCallbackInt("poemByCategory"))
        return listOf(
            AnswerToCallback(mKey),
            PoemStorage.Base.Instance().randomPoem(
                updating.map(UpdatingLanguageCode()),
                categoryCode
            ).map(
                PoemToMessage(
                    mKey,
                    updating,
                    true,
                    categoryCode
                )
            )
        )
    }
}