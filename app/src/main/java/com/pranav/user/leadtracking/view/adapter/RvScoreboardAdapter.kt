package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.ScoreboardResponce
import com.pranav.user.leadtracking.view.activity.ScoreBoardProfileActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RvScoreboardAdapter(var context: Context) : RecyclerView.Adapter<RvScoreboardAdapter.ViewHolder>() {
    lateinit var items: ArrayList<ScoreboardResponce>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_scoreboard, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        try {
            p0.txtVwName.text = items[p1].user!!.firstName + " " + items[p1].user!!.lastName
        } catch (e: Exception) {
        }
        try {
            p0.txtVwMetingCnt.text = items[p1].meetings.toString()
        } catch (e: Exception) {
        }
        try {
            p0.txtVwPipCnt.text = numberformat(items[p1].signed!!.toDouble()) + " / " + numberformat(items[p1].pipeline!!.toDouble())
        } catch (e: Exception) {
        }
        try {
            var rat = (items[p1].signed!!.toFloat() / (items[p1].signed!!.toFloat() + items[p1].pipeline!!.toFloat()))
            p0.ratBarValue.rating = rat
        } catch (e: Exception) {
        }

        if (p1 == 0)
            p0.imgVwTrophy.setImageResource(R.drawable.trophy_g)
        else if (p1 == 1)
            p0.imgVwTrophy.setImageResource(R.drawable.trophy_s)
        else
            p0.imgVwTrophy.setImageResource(R.drawable.trophy_b)
        try {
            if (items[p1].user!!.profileImage != null)
                Glide.with(context)
                        .asBitmap()
                        .load(items[p1].user!!.profileImage)
                        .into(p0.cirImgProfile)
        } catch (e: Exception) {
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun numberformat(number: Double): String {
        var numberString = ""
        if (Math.abs(number / 1000000) > 1) {
            numberString = String.format("%.1f", (number / 1000000)) + "M"
        } else if (Math.abs(number / 1000) > 1) {
            numberString = String.format("%.1f", (number / 1000)) + "K"
        } else {
            numberString = number.toString()
        }
        return numberString
    }

    fun set(arrayList: ArrayList<ScoreboardResponce>) {
        items = arrayList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwName: TextView
        var txtVwMetingCnt: TextView
        var txtVwPipCnt: TextView
        var ratBarValue: RatingBar
        var imgVwTrophy: ImageView
        var cirImgProfile: CircleImageView
        var llScordBoard: LinearLayout


        init {
            txtVwName = itemView.findViewById(R.id.txtVwName)
            txtVwMetingCnt = itemView.findViewById(R.id.txtVwMetingCnt)
            txtVwPipCnt = itemView.findViewById(R.id.txtVwPipCnt)
            ratBarValue = itemView.findViewById(R.id.ratBarValue)
            imgVwTrophy = itemView.findViewById(R.id.imgVwTrophy)
            cirImgProfile = itemView.findViewById(R.id.cirImgProfile)
            llScordBoard = itemView.findViewById(R.id.llScordBoard)


            llScordBoard.setOnClickListener {
//                Toast.makeText(context,items[adapterPosition].user!!.designationId.toString(),Toast.LENGTH_SHORT).show()
                val next = Intent(context, ScoreBoardProfileActivity::class.java)
                next.putExtra("IMAGE", items[adapterPosition].user!!.profileImage)
                next.putExtra("F_NAME", items[adapterPosition].user!!.firstName)
                next.putExtra("L_NAME", items[adapterPosition].user!!.lastName)
                next.putExtra("DESIGNATION_ID", items[adapterPosition].user!!.designationId.toString())
                next.putExtra("LOCATION", items[adapterPosition].user!!.location)
                next.putExtra("EMAIL", items[adapterPosition].user!!.email)
                next.putExtra("NUMBER", items[adapterPosition].user!!.contactNumber)
                next.putExtra("OFFICE_NUMBER", items[adapterPosition].user!!.officeNumber)
                next.putExtra("ABOUT", items[adapterPosition].user!!.about)
                context.startActivity(next)
            }

        }

    }


}