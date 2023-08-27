package data

class CategoryInjectLinkToProof(
    private val mLinkToProof: String
) : Category.Mapper<Category> {

    override fun map(
        id: Int, name: String, categoryCode: Int,
        languageCode: String, linkToProof: String
    ): Category {
        return Category(
            id,
            name,
            categoryCode,
            languageCode,
            mLinkToProof
        )
    }
}