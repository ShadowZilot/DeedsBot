package bot_chains.moderator.menu

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import messages.MainMenu

class BackToMainMenu : Chain(OnCallbackGotten("backToMainMenu")) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            MainMenu.Base(mKey, true).message(updating)
        )
    }
}