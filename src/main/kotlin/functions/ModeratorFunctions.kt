package functions

import bot_chains.moderator.add_verse.StartAddVerse
import bot_chains.moderator.menu.GoToModeratorMenu
import core.BotChains

class ModeratorFunctions : BotChains {

    override fun chains() = listOf(
        GoToModeratorMenu(),
        StartAddVerse()
    )
}