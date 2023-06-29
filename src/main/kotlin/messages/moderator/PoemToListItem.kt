package messages.moderator

import data.Poem
import inline_query_result.InlineQueryResult
import inline_query_result.InlineQueryResultText
import inline_query_result.content.InputTextContent

class PoemToListItem: Poem.Mapper<InlineQueryResult> {

    override fun map(
        id: Int,
        categoryCode: Int,
        langCode: String,
        tag: String,
        bibleLang: String,
        bibleLangCode: Int,
        text: String,
        linkToProof: String,
        imageSource: String
    ) = InlineQueryResultText(
        id,
        tag,
        InputTextContent("verse\\=$id"),
        text
    )
}