package messages

import commons.BackButton
import core.Updating
import data.Question
import executables.EditTextMessage
import executables.Executable
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineKeyboardMarkup

class QuestionToMessage(
    private val mKey: String,
    private val mUpdating: Updating
) : Question.Mapper<Executable> {

    override fun map(id: Int, question: String, response: String): Executable {
        return EditTextMessage(
            mKey,
            buildString {
                appendLine("*${ToMarkdownSupported.Base(question).convertedString()}*")
                appendLine()
                appendLine(ToMarkdownSupported.Base(response).convertedString())
            },
            -1,
            InlineKeyboardMarkup(
                listOf(
                    BackButton.Base(
                        "backToFaq",
                        mUpdating
                    ).button()
                ).convertToVertical()
            )
        )
    }
}