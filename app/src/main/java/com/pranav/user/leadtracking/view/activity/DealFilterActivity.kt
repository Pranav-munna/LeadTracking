package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.DealFilterFragment

class DealFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {

            var bundle = Bundle()
            try {
                bundle.putString("GROUP", intent.getStringExtra("GROUP"))
                bundle.putString("LEVEL", intent.getStringExtra("LEVEL"))
            } catch (e: Exception) {
                bundle.putString("GROUP", "")
                bundle.putString("LEVEL", "")
            }

            val fragment = DealFilterFragment()
            fragment.arguments = bundle


            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, this::class.java.name)
                    .commit()
        }
    }
}
