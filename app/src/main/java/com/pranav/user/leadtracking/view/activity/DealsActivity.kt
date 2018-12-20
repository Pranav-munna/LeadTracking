package com.pranav.user.leadtracking.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.DealsFragment

class DealsActivity : AppCompatActivity() {
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            var bundle = Bundle()
            try {
                bundle.putString("GROUP", intent.getStringExtra("GROUP"))
                bundle.putString("LEVEL", intent.getStringExtra("LEVEL"))
            } catch (e: Exception) {
                bundle.putString("GROUP", "")
                bundle.putString("LEVEL", "")
            }

            val fragment = DealsFragment()
            fragment.arguments = bundle


            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "DealsActivity")
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
