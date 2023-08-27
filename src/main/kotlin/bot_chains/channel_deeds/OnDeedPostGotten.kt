package bot_chains.channel_deeds

import chain.Chain
import core.Updating
import core.storage.Storages.Main.Provider
import data.CategoryInjectLinkToProof
import data.CategoryStorage
import data.PoemInjectLinkToProof
import data.PoemStorage
import executables.Executable
import handlers.OnPostChannelGotten
import logic.IsChannelPostContainsCategory
import logic.IsChannelPostContainsVerseTag
import org.json.JSONObject
import updating.UpdatingMessage
import updating.UpdatingMessageId

class OnDeedPostGotten : Chain(OnPostChannelGotten()) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val channelId = updating.map(object : Updating.Mapper<JSONObject> {
            override fun map(updating: JSONObject): JSONObject {
                return updating
            }
        }).getJSONObject("channel_post").getJSONObject("chat").getLong("id")
        return if (channelId == Provider().stConfig.configValueLong("channelId")) {
            val linkToProof =
                "${Provider().stConfig.configValueString("channelLink")}/${updating.map(UpdatingMessageId())}"
            val postText = updating.map(UpdatingMessage())
            try {
                val poem = IsChannelPostContainsVerseTag.Base(
                    PoemStorage.Base.Instance().allPoems("ru"),
                    postText
                ).isContains()
                PoemStorage.Base.Instance().updatePoem(
                    poem.map(PoemInjectLinkToProof(linkToProof))
                )
                listOf(Executable.Dummy())
            } catch (e: Exception) {
                try {
                    val category = IsChannelPostContainsCategory.Base(
                        CategoryStorage.Base.Instance().categoriesByLanguage("ru"),
                        postText
                    ).isContains()
                    CategoryStorage.Base.Instance().updateCategory(
                        category.map(CategoryInjectLinkToProof(linkToProof))
                    )
                    listOf(Executable.Dummy())
                } catch (e: Exception) {
                    listOf()
                }
            }
        } else {
            emptyList()
        }
    }
}