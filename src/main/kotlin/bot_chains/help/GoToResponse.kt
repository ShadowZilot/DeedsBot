package bot_chains.help

import chain.Chain
import core.Updating
import data.QuestionStorage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.QuestionToMessage
import updating.UpdatingCallbackInt

class GoToResponse : Chain(OnCallbackDataGotten("question")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val questionId = updating.map(UpdatingCallbackInt("question"))
        val question = QuestionStorage.Base.Instance().questionById(questionId)
        return listOf(
            question.map(
                QuestionToMessage(mKey, updating)
            )
        )
    }
}