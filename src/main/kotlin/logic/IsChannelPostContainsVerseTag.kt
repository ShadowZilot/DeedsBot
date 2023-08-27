package logic

import data.Poem
import data.PoemContainsPostTag

interface IsChannelPostContainsVerseTag {

    fun isContains(): Poem

    class Base(
        private val mVerses: List<Poem>,
        private val mText: String
    ) : IsChannelPostContainsVerseTag {

        override fun isContains(): Poem {
            val isContainsTag = PoemContainsPostTag(mText)
            for (verse in mVerses) {
                if (verse.map(isContainsTag)) {
                    return verse
                }
            }
            throw Exception()
        }
    }
}