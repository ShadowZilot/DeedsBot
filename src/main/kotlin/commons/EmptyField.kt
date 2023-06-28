package commons

import helpers.ToMarkdownSupported

interface EmptyField {

    fun fieldLabel(): String

    class Base(
        private val mLabel: String,
        private val mValue: Any,
        private val mDefaultValue: Any,
    ) : EmptyField {
        override fun fieldLabel(): String {
            return "$mLabel: *${
                if (mValue == mDefaultValue) {
                    ToMarkdownSupported.Base(
                        mValue.toString()
                    ).convertedString()
                } else "❌"
            }*"
        }
    }
}