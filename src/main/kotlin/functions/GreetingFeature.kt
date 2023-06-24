package functions

import bot_chains.greeting.AnotherPoem
import bot_chains.greeting.Greeting
import core.BotChains

class GreetingFeature : BotChains {

    override fun chains() = listOf(
        Greeting(),
        AnotherPoem()
    )
}