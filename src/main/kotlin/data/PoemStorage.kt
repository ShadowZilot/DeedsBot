package data

import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper

interface PoemStorage : StorageShell {

    fun randomPoem(languageCode: String) : Poem

    fun randomPoem(languageCode: String, categoryCode: Int) : Poem

    class Base(
        private val mTableName: String,
        private val mDatabase: DatabaseHelper
    ) : PoemStorage {

        override fun randomPoem(languageCode: String): Poem {
            var poem: Poem? = null
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName WHERE `lang_code` = '$languageCode' ORDER BY RAND() LIMIT 1;"
            ) { item, _ ->
                poem = Poem(item)
            }
            return poem ?: throw Exception()
        }

        override fun randomPoem(languageCode: String, categoryCode: Int): Poem {
            var poem: Poem? = null
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName WHERE `category_code` = $categoryCode AND " +
                        "`lang_code` = '$languageCode' ORDER BY RAND() LIMIT 1;"
            ) { item, _ ->
                poem = Poem(item)
            }
            return poem ?: throw Exception()
        }

        override fun tableName() = mTableName

        override fun tableSchema() = "CREATE TABLE $mTableName(" +
                "id int primary key auto_increment," +
                "category_code int," +
                "lang_code varchar(8)," +
                "tag varchar(24)," +
                "bible_lang varchar(8)," +
                "bible_lang_code int," +
                "poem_text text," +
                "link_to_proof varchar(128)" +
                ");"

        object Instance {
            private var mInstance: PoemStorage? = null

            fun create(tableName: String, database: DatabaseHelper) {
                if (mInstance == null) {
                    mInstance = Base(tableName, database)
                }
            }

            operator fun invoke() = mInstance ?: throw Exception()
        }
    }
}