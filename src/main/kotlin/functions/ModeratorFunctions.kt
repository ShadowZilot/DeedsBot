package functions

import bot_chains.moderator.add_verse.*
import bot_chains.moderator.delete_verse.GoToDeleteVerse
import bot_chains.moderator.delete_verse.SubmitDeleteVerse
import bot_chains.moderator.menu.BackToModeratorMenu
import bot_chains.moderator.menu.GoToModeratorMenu
import bot_chains.moderator.verses_list.GoBackToVerse
import bot_chains.moderator.verses_list.GoToVerseItem
import bot_chains.moderator.verses_list.VersesList
import core.BotChains

class ModeratorFunctions : BotChains {

    override fun chains() = listOf(
        VersesList(),
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
        EndInputCategory(),
        StartInputImage(),
        EndInputCategory(),
        SubmitAddVerse(),
        EndInputImage(),
        GoToVerseItem(),
        EditVerse(),
        GoToDeleteVerse(),
        SubmitDeleteVerse(),
        GoBackToVerse()
    )
}