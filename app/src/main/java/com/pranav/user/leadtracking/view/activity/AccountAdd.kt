package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.AccountAddFragment
import com.pranav.user.leadtracking.view.fragment.AddNotesFragment

class AccountAdd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putString("IDs",intent.getStringExtra("IDs"))
            bundle.putString("NAMEs",intent.getStringExtra("NAMEs"))

            val fragment = AccountAddFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "AddNotesActivity")
                    .commit()
        }

    }
}
