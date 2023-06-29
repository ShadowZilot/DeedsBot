package data

class PoemValidate : Poem.Mapper<Boolean> {
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
    ): Boolean {
        return categoryCode != -1 && langCode.isNotEmpty() &&
                tag.isNotEmpty() && tag.length <= 24 &&
                bibleLang.isNotEmpty() && bibleLang.length <= 8 &&
                bibleLangCode != -1 && text.isNotEmpty()
    }
}