package messages

import commons.TagWithoutVerse
import core.Updating
import data.CategoryStorage
import data.Poem
import executables.Executable
import executables.SendMessage
import executables.SendPhoto
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import logs.Logging
import sAnotherPoemLabel
import sSeeInSource
import sSeeProofLabel
import sSelectedCategoryLabel
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode

class PoemToMessage(
    private val mKey: String,
    private val mUserLanguageCode: Updating,
    private val mSelectedCategory: Int = -1,
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
        linkToProof: String,
        imageSource: String,
        localizedTag: String
    ): Executable {
        val textMessage = buildString {
            if (mSelectedCategory != -1) {
                appendLine(
                    Strings().string(
                        sSelectedCategoryLabel,
                        mUserLanguageCode,
                        CategoryStorage.Base.Instance().categoryName(
                            mUserLanguageCode.map(UpdatingLanguageCode()), mSelectedCategory
                        )
                    )
                )
            }
            appendLine()
            appendLine(ToMarkdownSupported.Base(text.trim()).convertedString())
            if (localizedTag.isNotEmpty()) {
                appendLine()
                appendLine("_${ToMarkdownSupported.Base(localizedTag).convertedString()}_")
            }
        }
        val keyboard = InlineKeyboardMarkup(
            mutableListOf<KeyboardButton>().apply {
                if (linkToProof.isNotEmpty()) {
                    add(
                        InlineButton(
                            Strings().string(sSeeProofLabel, mUserLanguageCode),
                            mUrl = linkToProof
                        )
                    )
                }
                add(
                    InlineButton(
                        Strings().string(sSeeInSource, mUserLanguageCode),
                        mUrl = "https://www.bible.com/bible/$bibleLangCode/${
                            TagWithoutVerse.Base(tag).newTag()
                        }.$bibleLang"
                    )
                )
                add(
                    InlineButton(
                        Strings().string(sAnotherPoemLabel, mUserLanguageCode),
                        mCallbackData = "anotherPoem"
                    )
                )
            }.convertToVertical()
        )
        return if (imageSource.isEmpty()) {
            SendMessage(
                mKey,
                textMessage,
                keyboard,
                mOnMessageIdGotten = mOnId
            )
        } else {
            SendPhoto(
                mKey,
                textMessage,
                mImageId = imageSource,
                mReplyMarkup = keyboard,
                mListener = { messageId, _ ->
                    mOnId.invoke(messageId)
                }
            )
        }
    }
}