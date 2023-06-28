package bot_chains.moderator.add_verse

import chain.Chain
import core.Updating
import data.Poem
import data.PoemToState
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import handlers.OnCallbackDataGotten
import messages.moderator.PoemContentManageMessage
import updating.UpdatingCallbackString
import updating.UserIdUpdating

class SelectLangAddVerse : Chain(OnCallbackDataGotten("langVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val langCode = updating.map(UpdatingCallbackString("langVerse"))
        Poem(langCode).map(PoemToState(mStates, updating))
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