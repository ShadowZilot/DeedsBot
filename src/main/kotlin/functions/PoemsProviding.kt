package functions

import bot_chains.categories.GoToCategoriesMenu
import bot_chains.categories.GoToPoemByCategory
import bot_chains.random_poem.GotToPoem
import bot_chains.random_poem.RandomPoemChain
import core.BotChains

class PoemsProviding : BotChains {

    override fun chains() = listOf(
        RandomPoemChain(),
        GoToCategoriesMenu(),
        GoToPoemByCategory(),
        GotToPoem()
    )
}