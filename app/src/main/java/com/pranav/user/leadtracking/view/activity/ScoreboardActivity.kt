package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.ScoreboardFragment

class ScoreboardActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, ScoreboardFragment(), "ScoreboardActivity")
                    .commit()

        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please back again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
