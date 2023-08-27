package data

import keyboard_markup.InlineButton

class CategoryToButton(
    private val mParameter: String = "poemByCategory"
) : Category.Mapper<InlineButton> {

    override fun map(id: Int, name: String, categoryCode: Int, languageCode: String, linkToProof: String) =
        InlineButton(
            name, mCallbackData = "$mParameter=$categoryCode"
        )
}