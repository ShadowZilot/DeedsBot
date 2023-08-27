package logic

import data.Category
import data.CategoryContainsPostTag

interface IsChannelPostContainsCategory {

    fun isContains(): Category

    class Base(
        private val mCategories: List<Category>,
        private val mText: String
    ) : IsChannelPostContainsCategory {

        override fun isContains(): Category {
            val isContainsCategory = CategoryContainsPostTag(mText)
            for (i in mCategories.indices) {
                if (mCategories[i].map(isContainsCategory)) {
                    return mCategories[i]
                }
            }
            throw Exception()
        }
    }
}