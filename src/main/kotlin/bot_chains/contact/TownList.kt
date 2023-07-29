package bot_chains.contact

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import messages.TownListMessage

class TownList : Chain(OnCallbackGotten("townList")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            TownListMessage.Base(
                mKey,
                "contact"
            ).message(updating)
        )
    }
}