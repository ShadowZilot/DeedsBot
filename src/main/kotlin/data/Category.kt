package data

import helpers.storage.Record
import java.sql.ResultSet

data class Category(
    private val mId: Int,
    private val mName: String,
    private val mCategoryCode: Int
) : Record() {

    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getString("name"),
        item.getInt("category_code")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mName,
        mCategoryCode
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            name: String,
            categoryCode: Int
        ) : T
    }

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE `id` = $mId"

    override fun insertSQLQuery(tableName: String) = "INSERT INTO $tableName (`name`, `category_code`)" +
            " VALUES('$mName', $mCategoryCode)"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `name` = '$mName'," +
            " `category_code` = $mCategoryCode WHERE `id` = $mId"
}