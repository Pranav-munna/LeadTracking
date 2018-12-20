package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce2.Option


class RvGridMultiSelectAdapter(var context: Context) : RecyclerView.Adapter<RvGridMultiSelectAdapter.ViewHolder>() {


    private lateinit var cardViewCallBackSelect: CardViewCallBackSelect
    var selectItem = ArrayList<String>()
    lateinit var q_option: List<Option>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_grid_select, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var count = 0
        while (count != selectItem.size) {
            if (p1.toString() == selectItem[count].split("&lt&")[0].trim()) {
                /* try {*/
                p0.cardVwSmall.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                /*} catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }*/
            }
            count++
        }
        p0.txtVwContent1.text = q_option[p1].getAdditionalProperties()["title"].toString()
    }

    override fun getItemCount(): Int {
        return q_option.size
    }

    fun setCallBack(dealQuestionsFragment: Any) {
        cardViewCallBackSelect = dealQuestionsFragment as CardViewCallBackSelect
    }

    fun set(selectItems: java.util.ArrayList<String>) {
        selectItem = selectItems

    }

    fun setdata(q_options: List<Option>) {
        q_option = q_options
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardVwSmall: CardView = itemView.findViewById(R.id.cardVwSmall)
        var txtVwContent1: TextView = itemView.findViewById(R.id.txtVwContent1)

        init {
            cardVwSmall.setOnClickListener {
                cardViewCallBackSelect.setCallbackselect(adapterPosition.toString() + " &lt& " + txtVwContent1.text.toString())
            }
        }
    }

    interface CardViewCallBackSelect {
        fun setCallbackselect(pos: String)
    }

}