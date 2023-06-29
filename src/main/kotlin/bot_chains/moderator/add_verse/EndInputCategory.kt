package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.moderator.PoemContentManageMessage
import updating.UpdatingCallbackInt

class EndInputCategory : Chain(OnCallbackDataGotten("addCategory")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val categoryCode = updating.map(UpdatingCallbackInt("addCategory"))
        mStates.state(updating).editor(mStates).apply {
            putInt("category_code", categoryCode)
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            Poem(
                mStates.state(updating)
            ).map(
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