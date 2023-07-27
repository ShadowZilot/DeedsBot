package data

import helpers.storage.Record
import staging.State
import java.sql.ResultSet

data class Poem(
    private val mId: Int,
    private val mCategoryCode: Int,
    private val mLangCode: String,
    private val mTag: String,
    private val mBibleLang: String,
    private val mBibleLangCode: Int,
    private val mPoemText: String,
    private val mLinkToProof: String,
    private val mImageSource: String,
    private val mLocalizedTag: String
) : Record() {
    constructor(langCode: String) : this(
        -1,
        -1,
        langCode,
        "",
        "",
        -1,
        "",
        "",
        "",
        ""
    )

    constructor(state: State) : this(
        state.int("id"),
        state.int("category_code"),
        state.string("lang_code"),
        state.string("tag"),
        state.string("bible_lang"),
        state.int("bible_lang_code"),
        state.string("poem_text"),
        state.string("link_to_proof"),
        state.string("image_source"),
        state.string("localized_tag")
    )

    constructor(item: ResultSet) : this(
        item.getInt("id"),
        item.getInt("category_code"),
        item.getString("lang_code"),
        item.getString("tag"),
        item.getString("bible_lang"),
        item.getInt("bible_lang_code"),
        item.getString("poem_text"),
        item.getString("link_to_proof"),
        item.getString("image_source"),
        item.getString("localized_tag")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mCategoryCode,
        mLangCode,
        mTag,
        mBibleLang,
        mBibleLangCode,
        mPoemText,
        mLinkToProof,
        mImageSource,
        mLocalizedTag
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
            linkToProof: String,
            imageSource: String,
            localizedTag: String
        ) : T
    }

    override fun deleteSQLQuery(tableName: String) = "DELETE FROM $tableName WHERE `id` = $mId"

    override fun insertSQLQuery(tableName: String) = "INSERT INTO $tableName (`category_code`, `lang_code`, `tag`," +
            " `bible_lang`, `bible_lang_code`, `poem_text`, `link_to_proof`, `image_source`, `localized_tag`)" +
            " VALUES($mCategoryCode, '$mLangCode', '$mTag'," +
            " '$mBibleLang', $mBibleLangCode, '$mPoemText', '$mLinkToProof', '$mImageSource', '$mLocalizedTag');"

    override fun updateSQLQuery(tableName: String) = "UPDATE $tableName SET `category_code` = $mCategoryCode," +
            " `lang_code` = '$mLangCode', `tag` = '$mTag', `bible_lang` = '$mBibleLang'," +
            " `bible_lang_code` = $mBibleLangCode, `poem_text` = '$mPoemText'," +
            " `link_to_proof` = '$mLinkToProof', `image_source` = '$mImageSource'," +
            " `localized_tag` = '$mLocalizedTag' WHERE `id` = $mId"
}