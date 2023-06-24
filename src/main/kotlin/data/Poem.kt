package data

import helpers.storage.Record
import java.sql.ResultSet

data class Poem(
    private val mId: Int,
    private val mCategoryCode: Int,
    private val mLangCode: String,
    private val mTag: String,
    private val mBibleLang: String,
    private val mBibleLangCode: Int,
    private val mPoemText: String,
    private val mLinkToProof: String
) : Record() {

    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getInt("category_code"),
        item.getString("lang_code"),
        item.getString("tag"),
        item.getString("bible_lang"),
        item.getInt("bible_lang_code"),
        item.getString("poem_text"),
        item.getString("link_to_proof")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mCategoryCode,
        mLangCode,
        mTag,
        mBibleLang,
        mBibleLangCode,
        mPoemText,
        mLinkToProof
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            categoryCode: Int,
            langCode: String,
            tag: String,
            bibleLang: String,
            bibleLangCode: Int,
            text: String,
            linkToProof: String
        ) : T
    }

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE `id` = $mId"

    override fun insertSQLQuery(tableName: String) = "INSERT INTO $tableName (`category_code`, `lang_code`, `tag`," +
            " `bible_lang`, `bible_lang_code`, `poem_text`, `link_to_proof`) VALUES($mCategoryCode, '$mLangCode', '$mTag'," +
            " '$mBibleLang', $mBibleLangCode, '$mPoemText', '$mLinkToProof');"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `category_code` = $mCategoryCode," +
            " `lang_code` = '$mLangCode', `tag` = '$mTag', `bible_lang` = '$mBibleLang'," +
            " `bible_lang_code` = $mBibleLangCode, `poem_text` = '$mPoemText', `link_to_proof` = '$mLinkToProof'"
}