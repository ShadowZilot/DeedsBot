package data

class PoemInjectLinkToProof(
    private val mLinkToProof: String
) : Poem.Mapper<Poem> {
    override fun map(
        id: Int,
        categoryCode: Int,
        langCode: String,
        tag: String,
        bibleLang: String,
        bibleLangCode: Int,
        text: String,
        linkToProof: String,
        imageSource: String,
        localizedTag: String
    ) = Poem(
        id,
        categoryCode,
        langCode,
        tag,
        bibleLang,
        bibleLangCode,
        text,
        mLinkToProof,
        imageSource,
        localizedTag
    )
}