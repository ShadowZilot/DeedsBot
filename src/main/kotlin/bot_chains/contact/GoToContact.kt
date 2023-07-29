package bot_chains.contact

import chain.Chain
import commons.ClearFormData
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import messages.ContactMenu

class GoToContact : Chain(OnCallbackGotten("contact")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        ClearFormData.Base(updating, mStates).clearForm()
        mStates.state(updating).editor(mStates).apply {
            putString("contactBackParameter", "contact")
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            ContactMenu.Base(
                mKey,
                true
            ).message(updating)
        )
    }
}