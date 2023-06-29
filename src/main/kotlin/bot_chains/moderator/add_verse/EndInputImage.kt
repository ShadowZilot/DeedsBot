package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import executables.DeleteMessage
import executables.Executable
import handlers.OnPhotoGotten
import messages.moderator.PoemContentManageMessage
import staging.safetyBoolean
import updating.UpdatingPhotoId
import updating.UserIdUpdating

class EndInputImage : Chain(OnPhotoGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("waitForImage")) {
            mStates.state(updating).editor(mStates).apply {
                putString("image_source", updating.map(UpdatingPhotoId()))
                deleteValue("waitForImage")
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
                        false,
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