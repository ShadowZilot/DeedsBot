package bot_chains.greeting

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import messages.MainMenu

class AnotherPoem : Chain(OnCallbackGotten("anotherPoem")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            MainMenu.Base(mKey, true).message(updating)
        )
    }
}