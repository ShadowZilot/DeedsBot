package messages

import data.Town
import keyboard_markup.InlineButton

class TownToButton : Town.Mapper<InlineButton> {

    override fun map(id: Int, name: String, link: String): InlineButton {
        return InlineButton(name, mUrl = link)
    }
}