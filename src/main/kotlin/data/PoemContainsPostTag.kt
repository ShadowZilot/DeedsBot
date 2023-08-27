package data

class PoemContainsPostTag(
    private val mText: String
) : Poem.Mapper<Boolean> {

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
    ): Boolean {
        return mText.contains("#$tag")
    }
}