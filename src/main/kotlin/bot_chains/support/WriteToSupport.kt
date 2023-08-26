package bot_chains.support

import chain.Chain
import commons.BackButton
import core.Updating
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.CommandEvent
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sLabelForFirstContactStatus
import staging.safetyString
import translations.domain.ContextString
import updating.UpdatingChatId

class WriteToSupport : Chain(CommandEvent("/support")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("waitForFirstContact", true)
            putString("contactBackParameter", "backToMainMenu")
        }.commit()
        return listOf(
            DeleteMessage(
                mKey,
                updating
            ),
            DeleteMessage(
                mKey,
                updating.map(UpdatingChatId()).second.toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            SendMessage(
                mKey,
                ContextString.Base.Strings().string(sLabelForFirstContactStatus, updating),
                InlineKeyboardMarkup(
                    listOf(
                        BackButton.Base(
                            mStates.state(updating).safetyString("contactBackParameter"),
                            updating
                        ).button()
                    ).convertToVertical()
                )
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }
        )
    }
}