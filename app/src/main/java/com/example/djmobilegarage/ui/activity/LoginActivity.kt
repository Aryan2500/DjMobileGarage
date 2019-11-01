package com.example.djmobilegarage.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.djmobilegarage.R
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
