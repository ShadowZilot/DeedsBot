package messages

import commons.BackButton
import core.Updating
import data.TownsStorage
import executables.EditTextMessage
import executables.Executable
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import sBibleLearnTitle
import translations.domain.ContextString.Base.Strings

interface TownListMessage {

    fun message(updating: Updating) : Executable

    class Base(
        private val mKey: String,
        private val mReturnParameter: String
    ) : TownListMessage {

        override fun message(updating: Updating): Executable {
            val text = Strings().string(sBibleLearnTitle, updating)
            val keyboard = InlineKeyboardMarkup(
                mutableListOf<KeyboardButton>().apply {
                    addAll(
                        TownsStorage.Base.Instance().towns().map {
                            it.map(TownToButton())
                        }
                    )
                    add(BackButton.Base(mReturnParameter, updating).button())
                }.convertToVertical()
            )
            return EditTextMessage(
                mKey,
                text,
                -1,
                keyboard
            )
        }
    }
}