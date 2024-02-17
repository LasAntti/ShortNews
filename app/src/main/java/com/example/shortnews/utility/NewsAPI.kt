package com.example.shortnews.utility



import com.example.shortnews.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException



object NewsApiService {
    private val API_KEY = BuildConfig.NEWS_API_KEY
    private val BASE_URL = "https://newsdata.io/api/1/news?country=fi&apikey=$API_KEY"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val client = OkHttpClient()

    suspend fun fetchNews(): List<Article>? {
        val request = Request.Builder()
            .url(BASE_URL)
            .build()

        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody: ResponseBody? = response.body
                if (responseBody != null) {
                    val json = responseBody.string()
                    val newsApiResponse = parseJson(json)
                    return newsApiResponse?.results
                } else {
                    println("Response body is null")
                }
            } else {
                println("Failed to fetch news. Error code: ${response.code}")
            }
        } catch (e: IOException) {
            println("Failed to fetch news: ${e.message}")
        }
        return null
    }

    private fun parseJson(json: String): NewsApiResponse? {
        return try {
            val adapter = moshi.adapter(NewsApiResponse::class.java)
            adapter.fromJson(json)
        } catch (e: Exception) {
            println("Error parsing JSON: ${e.message}")
            null
        }
    }
}

data class Article(
    val articleId: String?,
    val title: String,
    val link: String?,
    val keywords: List<String>?,
    val creator: List<String>?,
    val videoUrl: String?,
    val description: String?,
    val content: String?,
    val pubDate: String?,
    val imageUrl: String?,
    val sourceId: String?,
    val sourceUrl: String?,
    val sourceIcon: String?,
    val sourcePriority: Int?,
    val country: List<String>?,
    val category: List<String>?,
    val language: String?
)

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)
