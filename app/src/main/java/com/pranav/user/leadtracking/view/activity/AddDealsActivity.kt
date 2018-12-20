package com.pranav.user.leadtracking.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.AddDealsFragment

class AddDealsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var bundle = Bundle()
        bundle.putString("ID", intent.getStringExtra("ID"))
        bundle.putString("NAME", intent.getStringExtra("NAME"))
        bundle.putString("VALUE", intent.getStringExtra("VALUE"))
        bundle.putString("START_DATE", intent.getStringExtra("START_DATE"))
        bundle.putString("END_DATE", intent.getStringExtra("END_DATE"))
        bundle.putString("NAME2", intent.getStringExtra("NAME2"))
        bundle.putString("ACCOUNT_NAME", intent.getStringExtra("ACCOUNT_NAME"))
        bundle.putString("BILLING_TYPE", intent.getStringExtra("BILLING_TYPE"))
        bundle.putString("DELIVERABLE_TYPE", intent.getStringExtra("DELIVERABLE_TYPE"))

        bundle.putString("COUNTRY_CODE", intent.getStringExtra("COUNTRY_CODE"))
        bundle.putString("PHONE", intent.getStringExtra("PHONE"))
        bundle.putString("DESIGNATION", intent.getStringExtra("DESIGNATION"))
        bundle.putString("DESIGNATION_ID", intent.getStringExtra("DESIGNATION_ID"))
        bundle.putString("LAST_CONNECTED_AT", intent.getStringExtra("LAST_CONNECTED_AT"))
        bundle.putString("IMAGE", intent.getStringExtra("IMAGE"))
        bundle.putString("GROUP_ID", intent.getStringExtra("GROUP_ID"))
        bundle.putString("MAIL", intent.getStringExtra("MAIL"))
        bundle.putString("LEVEL_VALUE", intent.getStringExtra("LEVEL_VALUE"))
        bundle.putString("LEVEL_ID", intent.getStringExtra("LEVEL_ID"))
        bundle.putString("MEMBERS", intent.getStringExtra("MEMBERS"))
        bundle.putString("CONTACT_ID", intent.getStringExtra("CONTACT_ID"))
        val fragment = AddDealsFragment()
        fragment.arguments = bundle


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.idMain, fragment, "AddDealsActivity")
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
