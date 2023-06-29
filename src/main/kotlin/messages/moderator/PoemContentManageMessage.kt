package messages.moderator

import commons.BackButton
import commons.EmptyButton
import commons.EmptyField
import core.Updating
import data.CategoryStorage
import data.Poem
import executables.Executable
import executables.SendMessage
import executables.SendPhoto
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import sAddVerseHelperLabel
import sBibleLangCodeField
import sBibleLangLabelField
import sCategoryLabelField
import sImageSourceLabelField
import sLinkToProofLabelField
import sPoemTextLabelField
import sSubmitLabel
import sVerseTagLabelField
import translations.domain.ContextString.Base.Strings
import updating.UpdatingLanguageCode

class PoemContentManageMessage(
    private val mKey: String,
    private val mLanguage: Updating,
    private val mIsEditVerse: Boolean,
    private val mOnId: (id: Int) -> Unit = {}
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
        imageSource: String
    ): Executable {
        val message = buildString {
            appendLine(Strings().string(sAddVerseHelperLabel, mLanguage))
            appendLine()
            appendLine(EmptyField.Base(
                "\\*${Strings().string(sCategoryLabelField, mLanguage)}",
                CategoryStorage.Base.Instance().categoryName(mLanguage.map(UpdatingLanguageCode()), categoryCode),
                ""
            ).fieldLabel())
            appendLine(
                EmptyField.Base(
                    "\\*${Strings().string(sVerseTagLabelField, mLanguage)}",
                    tag,
                    ""
                ).fieldLabel()
            )
            appendLine(
                EmptyField.Base(
                    "\\*${Strings().string(sBibleLangLabelField, mLanguage)}",
                    bibleLang,
                    ""
                ).fieldLabel()
            )
            appendLine(
                EmptyField.Base(
                    "\\*${Strings().string(sBibleLangCodeField, mLanguage)}",
                    bibleLangCode,
                    -1
                ).fieldLabel()
            )
            appendLine(
                EmptyField.Base(
                    "\\*${Strings().string(sPoemTextLabelField, mLanguage)}",
                    text,
                    ""
                ).fieldLabel()
            )
            appendLine(
                EmptyField.Base(
                    Strings().string(sLinkToProofLabelField, mLanguage),
                    linkToProof,
                    ""
                ).fieldLabel()
            )
        }
        val keyboard = InlineKeyboardMarkup(
            listOf(
                listOf(
                    EmptyButton.NumberButton(
                        Strings().string(sCategoryLabelField, mLanguage),
                        categoryCode,
                        "inputCategory"
                    ).button()
                ),
                listOf(
                    EmptyButton.TextButton(
                        Strings().string(sVerseTagLabelField, mLanguage),
                        tag,
                        "inputString=tag"
                    ).button(),
                    EmptyButton.TextButton(
                        Strings().string(sBibleLangLabelField, mLanguage),
                        bibleLang,
                        "inputString=bible_lang"
                    ).button()
                ),
                listOf(
                    EmptyButton.NumberButton(
                        Strings().string(sBibleLangCodeField, mLanguage),
                        bibleLangCode,
                        "inputNumber=bible_lang_code"
                    ).button(),
                    EmptyButton.TextButton(
                        Strings().string(sPoemTextLabelField, mLanguage),
                        text,
                        "inputString=poem_text"
                    ).button()
                ),
                listOf(
                    EmptyButton.TextButton(
                        Strings().string(sLinkToProofLabelField, mLanguage),
                        linkToProof,
                        "inputString=link_to_proof"
                    ).button(),
                    EmptyButton.TextButton(
                        Strings().string(sImageSourceLabelField, mLanguage),
                        imageSource,
                        "inputImage"
                    ).button()
                ),
                listOf(
                    InlineButton(
                        Strings().string(sSubmitLabel, mLanguage),
                        mCallbackData = if (mIsEditVerse) "editVerse=$id" else "submitAddVerse"
                    )
                ),
                listOf(
                    BackButton.Base(
                        "backToModeratorMenu",
                        mLanguage
                    ).button()
                )
            )
        )
        return if (imageSource.isEmpty()) {
            SendMessage(
                mKey,
                message,
                keyboard,
                mOnMessageIdGotten = mOnId
            )
        } else {
            SendPhoto(
                mKey,
                message,
                imageSource,
                mReplyMarkup = keyboard,
                mListener = { messageId, _ ->
                    mOnId.invoke(messageId)
                }
            )
        }
    }
}