package bot_chains.tell_miracle

import chain.Chain
import commons.BackButton
import core.Updating
import core.storage.Storages
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.CommandEvent
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sTellMiracleButtonLabel
import sTellMiracleMessage
import translations.domain.ContextString.Base.Strings
import updating.UpdatingChatId

class TellMiracleChain : Chain(CommandEvent("/tell_story")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
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
                Strings().string(sTellMiracleMessage, updating),
                InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            Strings().string(sTellMiracleButtonLabel, updating),
                            mUrl = Storages.Main.Provider().stConfig.configValueString("miracleOperator")
                        ),
                        BackButton.Base(
                            "backToMainMenu",
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