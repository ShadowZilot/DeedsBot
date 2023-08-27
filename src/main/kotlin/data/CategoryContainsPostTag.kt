package data

class CategoryContainsPostTag(
    private val mText: String
) : Category.Mapper<Boolean> {

    override fun map(
        id: Int, name: String, categoryCode: Int,
        languageCode: String, linkToProof: String
    ): Boolean {
        return mText.contains("#${name.lowercase()}")
    }
}