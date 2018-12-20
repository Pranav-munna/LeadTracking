package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.MeetingQuestionFragment

class MeetingQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            var bundle = Bundle()
            bundle.putString("AGENDA", intent.getStringExtra("AGENDA"))
            bundle.putString("CONTACTS", intent.getStringExtra("CONTACTS"))
            bundle.putString("DATE", intent.getStringExtra("DATE"))
            bundle.putString("TIME", intent.getStringExtra("TIME"))
            bundle.putString("SUBJECT", intent.getStringExtra("SUBJECT"))
            bundle.putString("LEAD_ID", intent.getStringExtra("LEAD_ID"))
            bundle.putString("FLAG", intent.getStringExtra("FLAG"))

            var fragment = MeetingQuestionFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, this::class.java.name)
                    .commit()

        }

    }
}
