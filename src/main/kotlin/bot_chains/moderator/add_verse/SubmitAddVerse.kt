package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.*
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.moderator.ModeratorMenu

class SubmitAddVerse : Chain(OnCallbackGotten("submitAddVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val poem = Poem(mStates.state(updating))
        return  if (poem.map(PoemValidate())) {
            val poemIsExist = poem.map(DoesPoemExists(mKey))
            if (poemIsExist.first) {
                listOf(
                    poemIsExist.second
                )
            } else {
                PoemStorage.Base.Instance().insertPoem(
                    poem
                )
                ClearPoemModel.Base(mStates, updating)
                listOf(
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
        } else {
            listOf(
                AnswerToCallback(mKey, "Заполните все поля!", true)
            )
        }
    }
}