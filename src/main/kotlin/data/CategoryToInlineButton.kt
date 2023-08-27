package data

import keyboard_markup.InlineButton
import keyboard_markup.InlineModeQuery

class CategoryToInlineButton : Category.Mapper<InlineButton> {

    override fun map(
        id: Int, name: String, categoryCode: Int,
        languageCode: String,
        linkToProof: String
    ) = InlineButton(
        name,
        mInlineMode = InlineModeQuery.CurrentChat("category=${categoryCode}")
    )
}