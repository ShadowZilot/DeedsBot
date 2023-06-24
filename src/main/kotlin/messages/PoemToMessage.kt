package messages

import core.Updating
import data.Poem
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sAnotherPoemLabel
import sSeeInSource
import sSeeProofLabel
import translations.domain.ContextString.Base.Strings

class PoemToMessage(
    private val mKey: String,
    private val mUserLanguageCode: Updating,
    private val mIsEdit: Boolean,
    private val mOnId: (id: Int) -> Unit = {},
) : Poem.Mapper<Executable> {

    override fun map(
        id: Int,
        categoryCode: Int,
        langCode: String,
        tag: String,
        bibleLang: String,
        bibleLangCode: Int,
        text: String,
        linkToProof: String
    ) : Executable {
        val textMessage = buildString {
            appendLine("_${ToMarkdownSupported.Base(text.trim()).convertedString()}_")
        }
        val keyboard = InlineKeyboardMarkup(
            listOf(
                InlineButton(
                    Strings().string(sSeeProofLabel, mUserLanguageCode),
                    mUrl = linkToProof
                ),
                InlineButton(
                    Strings().string(sSeeInSource, mUserLanguageCode),
                    mUrl = "https://www.bible.com/bible/$bibleLangCode/$tag.$bibleLang"
                ),
                InlineButton(
                    Strings().string(sAnotherPoemLabel, mUserLanguageCode),
                    mCallbackData = "anotherPoem"
                )
            ).convertToVertical()
        )
        return if (mIsEdit) {
            EditTextMessage(
                mKey,
                textMessage,
                -1,
                keyboard
            )
        } else {
            SendMessage(
                mKey,
                textMessage,
                keyboard
            )
        }
    }
}