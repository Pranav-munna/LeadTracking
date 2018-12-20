package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.DealDetailResponce
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class RvContactAdapter(var context: Context) : RecyclerView.Adapter<RvContactAdapter.ViewHolder>() {
    var items = ArrayList<DealDetailResponce>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_contacts, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (items[p1 + 1].image.toString() == "null")
            p0.txtVwNameTop.text = items[p1 + 1].name.toString()
        else
            Glide.with(context)
                    .load(items[p1 + 1].image.toString())
                    .into(p0.cirImgVwprofileImg)

        p0.txtVwName.text = items[p1 + 1].name
    }

    override fun getItemCount(): Int {
        return items.size - 1
    }

    fun set(responce: Array<DealDetailResponce>) {
        val list = ArrayList(Arrays.asList(*responce))
        items = list
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwNameTop: TextView = itemView.findViewById(R.id.txtVwNameTop)
        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var cirImgVwprofileImg: CircleImageView = itemView.findViewById(R.id.cirImgVwprofileImg)


        init {


        }

    }


}