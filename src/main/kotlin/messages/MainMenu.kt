package messages

import core.Updating
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sPoemCategoryLabel
import sRandomPoemLabel
import sStartLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingFirstName

interface MainMenu {

    fun message(updating: Updating): Executable

    class Base(
        private val mKey: String,
        private val mIsEdit: Boolean = true,
        private val mOnId: (id: Int) -> Unit = {}
    ) : MainMenu {

        override fun message(updating: Updating): Executable {
            val text = Strings().string(
                sStartLabel, updating,
                ToMarkdownSupported.Base(updating.map(UpdatingFirstName())).convertedString()
            )
            val keyboard = InlineKeyboardMarkup(
                listOf(
                    InlineButton(
                        Strings().string(sRandomPoemLabel, updating),
                        mCallbackData = "randomPoem"
                    ),
                    InlineButton(
                        Strings().string(sPoemCategoryLabel, updating),
                        mCallbackData = "poemCategories"
                    )
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