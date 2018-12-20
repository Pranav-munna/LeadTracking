package com.pranav.user.leadtracking.view.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.DealProfileFragment

class DealProfileActivity : AppCompatActivity() {

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
            bundle.putString("VALUE", intent.getStringExtra("VALUE"))
            bundle.putString("NAME2", intent.getStringExtra("NAME2"))
            bundle.putString("LEVEL_VALUE", intent.getStringExtra("LEVEL_VALUE"))
            bundle.putString("LEVEL_ID", intent.getStringExtra("LEVEL_ID"))
            bundle.putString("MEMBERS", intent.getStringExtra("MEMBERS"))
            bundle.putString("CONTACT_ID", intent.getStringExtra("CONTACT_ID"))
            bundle.putString("START_DATE", intent.getStringExtra("START_DATE"))
            bundle.putString("END_DATE", intent.getStringExtra("END_DATE"))
            bundle.putString("BILLING_TYPE", intent.getStringExtra("BILLING_TYPE"))
            bundle.putString("DELIVERABLE_TYPE", intent.getStringExtra("DELIVERABLE_TYPE"))
            bundle.putString("STAGE", intent.getStringExtra("STAGE"))

            val fragment = DealProfileFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "DealProfileActivity")
                    .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val prefs = this.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        startActivity(Intent(this, DealsActivity::class.java))
    }
}
