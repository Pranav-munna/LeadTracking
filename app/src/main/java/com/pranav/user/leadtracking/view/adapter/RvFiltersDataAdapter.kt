package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.GroupResponce


class RvFiltersDataAdapter(var context: Context) : RecyclerView.Adapter<RvFiltersDataAdapter.ViewHolder>() {
    lateinit var list: Array<GroupResponce>
    private lateinit var mcallBack: CallBack
    var mark = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_filter_details, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.chkBx.text = list[p1].accountName
        if (mark == list[p1].accountName) {
            p0.chkBx.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<GroupResponce>, marked: String) {
        mark = marked
        list = responce
    }

    fun setCallBack(filtersFragment: Any?) {
        mcallBack = filtersFragment as CallBack
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chkBx: CheckBox = itemView.findViewById(R.id.chkBx)


        init {
            chkBx.setOnClickListener {
                mcallBack.addAccount(list[adapterPosition].accountName!!, list, adapterPosition)
            }

        }

    }

    interface CallBack {
        fun addAccount(data: String, list: Array<GroupResponce>, pos: Int) {}
    }

}