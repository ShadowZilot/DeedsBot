package data

import helpers.storage.Record
import java.sql.ResultSet

data class Category(
    private val mId: Int,
    private val mName: String,
    private val mCategoryCode: Int,
    private val mLanguageCode: String,
    private val mLinkToProof: String
) : Record() {

    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getString("name"),
        item.getInt("category_code"),
        item.getString("language_code"),
        item.getString("link_to_proof") ?: ""
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mName,
        mCategoryCode,
        mLanguageCode,
        mLinkToProof
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            name: String,
            categoryCode: Int,
            languageCode: String,
            linkToProof: String
        ): T
    }

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE `id` = $mId"

    override fun insertSQLQuery(tableName: String) =
        "INSERT INTO $tableName (`name`, `category_code`, `link_to_proof`)" +
                " VALUES('$mName', $mCategoryCode, '$mLinkToProof')"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `name` = '$mName'," +
            " `category_code` = $mCategoryCode, `link_to_proof` = '$mLinkToProof' WHERE `id` = $mId"
}