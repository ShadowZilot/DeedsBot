package bot_chains.greeting

import chain.Chain
import core.Updating
import executables.Executable
import handlers.CommandEvent
import messages.MainMenu

class Greeting : Chain(CommandEvent("/start")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(MainMenu.Base(
            mKey,
            false
        ) {
            mStates.state(updating).editor(mStates).apply {
                putInt("mainMessageId", it)
            }.commit()
        }.message(updating)
        )
    }
}