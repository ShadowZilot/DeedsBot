package data

import executables.AnswerToCallback
import executables.Executable

class DoesPoemExists(
    private val mKey: String
) : Poem.Mapper<Pair<Boolean, Executable>> {
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
    ): Pair<Boolean, Executable> {
        val result = PoemStorage.Base.Instance().isPoemExits(tag, langCode)
        return Pair(
            result, if (result)
                AnswerToCallback(
                    mKey, "Стих с тегом $tag уже существует!",
                    true
                ) else Executable.Dummy()
        )
    }
}