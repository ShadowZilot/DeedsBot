package bot_chains.moderator.verses_list

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerInlineQuery
import executables.Executable
import handlers.OnInlineQuerySenderChat
import messages.moderator.PoemToListItem
import updating.UpdatingInlineQuery
import updating.UpdatingInlineQueryOffset

class VersesList : Chain(OnInlineQuerySenderChat()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val offset = updating.map(UpdatingInlineQueryOffset())
        return listOf(
            AnswerInlineQuery(
                mKey,
                PoemStorage.Base.Instance().searchPoem(
                    updating.map(UpdatingInlineQuery()),
                    updating.map(UpdatingInlineQueryOffset())
                ).map {
                    it.map(PoemToListItem())
                },
                 offset + 50,
                0
            )
        )
    }
}