package messages

import data.Question
import keyboard_markup.InlineButton

class QuestionToButton : Question.Mapper<InlineButton> {

    override fun map(id: Int, question: String, response: String): InlineButton {
        return InlineButton(
            question,
            mCallbackData = "question=$id"
        )
    }
}