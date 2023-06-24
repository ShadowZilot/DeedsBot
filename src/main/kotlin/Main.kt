import core.BotProvider
import core.storage.Storages
import data.PoemStorage
import functions.GreetingFeature
import functions.PoemsProviding
import helpers.storage.jdbc_wrapping.DatabaseHelper

fun main(args: Array<String>) {
    val provider = BotProvider.Base(args)
    val db = DatabaseHelper.Base.Instance.provideInstance(Storages.Main.Provider().stConfig)
    PoemStorage.Base.Instance.create("poems", db)
    db.createTable(PoemStorage.Base.Instance().tableSchema())
    provider.createdBot(
        GreetingFeature(),
        PoemsProviding()
    )
}