package functions

import bot_chains.support.WriteToSupport
import core.BotChains

class SupportFunction : BotChains {

    override fun chains() = listOf(
        WriteToSupport()
    )
}