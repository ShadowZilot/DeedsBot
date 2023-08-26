package functions

import bot_chains.greeting.Greeting
import bot_chains.moderator.menu.BackToMainMenu
import core.BotChains

class GreetingFeature : BotChains {

    override fun chains() = listOf(
        Greeting(),
        BackToMainMenu()
    )
}