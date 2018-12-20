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
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce2.Answer
import com.pranav.user.leadtracking.controller.responce2.Option


class RvGridSingleSelectAnswerAdapter(var context: Context) : RecyclerView.Adapter<RvGridSingleSelectAnswerAdapter.ViewHolder>() {


    private lateinit var callBackSingleSelect: CallBackSingleSelect
    var selectItem = ""
    lateinit var option: List<Option>
    lateinit var a_answers: List<Answer>
    var q_pos = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_grid_select, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (a_answers[0].optionId == option[p1].id) {
            p0.cardVwSmall.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
            if (option[p1].getAdditionalProperties()["has_metadata"].toString() == "1") {
                p0.edtTxtContent.setText(a_answers[0].metadata!![0].getAdditionalProperties()["value"].toString())
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

    fun set(selectItems: String) {
        selectItem = selectItems
    }

    fun setdata(options: List<Option>, a_answer: List<Answer>, p1: Int) {
        option = options
        a_answers = a_answer
        q_pos = p1
    }

    fun setCallBack(rvAllQuestionAdapter: Any?) {
        callBackSingleSelect = rvAllQuestionAdapter as CallBackSingleSelect
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwContent: TextView = itemView.findViewById(R.id.txtVwContent)
        var txtVwContent1: TextView = itemView.findViewById(R.id.txtVwContent1)
        var edtTxtContent: EditText = itemView.findViewById(R.id.edtTxtContent)
        var cardVwSmall: CardView = itemView.findViewById(R.id.cardVwSmall)
        var cardVwLarge: CardView = itemView.findViewById(R.id.cardVwLarge)

        init {
            cardVwSmall.setOnClickListener {
                callBackSingleSelect.setCallbackSingleselectData(option[adapterPosition].id.toString(), q_pos)
            }

            edtTxtContent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    callBackSingleSelect.setCallbackSingleselect(s.toString(), q_pos.toString())
                }

            })
        }
    }

    interface CallBackSingleSelect {
        fun setCallbackSingleselect(data: String, pos: String)
        fun setCallbackSingleselectData(data: String, q_pos: Int)
    }

}