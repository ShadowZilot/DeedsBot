package bot_chains.moderator.verses_list

import chain.Chain
import core.Updating
import data.PoemStorage
import data.PoemToState
import executables.DeleteMessage
import executables.Executable
import handlers.OnTextGotten
import messages.moderator.PoemContentManageMessage
import updating.UpdatingMessage
import updating.UserIdUpdating

class GoToVerseItem : Chain(OnTextGotten()) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        val textData = updating.map(UpdatingMessage())
        return if (textData.contains("verse=")) {
            val poemId = textData.split("=")[1].toInt()
            val poem = PoemStorage.Base.Instance().poemById(poemId)
            poem.map(PoemToState(mStates, updating))
            mStates.state(updating).editor(mStates).apply {
                putBoolean("isEditPoem", true)
            }.commit()
            listOf(
                DeleteMessage(mKey, updating),
                DeleteMessage(
                    mKey,
                    updating.map(UserIdUpdating()).toString(),
                    mStates.state(updating).int("mainMessageId").toLong()
                ),
                poem.map(
                    PoemContentManageMessage(
                        mKey,
                        updating,
                        mStates.state(updating).boolean("isEditPoem"),
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