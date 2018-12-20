package com.pranav.user.leadtracking.view.adapter

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
import com.pranav.user.leadtracking.controller.responce2.Option


class RvGridPercentageAdapter(var context: Context) : RecyclerView.Adapter<RvGridPercentageAdapter.ViewHolder>() {


    private lateinit var cardViewCallBack: CardViewCallBack
    var expands = ArrayList<String>()
    lateinit var q_option: List<Option>
    var dummy_value = ""
    var opportunity_field = ""
    var str_data = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_grid_percentage, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        var count = 0
        while (count != expands.size) {
            if (p1.toString() == expands[count].split("&lt&")[0].trim()) {
                try {
                    p0.edtTxtContent.setText(expands[count].split("&lt&")[1].trim())
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
                /*dummy_value = dummy_value + """{"metadata":"""" +
                        expands[count].split("&lt&")[1].trim() + """","opportunity_field":"""" + opportunity_field + """","option_id":"""" +
                        q_option[p1].id + """"},"""*/

                p0.cardVwLarge.visibility = View.VISIBLE
                p0.cardVwSmall.visibility = View.GONE
            }
            count++
        }
        p0.txtVwContent.text = q_option[p1].getAdditionalProperties()["title"].toString()
        p0.txtVwContent1.text = q_option[p1].getAdditionalProperties()["title"].toString()
    }

    override fun getItemCount(): Int {
        return q_option.size
    }

    fun setCallBack(dealQuestionsFragment: Any) {
        cardViewCallBack = dealQuestionsFragment as CardViewCallBack
    }

    fun set(expand: ArrayList<String>) {
        expands = expand
    }

    fun setdata(q_options: List<Option>, q_opportunity_field: String) {
        q_option = q_options
        opportunity_field = q_opportunity_field
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwContent: TextView = itemView.findViewById(R.id.txtVwContent)
        var txtVwContent1: TextView = itemView.findViewById(R.id.txtVwContent1)
        var edtTxtContent: EditText = itemView.findViewById(R.id.edtTxtContent)
        var cardVwSmall: CardView = itemView.findViewById(R.id.cardVwSmall)
        var cardVwLarge: CardView = itemView.findViewById(R.id.cardVwLarge)

        init {

            cardVwSmall.setOnClickListener {
                /*if (edtTxtContent.text.trim().isNotEmpty())
                    dummy_value = dummy_value + """{"metadata":"""" +
                            edtTxtContent.text.trim().toString() + """","opportunity_field":"""" + opportunity_field + """","option_id":"""" +
                            q_option[adapterPosition].id + """"},"""*/

                cardViewCallBack.setCallback(adapterPosition.toString(), dummy_value)
            }
            cardVwLarge.setOnClickListener {
                /*dummy_value = dummy_value.replace("""{"metadata":"""" +
                        edtTxtContent.text.trim().toString() + """","opportunity_field":" """ + opportunity_field + """","option_id":"""" +
                        q_option[adapterPosition].id + """"},""", "")*/
                cardViewCallBack.setCallback(adapterPosition.toString(), dummy_value)
            }

            edtTxtContent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().trim().isNotEmpty())
                        if (s.toString().trim().substring(s.toString().trim().length - 1, s.toString().trim().length) != "%")
                            edtTxtContent.setText(s.toString() + "%")
                    try {
                        Toast.makeText(context, s.toString().length, Toast.LENGTH_SHORT).show()
                        edtTxtContent.setSelection(s.toString().length - 1)
                    } catch (e: Exception) {
                    }

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    cardViewCallBack.settexts(adapterPosition.toString(), s.toString() + " &lt& " + q_option[adapterPosition].id)
                    str_data = s.toString()
                    try {
                        edtTxtContent.setSelection(str_data.length - 1)
                    } catch (e: Exception) {
                    }
//                    Toast.makeText(context, start.toString() + " " + count + " " + before, Toast.LENGTH_SHORT).show()
                }

            })

        }
    }

    interface CardViewCallBack {
        fun setCallback(pos: String, dummy_value: String)
        fun settexts(pos: String, data: String)
    }

}