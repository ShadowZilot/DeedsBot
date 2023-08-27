package functions

import bot_chains.channel_deeds.OnDeedPostGotten
import core.BotChains

class DeedsChannel : BotChains {

    override fun chains() = listOf(
        OnDeedPostGotten()
    )
}