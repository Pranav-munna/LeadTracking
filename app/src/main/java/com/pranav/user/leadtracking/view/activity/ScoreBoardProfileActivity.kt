package com.pranav.user.leadtracking.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.ScoreBoardProfileFragment

class ScoreBoardProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putString("IMAGE", intent.getStringExtra("IMAGE"))
            bundle.putString("F_NAME", intent.getStringExtra("F_NAME"))
            bundle.putString("L_NAME", intent.getStringExtra("L_NAME"))
            bundle.putString("DESIGNATION_ID", intent.getStringExtra("DESIGNATION_ID"))
            bundle.putString("LOCATION", intent.getStringExtra("LOCATION"))
            bundle.putString("EMAIL", intent.getStringExtra("EMAIL"))
            bundle.putString("NUMBER", intent.getStringExtra("NUMBER"))
            bundle.putString("OFFICE_NUMBER", intent.getStringExtra("OFFICE_NUMBER"))
            bundle.putString("ABOUT", intent.getStringExtra("ABOUT"))
            val fragment = ScoreBoardProfileFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "ScoreBoardProfileActivity")
                    .commit()

        }
    }
}
