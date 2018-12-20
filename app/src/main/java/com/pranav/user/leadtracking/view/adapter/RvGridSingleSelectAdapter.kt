package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce2.Option
import com.pranav.user.leadtracking.view.fragment.DealQuestionsFragment


class RvGridSingleSelectAdapter(var context: Context) : RecyclerView.Adapter<RvGridSingleSelectAdapter.ViewHolder>() {


    private lateinit var cardViewCallBackSingleSelect: CardViewCallBackSelect
    var selectItem = ""
    lateinit var option: List<Option>

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_grid_select, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (p1.toString() == selectItem.split("&lt&")[0].trim()) {
            p0.cardVwSmall.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            if (option[p1].getAdditionalProperties()["has_metadata"].toString() == "1") {
                p0.cardVwLarge.visibility = View.VISIBLE
                p0.cardVwSmall.visibility = View.GONE
            }
        }
        p0.txtVwContent.text = option[p1].getAdditionalProperties()["title"].toString()
        p0.txtVwContent1.text = option[p1].getAdditionalProperties()["title"].toString()
    }

    override fun getItemCount(): Int {
        return option.size
    }

    fun setCallBack(dealQuestionsFragment: Any) {
        cardViewCallBackSingleSelect = dealQuestionsFragment as CardViewCallBackSelect
    }

    fun set(selectItems: String) {
        selectItem = selectItems

    }

    fun setdata(options: List<Option>) {
        option = options
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwContent: TextView = itemView.findViewById(R.id.txtVwContent)
        var txtVwContent1: TextView = itemView.findViewById(R.id.txtVwContent1)
        var edtTxtContent: EditText = itemView.findViewById(R.id.edtTxtContent)
        var cardVwSmall: CardView = itemView.findViewById(R.id.cardVwSmall)
        var cardVwLarge: CardView = itemView.findViewById(R.id.cardVwLarge)

        init {
            cardVwSmall.setOnClickListener {
                if (option[adapterPosition].getAdditionalProperties()["has_metadata"].toString() == "1") {
                    cardViewCallBackSingleSelect.setCallbackSingleselect(adapterPosition.toString() + " &lt& " +
                            txtVwContent1.text.toString() + " &lt& " +
                            adapterPosition)
                } else {
                    cardViewCallBackSingleSelect.setCallbackSingleselect(adapterPosition.toString() + " &lt& " +
                            txtVwContent1.text.toString() + " &lt& " +
                            "-1")
                }
            }

            edtTxtContent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    cardViewCallBackSingleSelect.setCallbackSingleselectData(s.toString(),
                            adapterPosition.toString() + " &lt& " + txtVwContent1.text.toString() +
                                    " &lt& -1")
                }

            })


        }
    }

    interface CardViewCallBackSelect {
        fun setCallbackSingleselect(pos: String)
        fun setCallbackSingleselectData(data: String, pos: String)
    }

}