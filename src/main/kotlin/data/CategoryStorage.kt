package data

import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper

interface CategoryStorage : StorageShell {

    fun categoriesByLanguage(langCode: String): List<Category>

    fun categoryName(langCode: String, categoryCode: Int) : String

    class Base private constructor(
        private val mTableName: String,
        private val mDatabase: DatabaseHelper
    ) : CategoryStorage {

        override fun categoriesByLanguage(langCode: String): List<Category> {
            val categories = mutableListOf<Category>()
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName WHERE `language_code` = '$langCode'"
            ) { item, isNext ->
                var next = isNext
                while (next) {
                    categories.add(
                        Category(item)
                    )
                    next = item.next()
                }
            }
            return categories
        }

        override fun categoryName(langCode: String, categoryCode: Int): String {
            var name = ""
            mDatabase.executeQuery(
                "SELECT name FROM $mTableName WHERE `language_code` = '$langCode'" +
                        " AND `category_code` = $categoryCode;"
            ) { item, _ ->
                name = item.getString("name")
            }
            return name
        }

        override fun tableName() = mTableName

        override fun tableSchema() = "CREATE TABLE $mTableName(" +
                "id int primary key auto_increment," +
                "name varchar(64)," +
                "category_code int," +
                "language_code varchar(8)" +
                ")"
        object Instance {
            private var mInstance: CategoryStorage? = null

            fun create(tableName: String, database: DatabaseHelper) {
                if (mInstance == null) {
                    mInstance = Base(tableName, database)
                }
            }

            operator fun invoke() = mInstance ?: throw Exception()
        }
    }
}