import core.BotProvider
import core.storage.Storages
import data.CategoryStorage
import data.PoemStorage
import data.TownsStorage
import functions.ContactFunction
import functions.GreetingFeature
import functions.ModeratorFunctions
import functions.PoemsProviding
import helpers.storage.jdbc_wrapping.DatabaseHelper
import okhttp3.OkHttpClient

val sClient = OkHttpClient()

fun main(args: Array<String>) {
    val provider = BotProvider.Base(args)
    val db = DatabaseHelper.Base.Instance.provideInstance(Storages.Main.Provider().stConfig)
    PoemStorage.Base.Instance.create("poems", db)
    db.createTable(PoemStorage.Base.Instance().tableSchema())
    CategoryStorage.Base.Instance.create("poem_categories", db)
    db.createTable(CategoryStorage.Base.Instance().tableSchema())
    TownsStorage.Base.Instance.create("towns", db)
    db.createTable(TownsStorage.Base.Instance().tableSchema())
    provider.createdBot(
        ModeratorFunctions(),
        GreetingFeature(),
        PoemsProviding(),
        ContactFunction()
    )
}