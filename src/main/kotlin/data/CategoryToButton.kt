package data

import keyboard_markup.InlineButton

class CategoryToButton : Category.Mapper<InlineButton> {

    override fun map(id: Int, name: String, categoryCode: Int, languageCode: String) = InlineButton(
        name, mCallbackData = "poemByCategory=$categoryCode"
    )
}