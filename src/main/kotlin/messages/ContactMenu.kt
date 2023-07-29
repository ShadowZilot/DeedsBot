package messages

import commons.BackButton
import core.Updating
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sBibleLearnLabel
import sContactByRequestLabel
import sContactTitle
import translations.domain.ContextString.Base.Strings

interface ContactMenu {

    fun message(updating: Updating) : Executable

    class Base(
        private val mKey: String,
        private val mIsEdit: Boolean,
        private val mOnId: (id: Int) -> Unit = {}
    ) : ContactMenu {

        override fun message(updating: Updating): Executable {
            val text = Strings().string(sContactTitle, updating)
            val keyboard = InlineKeyboardMarkup(
                listOf(
                    InlineButton(
                        Strings().string(sBibleLearnLabel, updating),
                        mCallbackData = "townList"
                    ),
                    InlineButton(
                        Strings().string(sContactByRequestLabel, updating),
                        mCallbackData = "contactRequest"
                    ),
                    BackButton.Base("backToMainMenu", updating).button()
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
                    keyboard
                ) {
                    mOnId.invoke(it)
                }
            }
        }
    }
}