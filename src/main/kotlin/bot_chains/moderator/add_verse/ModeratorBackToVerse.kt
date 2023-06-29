package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.moderator.PoemContentManageMessage
import updating.UserIdUpdating

class ModeratorBackToVerse : Chain(OnCallbackGotten("moderatorBackToVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            deleteValue("waitForString")
            deleteValue("waitForInt")
            deleteValue("waitForImage")
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
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