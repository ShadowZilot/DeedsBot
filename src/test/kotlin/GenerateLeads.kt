import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Test


class GenerateLeads {
    private val mClient = OkHttpClient()
    @Test
    fun generateLeads() {
        mClient.newCall(request()).execute()
    }

    private fun request(): Request {
        val resultArray = JSONArray()
        for (i in 51..75) {
            resultArray.put(pojo(index = i))
        }
        val body: RequestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaType(),
            resultArray.toString(2),
        )
        return Request.Builder()
            .addHeader("Authorization", ACCESS_TOKEN)
            .post(body)
            .url("https://testcrmday.amocrm.ru/api/v4/leads")
            .build()
    }

    private fun pojo(index: Int): JSONObject {
        return JSONObject().apply {
            put("name", "TestScan-$index")
            put("status_id", 48835660)
            put("pipeline_id", 5523130)
            put("_embedded", JSONObject().apply {
                put("tags", JSONArray().apply {
                    put(JSONObject().apply {
                        put("id", 174329)
                        put("name", "free")
                    })
                    put(JSONObject().apply {
                        put("id", 174313)
                        put("name", "telegram")
                    })
                })
                put("contacts", JSONArray().apply {
                    put(JSONObject().apply {
                        put("id", 44120517)
                        put("is_main", true)
                    })
                })
            })
        }
    }

    companion object {
        const val ACCESS_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijc1MmFhZDA2NmQzZGUwMWFiZGE4MTkzMjRlODg3MDc3NmZhMmNlZDk4MzA2ZGIyNTBmMjA1OGYxZmJhZTVhNjMxNjY4MWVhYThjNTgzNjgzIn0.eyJhdWQiOiI5MGU3NDUxYS0wNTgwLTQ4NTItYTllMi01YTMzNGU4ZTI0NmYiLCJqdGkiOiI3NTJhYWQwNjZkM2RlMDFhYmRhODE5MzI0ZTg4NzA3NzZmYTJjZWQ5ODMwNmRiMjUwZjIwNThmMWZiYWU1YTYzMTY2ODFlYWE4YzU4MzY4MyIsImlhdCI6MTY4OTU3NTY2NywibmJmIjoxNjg5NTc1NjY3LCJleHAiOjE2ODk2NjIwNjcsInN1YiI6Ijk3NDQxODYiLCJncmFudF90eXBlIjoiIiwiYWNjb3VudF9pZCI6MCwiYmFzZV9kb21haW4iOm51bGwsInZlcnNpb24iOiJ2MSIsInNjb3BlcyI6WyJjaGF0cyIsImNybSIsIm1haWwiLCJub3RpZmljYXRpb25zIiwidW5zb3J0ZWQiXX0.MmM9eMuci3QvlDBReim6WQVATBoShXzW9pPW8ukRYnA25PLOfp2iwvXQ6omUl2VAv_cUvsWdNZzlY8dhWnUpzsyjtO8kBKU35tRbMbtoMf_mKfY7r-HlOGtMeRLazLpawS98oifEIJ0T4duQK7KnHbOFio8z7eMjNv6XtHfqy0mnkX1hReYusUGTk1G2_4vLfxaTeOwW_cgFI0Py1u-zcQGl6VukX6gLxhtdn67JfOAONmIVHj-MnyNKnfyto5HcHziq7jKhGUOCWxlKWsgZZV0rZR4MWgTvVN8mWbhKHrPadnS1J3sbDfnaYhFiAOJQNG-sINFBbT3p07ryFDgj8Q"
    }
}