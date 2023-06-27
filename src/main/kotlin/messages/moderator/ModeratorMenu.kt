package messages.moderator

import commons.BackButton
import core.Updating
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import sModeratorText
import translations.domain.ContextString.Base.Strings

interface ModeratorMenu {

    fun message() : Executable

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