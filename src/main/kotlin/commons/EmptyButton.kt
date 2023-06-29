package commons

import keyboard_markup.InlineButton

interface EmptyButton {

    fun button() : InlineButton

    class TextButton(
        private val mButtonText: String,
        private val mValue: String,
        private val mParameter: String,
        private val mDefaultValue: String = "",
    ) : EmptyButton {

        override fun button(): InlineButton {
            return InlineButton(
                "$mButtonText ${if (mValue == mDefaultValue) "❌" else "✅"}",
                mCallbackData = mParameter
            )
        }
    }

    class NumberButton(
        private val mButtonText: String,
        private val mValue: Int,
        private val mParameter: String,
        private val mDefaultValue: Int = -1,
    ) : EmptyButton {

        override fun button(): InlineButton {
            return InlineButton(
                "$mButtonText ${if (mValue == mDefaultValue) "❌" else "✅"}",
                mCallbackData = mParameter
            )
        }
    }
}