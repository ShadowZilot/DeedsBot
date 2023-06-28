package bot_chains.moderator.add_verse

import chain.Chain
import commons.BackButton
import core.Updating
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackDataGotten
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sInputDataLabel
import translations.domain.ContextString
import updating.UpdatingCallbackString
import updating.UserIdUpdating

class StartEnterIntField : Chain(OnCallbackDataGotten("inputNumber")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val key = updating.map(UpdatingCallbackString("inputNumber"))
        mStates.state(updating).editor(mStates).apply {
            putString("waitForInt", key)
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(
                mKey,
                updating.map(UserIdUpdating()).toString(),
                mStates.state(updating).int("mainMessageId").toLong()
            ),
            SendMessage(
                mKey,
                buildString {
                    appendLine(
                        ContextString.Base.Strings().string(
                            sInputDataLabel, updating,
                            ToMarkdownSupported.Base(key).convertedString()
                        )
                    )
                },
                InlineKeyboardMarkup(
                    listOf(
                        BackButton.Base(
                            "moderatorBackToVerse",
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