package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pranav.user.leadtracking.R
import de.hdodenhof.circleimageview.CircleImageView


class RvTeamMembersAdapter(var context: Context) : RecyclerView.Adapter<RvTeamMembersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_team_members, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//        p0.txtVwName.text = "James Williamsss..."
    }

    override fun getItemCount(): Int {
        return 12
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var txtVwName: TextView
//        lateinit var cirImgVwLogo: CircleImageView


        init {
//            txtVwName = itemView.findViewById(R.id.txtVwName)




        }

    }


}