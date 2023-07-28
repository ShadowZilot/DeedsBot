package bot_chains.moderator.verses_list.categories

import chain.Chain
import commons.BackButton
import core.Updating
import data.CategoryStorage
import data.CategoryToButton
import data.CategoryToInlineButton
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import updating.UpdatingLanguageCode

class ModeratorGoCategories : Chain(OnCallbackGotten("moderatorCategories")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val keyboard = InlineKeyboardMarkup(
            mutableListOf<KeyboardButton>().apply {
                addAll(
                    CategoryStorage.Base.Instance().categoriesByLanguage(
                    updating.map(UpdatingLanguageCode())
                ).map {
                    it.map(CategoryToInlineButton())
                })
                add(
                    BackButton.Base(
                        "backToModeratorMenu",
                        updating
                    ).button()
                )
            }.convertToVertical()
        )
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(mKey, updating),
            SendMessage(
                mKey,
                buildString {
                    appendLine("*Стихи по категориям*")
                    appendLine()
                },
                keyboard
            )
        )
    }
}