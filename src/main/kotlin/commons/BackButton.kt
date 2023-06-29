package commons

import core.Updating
import keyboard_markup.InlineButton
import sBackLabel
import translations.domain.ContextString

interface BackButton {

    fun button() : InlineButton

    class Base(
        private val mParameter: String,
        private val mUpdating: Updating
    ) : BackButton {

        override fun button() = InlineButton(
            ContextString.Base.Strings().string(sBackLabel, mUpdating),
            mCallbackData = mParameter
        )
    }
}