package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.AllQuestionsFragment

class AllQuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putString("ID", intent.getStringExtra("ID"))

            var fragment = AllQuestionsFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "AllQuestionsActivity")
                    .commit()
        }
    }
}
