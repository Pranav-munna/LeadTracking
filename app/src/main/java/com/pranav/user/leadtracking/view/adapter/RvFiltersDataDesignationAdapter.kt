package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.view.fragment.FiltersFragment


class RvFiltersDataDesignationAdapter(var context: Context) : RecyclerView.Adapter<RvFiltersDataDesignationAdapter.ViewHolder>() {
    lateinit var list: Array<DesignationResponce>
    private lateinit var mcallBack: CallBack
    var mark = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_filter_details, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.chkBx.text = list[p1].name
        if (mark == list[p1].name) {
            p0.chkBx.isChecked = true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<DesignationResponce>, marked: String) {
        /*var list_ = ArrayList<GroupResponce>(responce)
        list.addAll(list_)*/
        mark = marked
        list = responce
    }

    fun setCallBack(filtersFragment: FiltersFragment) {
        mcallBack = filtersFragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chkBx: CheckBox


        init {
            chkBx = itemView.findViewById(R.id.chkBx)

            chkBx.setOnClickListener {
                mcallBack.addDesignation(list[adapterPosition].name!!, list, adapterPosition)
            }
        }

    }

    interface CallBack {
        fun addDesignation(data: String, list: Array<DesignationResponce>, pos: Int) {}
    }

}