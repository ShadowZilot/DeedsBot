package bot_chains.categories

import chain.Chain
import commons.BackButton
import core.Updating
import data.CategoryStorage
import data.CategoryToButton
import executables.AnswerToCallback
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import sSelectCategoryLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode

class GoToCategoriesMenu : Chain(OnCallbackGotten("poemCategories")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val keyboard = InlineKeyboardMarkup(
            mutableListOf<KeyboardButton>().apply {
                addAll(CategoryStorage.Base.Instance().categoriesByLanguage(
                    updating.map(UpdatingLanguageCode())
                ).map {
                    it.map(CategoryToButton())
                })
                add(
                    BackButton.Base(
                        "anotherPoem",
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
                Strings().string(sSelectCategoryLabel, updating),
                keyboard
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }
        )
    }
}