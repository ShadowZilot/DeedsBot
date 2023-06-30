package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import executables.DeleteMessage
import executables.Executable
import handlers.OnTextGotten
import messages.moderator.PoemContentManageMessage
import staging.safetyBoolean
import staging.safetyString
import updating.UpdatingMessage
import updating.UserIdUpdating

class EndEnterIntField : Chain(OnTextGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyString("waitForInt") != "") {
            val input = updating.map(UpdatingMessage())
            val key = mStates.state(updating).string("waitForInt")
            mStates.state(updating).editor(mStates).apply {
                putInt(key, input.toInt())
                deleteValue("waitForInt")
            }.commit()
            listOf(
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
                        mStates.state(updating).safetyBoolean("isEditPoem")
                    ) {
                        mStates.state(updating).editor(mStates).apply {
                            putInt("mainMessageId", it)
                        }.commit()
                    }
                )
            )
        } else {
            emptyList()
        }
    }
}