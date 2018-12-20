package com.pranav.user.leadtracking.view.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.pranav.user.leadtracking.R

class CupActivity : AppCompatActivity() {

    lateinit var imgVwCup: ImageView
    lateinit var txtVwTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cup)

        imgVwCup = findViewById(R.id.imgVwCup)
        txtVwTitle = findViewById(R.id.txtVwTitle)

        val dm = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        this.window.setLayout(width, (height * .8).toInt())
        this.window.setGravity(Gravity.BOTTOM)

        var Flag = intent.getStringExtra("Flag")
        var txtVwClose = findViewById<TextView>(R.id.txtVwClose)

        if (Flag != "0") {
            txtVwTitle.text = "Better luck next time!"
            imgVwCup.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_better_luck))
        }

        txtVwClose.setOnClickListener { onBackPressed() }


    }
}
