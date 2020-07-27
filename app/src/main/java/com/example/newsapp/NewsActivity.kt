package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class NewsActivity : AppCompatActivity() {

    companion object {
        var apiKey = ""
    }

    private var currentPage = 1
    private var currentPageSize = 10
    private var searchQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        if (apiKey == "")   getApiKey()
        setContentView(R.layout.activity_news)
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
        searchQuery = findViewById<EditText>(R.id.searchText).text.toString().replace(' ', '+')
        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + currentPage + "-" + (currentPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")

        val next = findViewById<TextView>(R.id.nextText)
        next.visibility = View.VISIBLE
        next.setClickable(true)
    }

    public fun resetClicked(v: View) {
        currentPage = 1
        findViewById<EditText>(R.id.searchText).setText("")
        findViewById<TextView>(R.id.queryText).setText("")
        findViewById<TextView>(R.id.showingResultsText).setText("")

        val prev = findViewById<TextView>(R.id.prevText)
        prev.visibility = View.INVISIBLE
        prev.setClickable(false)

        val next = findViewById<TextView>(R.id.nextText)
        next.visibility = View.INVISIBLE
        next.setClickable(false)
    }

    public fun prevClicked(v: View) {
        currentPage -= 10

        if (currentPage == 1) {
            val prev = findViewById<TextView>(R.id.prevText)
            prev.visibility = View.INVISIBLE
            prev.setClickable(false)
        }

        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + currentPage + "-" + (currentPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")
    }

    public fun nextClicked(v: View) {
        currentPage += 10

        // No limit yet

        val prev = findViewById<TextView>(R.id.prevText)
        prev.visibility = View.VISIBLE
        prev.setClickable(true)

        findViewById<TextView>(R.id.showingResultsText).setText("Showing results " + currentPage + "-" + (currentPage + 9) + " for:")
        findViewById<TextView>(R.id.queryText).setText("\"" + searchQuery + "\"")
    }
}
