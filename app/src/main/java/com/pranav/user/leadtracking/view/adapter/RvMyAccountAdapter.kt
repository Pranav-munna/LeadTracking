package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.GroupResponce


class RvMyAccountAdapter(var context: Context) : RecyclerView.Adapter<RvMyAccountAdapter.ViewHolder>() {

    lateinit var list: Array<GroupResponce>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_my_account, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwName.text = list[p1].accountName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<GroupResponce>) {
        list = responce
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)

        init {

        }

    }


}