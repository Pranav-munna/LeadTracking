package com.pranav.user.leadtracking.view.adapter

import android.annotation.SuppressLint
import android.content.Context
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


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RvGridPercentageAnswerAdapter(var context: Context) : RecyclerView.Adapter<RvGridPercentageAnswerAdapter.ViewHolder>() {


    private lateinit var percentageCallBack: PercentageCallBack
    var expands = ArrayList<String>()
    lateinit var q_option: List<Option>
    lateinit var a_answers: List<Answer>
    var opportunity_field = ""
    var Q_pos = 0
    lateinit var p0_VH: RvAllQuestionAdapter.ViewHolder

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_grid_percentage, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var count2 = 0
        while (count2 != a_answers.size) {
            if (a_answers[count2].optionId == q_option[p1].id &&
                    a_answers[count2].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "").trim().isNotEmpty()) {
                p0.edtTxtContent.setText(a_answers[count2].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "") + "%")
                p0.cardVwLarge.visibility = View.VISIBLE
                p0.cardVwSmall.visibility = View.GONE
            }
            count2++
        }

        var data = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
        count2 = 1
        while (count2 != data.size) {
            if (data[count2].split("##")[1] == q_option[p1].id.toString()) {
                p0.edtTxtContent.setText(data[count2].split("##")[0].replace("%", "") + "%")
                p0.cardVwLarge.visibility = View.VISIBLE
                p0.cardVwSmall.visibility = View.GONE
            }
            count2++
        }


        p0.txtVwContent.text = q_option[p1].getAdditionalProperties()["title"].toString()
        p0.txtVwContent1.text = q_option[p1].getAdditionalProperties()["title"].toString()
    }

    override fun getItemCount(): Int {
        return q_option.size
    }

    fun set(expand: ArrayList<String>) {
        expands = expand

    }

    fun setdata(q_options: List<Option>, q_opportunity_field: String, a_answer: List<Answer>, q_pos: Int, p0: RvAllQuestionAdapter.ViewHolder) {
        q_option = q_options
        opportunity_field = q_opportunity_field
        a_answers = a_answer
        Q_pos = q_pos
        p0_VH = p0
    }

    fun setCallBack(rvAllQuestionAdapter: Any?) {
        percentageCallBack = rvAllQuestionAdapter as PercentageCallBack
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwContent: TextView = itemView.findViewById(R.id.txtVwContent)
        var txtVwContent1: TextView = itemView.findViewById(R.id.txtVwContent1)
        var edtTxtContent: EditText = itemView.findViewById(R.id.edtTxtContent)
        var cardVwSmall: CardView = itemView.findViewById(R.id.cardVwSmall)
        var cardVwLarge: CardView = itemView.findViewById(R.id.cardVwLarge)

        init {

            cardVwSmall.setOnClickListener {
                cardVwLarge.visibility = View.VISIBLE
                cardVwSmall.visibility = View.GONE
                val prefs = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE)
                val data = prefs.getString("DATA", " ")

                val editor = prefs.edit()
                editor.putString("DATA", "$data#lt# ##${q_option[adapterPosition].id.toString()}## ")
                editor.apply()
                percentageCallBack.setPSmallCallback(Q_pos, p0_VH)
            }
            cardVwLarge.setOnClickListener {
                val data = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
                var dummy = " "
                var count2 = 1
                while (count2 != data.size) {
                    if (data[count2].split("##")[1] != q_option[adapterPosition].id.toString())
                        dummy = "$dummy#lt#${data[count2].split("##")[0]}##${data[count2].split("##")[1]}##${data[count2].split("##")[2]}"
                    count2++
                }
                val prefs = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("DATA", dummy)
                editor.apply()
                percentageCallBack.setPLargeCallback(Q_pos, p0_VH, q_option[adapterPosition].id.toString())

            }

            edtTxtContent.addTextChangedListener(object : TextWatcher {
                @SuppressLint("SetTextI18n")
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().trim().isNotEmpty())
                        if (s.toString().trim().substring(s.toString().trim().length - 1, s.toString().trim().length) != "%")
                            edtTxtContent.setText(s.toString() + "%")
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    percentageCallBack.setPTextCallback(Q_pos, p0_VH, q_option[adapterPosition].id.toString(), s.toString())
                }

            })

        }
    }

    interface PercentageCallBack {
        fun setPSmallCallback(pos: Int, p0_VH: RvAllQuestionAdapter.ViewHolder)
        fun setPLargeCallback(pos: Int, p0_VH: RvAllQuestionAdapter.ViewHolder, id: String)
        fun setPTextCallback(pos: Int, p0_VH: RvAllQuestionAdapter.ViewHolder, id: String, text: String)
    }

}