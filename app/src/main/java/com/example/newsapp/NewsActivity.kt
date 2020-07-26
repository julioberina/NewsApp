package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class NewsActivity : AppCompatActivity() {

    companion object {
        var apiKey = ""
    }

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

    public fun searchClicked() {

    }

    public fun resetClicked() {

    }
}
