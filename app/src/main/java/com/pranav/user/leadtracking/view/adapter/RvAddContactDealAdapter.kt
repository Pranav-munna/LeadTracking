package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.fragment.tab.DealDetailTabDeal


class RvAddContactDealAdapter(var context: Context) : RecyclerView.Adapter<RvAddContactDealAdapter.ViewHolder>() {

    lateinit var name: List<String>
    private lateinit var mcallBack: McallBack
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

    fun set(split_name: List<String>) {
        name = split_name
    }

    fun setCallBack(dealDetailTabDeal: DealDetailTabDeal) {
        mcallBack = dealDetailTabDeal
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var txtVwfName: TextView = itemView.findViewById(R.id.txtVwfName)
        var imgVwDelete: ImageView = itemView.findViewById(R.id.imgVwDelete)


        init {

            imgVwDelete.setOnClickListener {
                mcallBack.setCallback(name[adapterPosition])
            }
        }

    }

    interface McallBack {
        fun setCallback(name: String)
    }

}