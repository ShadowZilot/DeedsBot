package bot_chains.moderator.menu

import chain.Chain
import commons.ClearFormData
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackGotten
import messages.MainMenu
import updating.UpdatingChatId

class BackToMainMenu : Chain(OnCallbackGotten("backToMainMenu")) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        ClearFormData.Base(updating, mStates).clearForm()
        mStates.state(updating).editor(mStates).apply {
            deleteValue("verseCategory")
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(
                mKey,
                updating.map(UpdatingChatId()).second.toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            MainMenu.Base(mKey, false) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message(updating)
        )
    }
}