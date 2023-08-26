package functions

import bot_chains.tell_miracle.TellMiracleChain
import core.BotChains

class MiracleChains : BotChains {

    override fun chains() = listOf(
        TellMiracleChain()
    )
}