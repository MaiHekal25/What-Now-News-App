package com.training.whatnow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.training.whatnow.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=a894df1ef11f4c25b9951037b52924e4&pageSize=30
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()
    }

    private fun loadNews() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


        val c = retrofit.create(INews::class.java)
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(
                call: Call<News?>,
                response: Response<News?>
            ) {
                val news = response.body()
                var articles = news?.articles!!

                articles.removeAll {
                    it.title == "[Removed]"
                }
                showNews(articles)
                binding.progress.isVisible = false
            }

            override fun onFailure(
                call: Call<News?>,
                t: Throwable
            ) {
                Log.d("###", "Error: Articles Are Not Available")
                binding.progress.isVisible = false
            }

        })
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.recyclerNews.adapter = adapter
    }
}