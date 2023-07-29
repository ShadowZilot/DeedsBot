package bot_chains.contact

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.ContactMenu
import updating.UpdatingCallbackInt

class GoToDiscuss : Chain(OnCallbackDataGotten("goDiscuss")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val poemId = updating.map(UpdatingCallbackInt("goDiscuss"))
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            ContactMenu.Base(mKey, false, "goPoem=$poemId") {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                    putString("contactBackParameter", "goPoem=$poemId")
                }.commit()
            }.message(updating)
        )
    }
}