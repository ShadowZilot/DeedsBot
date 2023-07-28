package bot_chains.moderator.verses_list.categories

import chain.Chain
import core.Updating
import data.PoemStorage
import executables.AnswerInlineQuery
import executables.Executable
import handlers.OnInlineQuerySenderChat
import messages.moderator.PoemToListItem
import updating.UpdatingInlineQuery
import updating.UpdatingInlineQueryOffset

class CategoriesList : Chain(OnInlineQuerySenderChat()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val query = updating.map(UpdatingInlineQuery())
        return if (query.contains("category=") && query.length > 9) {
            val categoryCode = query.split("=")[1].toInt()
            listOf(
                AnswerInlineQuery(
                    mKey,
                    PoemStorage.Base.Instance().poemsByCode(
                        categoryCode,
                        updating.map(UpdatingInlineQueryOffset())
                    ).map {
                        it.map(PoemToListItem())
                    },
                    updating.map(UpdatingInlineQueryOffset()) + 50,
                    0
                )
            )
        } else {
            emptyList()
        }
    }
}