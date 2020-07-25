package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    companion object {
        var isFirstRun = true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        if (MainActivity.isFirstRun) {
            Handler().postDelayed(Runnable() {
                run() {
                    MainActivity.isFirstRun = false
                    startActivity(Intent(this, NewsActivity::class.java))
                    finish()
                }
            }, 5000)
        }
        else {
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }
    }
}
