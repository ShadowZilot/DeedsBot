package functions

import bot_chains.random_poem.RandomPoemChain
import core.BotChains

class PoemsProviding : BotChains {

    override fun chains() = listOf(
        RandomPoemChain()
    )
}