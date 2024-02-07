package com.example.shortnews.utility



import com.example.shortnews.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

object NewsApiService {
    private const val API_KEY = BuildConfig.NEWS_API_KEY
    private const val BASE_URL = "https://newsdata.io/api/1/news?country=fi&apikey=$API_KEY"

    // Function to fetch news articles from the API
    suspend fun fetchNews() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(BASE_URL)
            .build()
        println("KOKO URL:$BASE_URL")

        try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody: ResponseBody? = response.body
                if (responseBody != null) {
                    val responseString = responseBody.string()
                    println(responseString) // Print the response to the terminal
                } else {
                    println("Response body is null")
                }
            } else {
                println("Failed to fetch news. Error code: ${response.code}")
            }
        } catch (e: IOException) {
            println("Failed to fetch news: ${e.message}")
        }
    }
}