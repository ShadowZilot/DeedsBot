package commons

interface TagWithoutVerse {

    fun newTag() : String

    class Base(
        private val mTag: String
    ) : TagWithoutVerse {

        override fun newTag(): String {
            val tagList = mTag.split(".")
            return "${tagList[0]}.${tagList[1]}"
        }
    }
}