package functions

import bot_chains.contact.*
import core.BotChains

class ContactFunction : BotChains {

    override fun chains() = listOf(
        GoToContact(),
        TownList(),
        BeginFillContactForm(),
        SecondStatusContact(),
        ThirdStatusContact(),
        FinalForm(),
        SubmitForm()
    )
}