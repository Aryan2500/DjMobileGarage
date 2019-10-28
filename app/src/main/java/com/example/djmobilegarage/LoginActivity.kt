package com.example.djmobilegarage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        get_otp_btn.setOnClickListener{
            Intent(this , OtpActivity::class.java).also {
                startActivity(it)
            }
        }
    }


}
