package functions

import bot_chains.moderator.add_verse.*
import bot_chains.moderator.menu.BackToModeratorMenu
import bot_chains.moderator.menu.GoToModeratorMenu
import core.BotChains

class ModeratorFunctions : BotChains {

    override fun chains() = listOf(
        GoToModeratorMenu(),
        StartAddVerse(),
        SelectLangAddVerse(),
        BackToModeratorMenu(),
        StartEnterStringField(),
        EndEnterStringField(),
        ModeratorBackToVerse(),
        StartEnterIntField(),
        EndEnterIntField(),
        StartInputCategory(),
        EndInputCategory()
    )
}