package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import executables.DeleteMessage
import executables.Executable
import handlers.OnTextGotten
import messages.moderator.PoemContentManageMessage
import updating.UpdatingMessage
import updating.UserIdUpdating

class EndEnterStringField : Chain(OnTextGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val input = updating.map(UpdatingMessage())
        val key = mStates.state(updating).string("waitForString")
        mStates.state(updating).editor(mStates).apply {
            putString(key, input)
            deleteValue("waitForString")
        }.commit()
        return listOf(
            DeleteMessage(mKey, updating),
            DeleteMessage(
                mKey,
                updating.map(UserIdUpdating()).toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            Poem(mStates.state(updating)).map(
                PoemContentManageMessage(
                    mKey,
                    updating,
                    false
                ) {
                    mStates.state(updating).editor(mStates).apply {
                        putInt("mainMessageId", it)
                    }.commit()
                }
            )
        )
    }
}