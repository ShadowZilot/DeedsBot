package data

import helpers.storage.Record
import java.sql.ResultSet

data class Question(
    private val mId: Int,
    private val mQuestion: String,
    private val mResponse: String
) : Record() {
    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getString("question"),
        item.getString("response")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mQuestion,
        mResponse
    )

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE `id` = $mId"

    override fun insertSQLQuery(tableName: String) = "INSERT INTO $tableName `id`, `question`, `response`" +
            " VALUES($mId, '$mQuestion', '$mResponse')"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `question` = '$mQuestion'," +
            " `response` = '$mResponse' WHERE `id` = $mId"

    interface Mapper<T> {

        fun map(
            id: Int,
            question: String,
            response: String
        ): T
    }
}