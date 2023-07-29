package bot_chains.contact

import chain.Chain
import commons.ClearFormData
import core.Updating
import core.storage.Storages
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackGotten
import helpers.ToMarkdownSupported
import messages.MainMenu
import sRequestSendNotification
import translations.domain.ContextString.Base.Strings

class SubmitForm : Chain(OnCallbackGotten("submitRequestForm")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val state = mStates.state(updating)
        val name = state.string("name")
        val reason = state.string("reason")
        val contact = state.string("contact")
        ClearFormData.Base(updating, mStates).clearForm()
        return listOf(
            AnswerToCallback(mKey, Strings().string(sRequestSendNotification, updating), true),
            DeleteMessage(mKey, updating),
            MainMenu.Base(mKey, false) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }.message(updating),
            SendMessage(
                mKey,
                buildString {
                    appendLine("*Заявка*")
                    appendLine()
                    appendLine("Имя: _${ToMarkdownSupported.Base(name).convertedString()}_")
                    appendLine("Причина: _${ToMarkdownSupported.Base(reason).convertedString()}_")
                    appendLine("Контакт: _${ToMarkdownSupported.Base(contact).convertedString()}_")
                },
                mChatId = Storages.Main.Provider().stConfig.configValueLong("requestGroup")
            )
        )
    }
}