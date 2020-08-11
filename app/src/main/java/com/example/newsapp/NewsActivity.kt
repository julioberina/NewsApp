package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.News
import com.example.newsapp.interfaces.NewsService
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import kotlin.concurrent.thread

class NewsActivity : AppCompatActivity() {

    companion object {
        var apiKey = ""
    }

    private var currentPage = 1
    private var currentPageSize = 10
    private var searchQuery = ""
    private var startPage = 1
    private lateinit var retrofit: Retrofit
    private lateinit var newsService: NewsService
    private lateinit var adapter: NewsListAdapter
    public var data: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        if (apiKey == "")   getApiKey()

        retrofit = Retrofit.Builder()
                            .baseUrl("https://newsapi.org/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

        newsService = retrofit.create(NewsService::class.java)

        Log.e("NewsActivity", "I made it to this point")
        Log.e("NewsActivity", "I finished the request")

        adapter = NewsListAdapter(this, data)

        setContentView(R.layout.activity_news)

        val recycler = findViewById<RecyclerView>(R.id.recView)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun getApiKey() {
        try {
            val inputStream = getAssets().open("apikey.txt")

            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                apiKey = bufferedReader.readLine()
                inputStream.close()
            }

        } catch (e: FileNotFoundException) {
            Log.e("NewsActivity", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("NewsActivity", "Cannot read file: " + e.toString())
        }
    }

    public fun searchClicked(v: View) {
        currentPage = 1
        startPage = 1
        searchQuery = findViewById<EditText>(R.id.searchText).text.toString().replace(' ', '+')

        runBlocking {
            val headerMap = mapOf("X-Api-Key" to apiKey)
            data = newsService.getNews(headerMap, searchQuery, 10, currentPage)
        }

        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + startPage + "-" + (startPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")

        adapter.setData(data)
        adapter.notifyDataSetChanged()

        val next = findViewById<TextView>(R.id.nextText)
        next.visibility = View.VISIBLE
        next.setClickable(true)

        val recView = findViewById<RecyclerView>(R.id.recView)
        recView.visibility = View.VISIBLE
    }

    public fun resetClicked(v: View) {
        currentPage = 1
        startPage = 1
        findViewById<EditText>(R.id.searchText).setText("")
        findViewById<TextView>(R.id.queryText).setText("")
        findViewById<TextView>(R.id.showingResultsText).setText("")

        val prev = findViewById<TextView>(R.id.prevText)
        prev.visibility = View.INVISIBLE
        prev.setClickable(false)

        val next = findViewById<TextView>(R.id.nextText)
        next.visibility = View.INVISIBLE
        next.setClickable(false)

        val recView = findViewById<RecyclerView>(R.id.recView)
        recView.visibility = View.GONE

        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }

    public fun prevClicked(v: View) {
        startPage -= 10
        currentPage -= 1

        if (startPage == 1) {
            val prev = findViewById<TextView>(R.id.prevText)
            prev.visibility = View.INVISIBLE
            prev.setClickable(false)
        }

        runBlocking {
            val headerMap = mapOf("X-Api-Key" to apiKey)
            data = newsService.getNews(headerMap, searchQuery, 10, currentPage)
        }

        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + startPage + "-" + (startPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")

        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }

    public fun nextClicked(v: View) {
        startPage += 10
        currentPage += 1

        runBlocking {
            val headerMap = mapOf("X-Api-Key" to apiKey)
            data = newsService.getNews(headerMap, searchQuery, 10, currentPage)
        }

        val prev = findViewById<TextView>(R.id.prevText)
        prev.visibility = View.VISIBLE
        prev.setClickable(true)

        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + startPage + "-" + (startPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")

        adapter.setData(data)
        adapter.notifyDataSetChanged()
    }
}
