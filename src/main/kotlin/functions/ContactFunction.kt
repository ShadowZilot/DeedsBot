package functions

import bot_chains.contact.GoToContact
import bot_chains.contact.TownList
import core.BotChains

class ContactFunction : BotChains {

    override fun chains() = listOf(
        GoToContact(),
        TownList()
    )
}