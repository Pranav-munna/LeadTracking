package com.pranav.user.leadtracking.view.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.FiltersFragment

class FiltersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val bundle = Bundle()
        try {
            bundle.putString("GROUP", intent.getStringExtra("GROUP"))
            bundle.putString("DESIGNATION", intent.getStringExtra("DESIGNATION"))
        } catch (e: Exception) {
            bundle.putString("GROUP", "")
            bundle.putString("DESIGNATION", "")
        }

        val fragment = FiltersFragment()
        fragment.arguments = bundle
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, this::class.java.name)
                    .commit()
        }

    }
}
