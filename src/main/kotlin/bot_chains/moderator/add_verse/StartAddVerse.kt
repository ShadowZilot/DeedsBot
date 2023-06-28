package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten

class StartAddVerse : Chain(OnCallbackGotten("addVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),

        )
    }
}