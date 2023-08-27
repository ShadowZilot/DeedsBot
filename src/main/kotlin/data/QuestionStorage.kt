package data

import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import java.sql.SQLException

interface QuestionStorage : StorageShell {

    fun allQuestions(): List<Question>

    fun questionById(id: Int): Question

    class Base private constructor(
        private val mTableName: String,
        private val mDatabase: DatabaseHelper
    ) : QuestionStorage {

        override fun allQuestions(): List<Question> {
            val questions = mutableListOf<Question>()
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName"
            ) { item, isNext ->
                var next = isNext
                while (next) {
                    questions.add(
                        Question(item)
                    )
                    next = item.next()
                }
            }
            return questions
        }

        override fun questionById(id: Int): Question {
            var question: Question? = null
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName WHERE `id` = $id"
            ) { item, _ ->
                question = try {
                    Question(item)
                } catch (e: SQLException) {
                    null
                }
            }
            return question ?: throw Exception("Question with id = $id not found!")
        }

        override fun tableName() = mTableName

        override fun tableSchema() = "CREATE TABLE $mTableName(" +
                "id int primary key auto_increment," +
                "question varchar(256)," +
                "response varchar(256)" +
                ");"

        object Instance {
            private var mInstance: QuestionStorage? = null

            fun create(tableName: String, database: DatabaseHelper) {
                if (mInstance == null) {
                    mInstance = Base(tableName, database)
                }
            }

            operator fun invoke() = mInstance ?: throw Exception()
        }
    }
}