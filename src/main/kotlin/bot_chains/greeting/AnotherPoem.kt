package bot_chains.greeting

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.MainMenu

class AnotherPoem : Chain(OnCallbackGotten("anotherPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(
                mKey,
                updating
            ),
            MainMenu.Base(mKey, false) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message(updating)
        )
    }
}