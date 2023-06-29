package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.ClearPoemModel
import data.Poem
import data.PoemStorage
import data.PoemValidate
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.moderator.ModeratorMenu

class EditVerse : Chain(OnCallbackDataGotten("editVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val poem = Poem(mStates.state(updating))
        return  if (poem.map(PoemValidate())) {
            PoemStorage.Base.Instance().updatePoem(poem)
            ClearPoemModel.Base(mStates, updating).clear()
            listOf(
                AnswerToCallback(mKey, "Стих отредактирован!", true),
                DeleteMessage(mKey, updating),
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
        } else {
            listOf(
                AnswerToCallback(mKey, "Некорректные данные!", true)
            )
        }
    }
}