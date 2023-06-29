package bot_chains.moderator.add_verse

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
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import sSelectCategoryLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode

class StartInputCategory : Chain(OnCallbackGotten("inputCategory")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val keyboard = InlineKeyboardMarkup(
            mutableListOf<List<KeyboardButton>>().apply {
                val categories = CategoryStorage.Base.Instance().categoriesByLanguage(
                    updating.map(UpdatingLanguageCode())
                )
                val tmp = mutableListOf<InlineButton>()
                for (i in categories.indices) {
                    tmp.add(
                        categories[i].map(
                            CategoryToButton(
                                "addCategory"
                            )
                        )
                    )
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
                        BackButton.Base(
                            "moderatorBackToVerse",
                            updating
                        ).button()
                    )
                )
            }
        )
        return listOf(
            AnswerToCallback(mKey),
            DeleteMessage(
                mKey,
                updating
            ),
            SendMessage(
                mKey,
                buildString {
                    appendLine(Strings().string(sSelectCategoryLabel, updating))
                },
                keyboard
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putInt("mainMessageId", it)
                }.commit()
            }
        )
    }
}