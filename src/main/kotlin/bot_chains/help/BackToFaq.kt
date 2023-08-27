package bot_chains.help

import chain.Chain
import core.Updating
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.HelpQuestionsMenu
import updating.UpdatingChatId

class BackToFaq : Chain(OnCallbackGotten("backToFaq")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            DeleteMessage(mKey, updating),
            DeleteMessage(
                mKey,
                updating.map(UpdatingChatId()).second.toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            HelpQuestionsMenu.Base(
                mKey,
                updating
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message()
        )
    }
}