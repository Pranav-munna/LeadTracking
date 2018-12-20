package com.pranav.user.leadtracking.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.NetworkDetailsFragment

class NetworkDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {

            var bundle = Bundle()
            bundle.putString("NAME", intent.getStringExtra("NAME"))
            bundle.putString("ACCOUNT_NAME", intent.getStringExtra("ACCOUNT_NAME"))
            bundle.putString("COUNTRY_CODE", intent.getStringExtra("COUNTRY_CODE"))
            bundle.putString("PHONE", intent.getStringExtra("PHONE"))
            bundle.putString("DESIGNATION", intent.getStringExtra("DESIGNATION"))
            bundle.putString("DESIGNATION_ID", intent.getStringExtra("DESIGNATION_ID"))
            bundle.putString("LAST_CONNECTED_AT", intent.getStringExtra("LAST_CONNECTED_AT"))
            bundle.putString("IMAGE", intent.getStringExtra("IMAGE"))
            bundle.putString("GROUP_ID", intent.getStringExtra("GROUP_ID"))
            bundle.putString("ID", intent.getStringExtra("ID"))
            bundle.putString("MAIL", intent.getStringExtra("MAIL"))

            val fragment = NetworkDetailsFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "NetworkDetailsActivity")
                    .commit()
        }
    }

    override fun onBackPressed() {
        val prefs = this.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        startActivity(Intent(this, NetworkActivity::class.java))
    }
}
