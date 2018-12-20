package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.InviteesFragment

class InviteesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, InviteesFragment(), this::class.java.name)
                    .commit()

        }

    }
}
