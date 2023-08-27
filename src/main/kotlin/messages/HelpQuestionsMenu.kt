package messages

import commons.BackButton
import core.Updating
import data.QuestionStorage
import executables.Executable
import executables.SendMessage
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup
import keyboard_markup.KeyboardButton
import sHelpTitleText
import translations.domain.ContextString

interface HelpQuestionsMenu {

    fun message(): Executable

    class Base(
        private val mKey: String,
        private val mUpdating: Updating,
        private val mOnId: (id: Int) -> Unit
    ) : HelpQuestionsMenu {

        override fun message(): Executable {
            return SendMessage(
                mKey,
                ContextString.Base.Strings().string(sHelpTitleText, mUpdating),
                InlineKeyboardMarkup(
                    mutableListOf<KeyboardButton>().apply {
                        val questions = QuestionStorage.Base.Instance().allQuestions()
                        for (i in questions.indices) {
                            add(
                                questions[i].map(QuestionToButton())
                            )
                        }
                        add(
                            BackButton.Base(
                                "backToMainMenu",
                                mUpdating
                            ).button()
                        )
                    }.convertToVertical()
                )
            ) {
                mOnId.invoke(it)
            }
        }
    }
}