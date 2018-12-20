package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.tab.MeetingTabDeal
import de.hdodenhof.circleimageview.CircleImageView


class RvAddContactMeetingAdapter(var context: Context) : RecyclerView.Adapter<RvAddContactMeetingAdapter.ViewHolder>() {

    lateinit var name: List<String>
    private lateinit var mcallBack: McallBack
//    var size = 1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_add_contacts, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwName.text = name[p1].split("##")[1]
        p0.txtVwfName.text = name[p1].split("##")[1]
    }

    override fun getItemCount(): Int {
        return name.size - 1
    }

    /*fun set(split_name: List<String>) {
        size = name.size - 1
        name = split_name
    }*/

    fun setCallBack(meetingTab: MeetingTabDeal) {
        mcallBack = meetingTab
    }

    fun set(split: List<String>) {
        name = split
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var txtVwName: TextView
        lateinit var cirImgVwLogo: CircleImageView
        var txtVwName: TextView
        var txtVwfName: TextView
        var imgVwDelete: ImageView


        init {
            txtVwName = itemView.findViewById(R.id.txtVwName)
            txtVwfName = itemView.findViewById(R.id.txtVwfName)
            imgVwDelete = itemView.findViewById(R.id.imgVwDelete)

            imgVwDelete.setOnClickListener {
                mcallBack.setCallback(name[adapterPosition])
            }
        }

    }

    interface McallBack {
        fun setCallback(name: String)
    }

}