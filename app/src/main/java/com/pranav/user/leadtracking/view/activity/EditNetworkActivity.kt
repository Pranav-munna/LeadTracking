package com.pranav.user.leadtracking.view.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.EditNetworkfragment

class EditNetworkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState==null){
            var bundle = Bundle()
            bundle.putString("IMAGE", intent.getStringExtra("IMAGE"))
            bundle.putString("NAME",  intent.getStringExtra("NAME"))
            bundle.putString("DESIGNATION_ID",  intent.getStringExtra("DESIGNATION_ID"))
            bundle.putString("COUNTRY_CODE",  intent.getStringExtra("COUNTRY_CODE"))
            bundle.putString("PHONE",   intent.getStringExtra("PHONE"))
            bundle.putString("MAIL",   intent.getStringExtra("MAIL"))
            bundle.putString("GROUP_ID",   intent.getStringExtra("GROUP_ID"))
            bundle.putString("ID",   intent.getStringExtra("ID"))
            bundle.putString("LAST_CONNECTED_AT",   intent.getStringExtra("LAST_CONNECTED_AT"))


            val fragment = EditNetworkfragment()
            fragment.arguments = bundle


            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment,"EditNetworkActivity")
                    .commit()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith("android.webkit.")) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]
            if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
