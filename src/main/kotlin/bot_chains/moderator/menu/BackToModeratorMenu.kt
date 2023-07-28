package bot_chains.moderator.menu

import chain.Chain
import core.Updating
import data.ClearPoemModel
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.moderator.ModeratorMenu
import updating.UserIdUpdating

class BackToModeratorMenu : Chain(OnCallbackGotten("backToModeratorMenu")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        ClearPoemModel.Base(mStates, updating).clear()
        mStates.state(updating).editor(mStates).apply {
            deleteValue("isEditPoem")
        }.commit()
        return listOf(
            DeleteMessage(mKey, updating),
            DeleteMessage(
                mKey,
                updating.map(UserIdUpdating()).toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            ModeratorMenu.Base(
                mKey,
                updating,
                false
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message()
        )
    }
}