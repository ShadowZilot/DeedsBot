package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.ClearPoemModel
import data.Poem
import data.PoemStorage
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.moderator.ModeratorMenu

class SubmitAddVerse : Chain(OnCallbackGotten("submitAddVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        PoemStorage.Base.Instance().insertPoem(
            Poem(mStates.state(updating))
        )
        ClearPoemModel.Base(mStates, updating)
        return listOf(
            AnswerToCallback(mKey, "Стих добавлен!"),
            DeleteMessage(mKey, updating),
            ModeratorMenu.Base(
                mKey,
                updating,
                false,
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message()
        )
    }
}