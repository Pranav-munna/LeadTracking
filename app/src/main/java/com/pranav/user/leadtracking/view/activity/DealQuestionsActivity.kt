package com.pranav.user.leadtracking.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.DealQuestionsFragment

class DealQuestionsActivity : AppCompatActivity() {
    var flag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            flag = intent.getStringExtra("FLAG")
            var bundle = Bundle()
            bundle.putString("FLAG", flag)
            bundle.putString("ID", intent.getStringExtra("ID"))
            bundle.putString("LEVEL_ID", intent.getStringExtra("LEVEL_ID"))

            val fragment = DealQuestionsFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "DealQuestionsActivity")
                    .commit()
        }
    }

    override fun onBackPressed() {
        if (flag != "1")
            super.onBackPressed()
        else
            Toast.makeText(this, "Need to complete level 1", Toast.LENGTH_SHORT).show()
    }

}


