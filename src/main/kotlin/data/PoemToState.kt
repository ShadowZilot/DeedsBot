package data

import core.Updating
import staging.StateHandling

class PoemToState(
    private val mStates: StateHandling,
    private val mUpdating: Updating
) : Poem.Mapper<Unit> {
    override fun map(
        id: Int,
        categoryCode: Int,
        langCode: String,
        tag: String,
        bibleLang: String,
        bibleLangCode: Int,
        text: String,
        linkToProof: String,
        imageSource: String
    ) {
        mStates.state(mUpdating).editor(mStates).apply {
            putInt("id", id)
            putInt("category_code", categoryCode)
            putString("lang_code", langCode)
            putString("tag", tag)
            putString("bible_lang", bibleLang)
            putInt("bible_lang_code", bibleLangCode)
            putString("poem_text", text)
            putString("link_to_proof", linkToProof)
            putString("image_source", imageSource)
        }.commit()
    }
}

interface ClearPoemModel {

    fun clear()

    class Base(
        private val mStates: StateHandling,
        private val mUpdating: Updating
    ) : ClearPoemModel {
        override fun clear() {
            mStates.state(mUpdating).editor(mStates).apply {
                deleteValue("id")
                deleteValue("category_code")
                deleteValue("lang_code")
                deleteValue("tag")
                deleteValue("bible_lang")
                deleteValue("bible_lang_code")
                deleteValue("poem_text")
                deleteValue("link_to_proof")
                deleteValue("image_source")
            }.commit()
        }
    }
}