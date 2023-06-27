package bot_chains.moderator.menu

import chain.Chain
import core.Updating
import executables.DeleteMessage
import executables.Executable
import handlers.CommandEvent
import updating.UserIdUpdating

class GoToModeratorMenu : Chain(CommandEvent("/moderator")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            DeleteMessage(mKey, updating),
            DeleteMessage(
                mKey,
                updating.map(UserIdUpdating()).toString(),
                mStates.state(updating).long("mainMessageId")
            )
        )
    }
}