package functions

import bot_chains.help.BackToFaq
import bot_chains.help.GoToResponse
import bot_chains.help.HelpFAQCommand
import core.BotChains

class HelpFunctions : BotChains {

    override fun chains() = listOf(
        HelpFAQCommand(),
        GoToResponse(),
        BackToFaq()
    )
}