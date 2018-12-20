package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.AccountAddEditFragment

class AccountAddEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, AccountAddEditFragment(), "AddDealsActivity")
                    .commit()
        }
    }
}
