package messages.moderator

import commons.BackButton
import core.Updating
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.InlineModeQuery
import sAddVerseLabel
import sModeratorText
import sSearchVersesLabel
import translations.domain.ContextString.Base.Strings

interface ModeratorMenu {

    fun message(): Executable

    class Base(
        private val mKey: String,
        private val mLanguage: Updating,
        private val mIsEdit: Boolean = true,
        private val mOnId: (id: Int) -> Unit = {}
    ) : ModeratorMenu {

        override fun message(): Executable {
            val text = Strings().string(sModeratorText, mLanguage)
            val keyboard = InlineKeyboardMarkup(
                listOf(
                    InlineButton(
                        Strings().string(sSearchVersesLabel, mLanguage),
                        mInlineMode = InlineModeQuery.CurrentChat("")
                    ),
                    InlineButton(
                        "Категории",
                        mCallbackData = "moderatorCategories"
                    ),
                    InlineButton(
                        Strings().string(sAddVerseLabel, mLanguage),
                        mCallbackData = "addVerse"
                    ),
                    BackButton.Base(
                        "backToMainMenu",
                        mLanguage
                    ).button()
                ).convertToVertical()
            )
            return if (mIsEdit) {
                EditTextMessage(
                    mKey,
                    text,
                    -1,
                    keyboard
                )
            } else {
                SendMessage(
                    mKey,
                    text,
                    keyboard,
                    mOnMessageIdGotten = mOnId
                )
            }
        }
    }
}