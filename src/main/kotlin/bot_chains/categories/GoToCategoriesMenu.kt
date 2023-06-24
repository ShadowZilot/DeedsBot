package bot_chains.categories

import chain.Chain
import core.Updating
import data.CategoryStorage
import data.CategoryToButton
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import sBackLabel
import sSelectCategoryLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode

class GoToCategoriesMenu : Chain(OnCallbackGotten("poemCategories")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val keyboard = InlineKeyboardMarkup(
            mutableListOf<List<KeyboardButton>>().apply {
                val categories = CategoryStorage.Base.Instance().categoriesByLanguage(
                    updating.map(UpdatingLanguageCode())
                )
                val tmp = mutableListOf<InlineButton>()
                for (i in categories.indices) {
                    tmp.add(categories[i].map(CategoryToButton()))
                    if (i % 2 != 0) {
                        add(tmp.toList())
                        tmp.clear()
                    }
                }
                if (categories.size % 2 != 0) {
                    add(tmp.toList())
                    tmp.clear()
                }
                add(
                    listOf(
                        InlineButton(
                            Strings().string(sBackLabel, updating),
                            mCallbackData = "anotherPoem"
                        )
                    )
                )
            }
        )
        return listOf(
            AnswerToCallback(mKey),
            EditTextMessage(
                mKey,
                Strings().string(sSelectCategoryLabel, updating),
                -1,
                keyboard
            )
        )
    }
}