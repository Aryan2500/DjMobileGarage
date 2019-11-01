package com.example.djmobilegarage.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.djmobilegarage.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


       Handler().postDelayed({
            // Do something after 5s = 5000ms
            Intent(this , LoginActivity::class.java).let {
                startActivity(it)
                finish()
            }
        }, 5000)
    }
}
