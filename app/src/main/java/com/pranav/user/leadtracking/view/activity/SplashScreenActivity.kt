package com.pranav.user.leadtracking.view.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.pranav.user.leadtracking.R
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class SplashScreenActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_splashscreen)

        handler.postDelayed({
            if (!(this).isFinishing) {
//                startActivity(Intent(this, LoginActivity::class.java))
                val prefs = this.getSharedPreferences("Token_", Context.MODE_PRIVATE).getString("LOGIN", "0")

                if (prefs == "0")
                    startActivity(Intent(this, MainActivity::class.java))
                else
                    startActivity(Intent(this, HomeFeedsActivity::class.java))

                this.finish()
            }
        }, 4000)


    }
}
