package com.pranav.user.leadtracking.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.NotesDetailsFragment

class NotesDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putString("ID", intent.getStringExtra("ID"))
            bundle.putString("TITLE", intent.getStringExtra("TITLE"))
            bundle.putString("BODY", intent.getStringExtra("BODY"))
            bundle.putString("NAME", intent.getStringExtra("NAME"))

            val fragment = NotesDetailsFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "NotesDetailsActivity")
                    .commit()
        }

    }
}
