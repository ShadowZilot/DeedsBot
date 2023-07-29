package data

import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper

interface TownsStorage : StorageShell {

    fun towns() : List<Town>

    class Base private constructor(
        private val mDatabase: DatabaseHelper,
        private val mTableName: String
    ) : TownsStorage {
        override fun towns(): List<Town> {
            val towns = mutableListOf<Town>()
            mDatabase.executeQuery(
                "SELECT * FROM $mTableName ORDER BY name"
            ) { item, next ->
                var isNext = next
                while (isNext) {
                    towns.add(
                        Town(item)
                    )
                    isNext = item.next()
                }
            }
            return towns
        }

        override fun tableName() = mTableName

        override fun tableSchema() = "CREATE TABLE $mTableName(" +
                "id int primary key auto_increment," +
                "name varchar(128)," +
                "link varchar(256)" +
                ");"

        object Instance {
            private var mInstance: TownsStorage? = null

            fun create(tableName: String, database: DatabaseHelper) {
                if (mInstance == null) {
                    mInstance = Base(database, tableName)
                }
            }

            operator fun invoke() = mInstance ?: throw Exception()
        }
    }
}