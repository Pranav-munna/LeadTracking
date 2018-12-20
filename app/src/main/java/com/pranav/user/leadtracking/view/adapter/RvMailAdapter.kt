package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pranav.user.leadtracking.R
import de.hdodenhof.circleimageview.CircleImageView


class RvMailAdapter(var context: Context) : RecyclerView.Adapter<RvMailAdapter.ViewHolder>() {

    lateinit var list: List<String>
    var size = 0
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_mail_listing, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwLetter.text = list[p1]
        p0.txtVwMail.text = list[p1]
//        p0.txtVwfName.text = name[p1].split("##")[1]
    }

    override fun getItemCount(): Int {
        return size
    }

    fun set() {
        var text = context.getSharedPreferences("email", Context.MODE_PRIVATE).getString("EMAIL", "")
        if (text.trim().isNotEmpty()) {
            list = text.split(",")
            size = list.size
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtVwLetter: TextView = itemView.findViewById(R.id.txtVwLetter)
        var txtVwMail: TextView = itemView.findViewById(R.id.txtVwMail)
//        var txtVwfName: TextView


        init {
            /*txtVwName
            txtVwfName = itemView.findViewById(R.id.txtVwfName)
            imgVwDelete = itemView.findViewById(R.id.imgVwDelete)*/

        }

    }

}