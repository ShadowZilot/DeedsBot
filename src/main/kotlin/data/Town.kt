package data

import helpers.storage.Record
import java.sql.ResultSet

data class Town(
    private val mId: Int,
    private val mName: String,
    private val mLink: String
) : Record() {

    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getString("name"),
        item.getString("link")
    )

    fun <T> map(
        mapper: Mapper<T>
    ) = mapper.map(
        mId,
        mName,
        mLink
    )

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE id = $mId"

    override fun insertSQLQuery(tableName: String) = "INSERT INTO $tableName (`name`, `link`)" +
            " VALUES('$mName', '$mLink')"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `name` = '$mName', `link` = '$mLink'" +
            " WHERE `id` = $mId"

    interface Mapper<T> {

        fun map(
            id: Int,
            name: String,
            link: String
        ) : T
    }
}