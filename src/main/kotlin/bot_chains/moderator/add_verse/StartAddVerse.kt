package bot_chains.moderator.add_verse

import chain.Chain
import commons.BackButton
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sEnglishLanguageLabel
import sRussianLanguageLabel
import sSelectLanguageText
import translations.domain.ContextString
import translations.domain.ContextString.Base.Strings

class StartAddVerse : Chain(OnCallbackGotten("addVerse")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(mKey),
            EditTextMessage(
                mKey,
                Strings().string(sSelectLanguageText, updating),
                -1,
                InlineKeyboardMarkup(
                    listOf(
                        listOf(
                            InlineButton(
                                Strings().string(sRussianLanguageLabel, updating),
                                mCallbackData = "langVerse=ru"
                            ),
                            InlineButton(
                                Strings().string(sEnglishLanguageLabel, updating),
                                mCallbackData = "langVerse=en"
                            )
                        ),
                        listOf(
                            BackButton.Base(
                                "backToModeratorMenu",
                                updating
                            ).button()
                        )
                    )
                )
            )
        )
    }
}