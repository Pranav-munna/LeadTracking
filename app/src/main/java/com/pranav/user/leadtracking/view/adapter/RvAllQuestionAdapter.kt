package com.pranav.user.leadtracking.view.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.model.AnswersOneLevelModel
import faranjit.currency.edittext.CurrencyEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RvAllQuestionAdapter(var context: Context) : RecyclerView.Adapter<RvAllQuestionAdapter.ViewHolder>(), RvGridSingleSelectAnswerAdapter.CallBackSingleSelect, RvGridPercentageAnswerAdapter.PercentageCallBack, RvGridMultiSelectAnswerAdapter.CardViewCallBackSelect {
    override fun setCallbackselect(pos: String) {

    }

    private lateinit var callBackIntephase: CallBackIntephase
    lateinit var q_items: ArrayList<AnswersOneLevelModel>
    //    var q_extra_answer = ArrayList<QAnswersModel>()
    private lateinit var rvGridSingleSelectAnswerAdapter: RvGridSingleSelectAnswerAdapter
    private lateinit var rvGridPercentageAnswerAdapter: RvGridPercentageAnswerAdapter
    private lateinit var rvGridMultiSelectAnswerAdapter: RvGridMultiSelectAnswerAdapter
    var level = 0
    var myCalendar = Calendar.getInstance()!!
    val cur_acl = myCalendar.timeInMillis
    lateinit var tv: TextView
    var pos = 0
    lateinit var q_option: ArrayList<String>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_all_questions, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return q_items.size
    }

    fun set(q_item: ArrayList<AnswersOneLevelModel>) {
        q_items = q_item
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwQuestion.text = q_items[p1].Q_title
        if (q_items[p1].Q_type == "text")
            textVw(p0, p1)
        if (q_items[p1].Q_type == "date")
            calender(p0, p1)
        if (q_items[p1].Q_type == "number")
            numberVw(p0, p1)
        if (q_items[p1].Q_type == "single-select")
            gridSingleSelect(p0, p1)
        if (q_items[p1].Q_type == "currency")
            currency(p0, p1)
        if (q_items[p1].Q_type == "multi-select") {
            if (q_items[p1].Q_options!![0].getAdditionalProperties()["has_metadata"].toString() == "1")
                gridPercentage(p0, p1)
            else
                gridMultiSelect(p0, p1)
        }
        if (q_items[p1].Q_type == "percentage")
            gridPercentage(p0, p1)
    }

    private fun gridSingleSelect(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.VISIBLE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.GONE

        rvGridSingleSelectAnswerAdapter = RvGridSingleSelectAnswerAdapter(context)
        p0.gridView.adapter = rvGridSingleSelectAnswerAdapter
        rvGridSingleSelectAnswerAdapter.setCallBack(this)
        rvGridSingleSelectAnswerAdapter.setdata(q_items[p1].Q_options!!, q_items[p1].A_answer!!, p1)
        p0.gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        var gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                if (q_items[p1].Q_options!![p0].getAdditionalProperties()["has_metadata"].toString() == "1") {
                    return 2
                }
                return 1

            }
        }
        p0.gridView.layoutManager = gridLayoutManager
    }

    val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel(tv, pos)
    }

    private fun updateLabel(txtVwcalander: TextView, adapterPosition: Int) {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtVwcalander.text = sdf.format(myCalendar.time)
        q_items[adapterPosition].A_answer!![0].answer = sdf.format(myCalendar.time)
        callBackIntephase.setCallbackSModel(q_items)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwQuestion: TextView = itemView.findViewById(R.id.txtVwQuestion)
        var includeLayouttextVw: LinearLayout = itemView.findViewById(R.id.includeLayouttextVw)
        var includeLayoutnumberVw: LinearLayout = itemView.findViewById(R.id.includeLayoutnumberVw)
        var includeLayoutcalender: LinearLayout = itemView.findViewById(R.id.includeLayoutcalender)
        var includeLayoutcurrency: LinearLayout = itemView.findViewById(R.id.includeLayoutcurrency)
        var gridView: RecyclerView = itemView.findViewById(R.id.gridView)
        var edtTxt: EditText = itemView.findViewById(R.id.edtTxt)
        var txtVwcalander: TextView = itemView.findViewById(R.id.txtVwcalander)
        var edtTxtNumber: TextView = itemView.findViewById(R.id.edtTxtNumber)
        var edtTxtCurrency: CurrencyEditText = itemView.findViewById(R.id.edtTxtCurrency)

        init {
            edtTxt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    q_items[adapterPosition].A_answer!![0].answer = s.toString()
                    callBackIntephase.setCallbackSModel(q_items)
                }
            })

            edtTxtNumber.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    q_items[adapterPosition].A_answer!![0].answer = s.toString()
                    callBackIntephase.setCallbackSModel(q_items)
                }
            })
            edtTxtCurrency.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    q_items[adapterPosition].A_answer!![0].answer = s.toString()
                    callBackIntephase.setCallbackSModel(q_items)
                }
            })

            txtVwcalander.setOnClickListener {
                var dpDialog = DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                tv = txtVwcalander
                pos = adapterPosition
                dpDialog.datePicker.minDate = cur_acl
                dpDialog.show()
//                callBackIntephase.setCallbackSingl eselect(adapterPosition.toString())
            }
        }

    }

    private fun currency(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.GONE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.VISIBLE
        p0.includeLayoutnumberVw.visibility = View.GONE
        p0.edtTxtCurrency.showSymbol(true)
        p0.edtTxtCurrency.setText((q_items[p1].A_answer!![0].answer!!.replace("$", "").replace(",", "").toDouble() * 10).toString())

    }

    private fun numberVw(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.GONE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.VISIBLE

        p0.edtTxtNumber.text = q_items[p1].A_answer!![0].answer

    }

    private fun calender(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.GONE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.VISIBLE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.GONE

        p0.txtVwcalander.text = q_items[p1].A_answer!![0].answer

    }

    private fun textVw(p0: ViewHolder, p1: Int) {
        p0.includeLayouttextVw.visibility = View.VISIBLE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.GONE
        p0.gridView.visibility = View.GONE
        p0.edtTxt.setText(q_items[p1].A_answer!![0].answer)
    }

    private fun gridPercentage(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.VISIBLE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.GONE
        rvGridPercentageAnswerAdapter = RvGridPercentageAnswerAdapter(context)
        p0.gridView.adapter = rvGridPercentageAnswerAdapter
        rvGridPercentageAnswerAdapter.setCallBack(this)
        rvGridPercentageAnswerAdapter.setdata(q_items[p1].Q_options!!, q_items[p1].Q_opportunity_field, q_items[p1].A_answer!!, p1, p0)
        p0.gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        var gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                var count2 = 0
                while (count2 != q_items[p1].A_answer!!.size) {
                    if (q_items[p1].A_answer!![count2].optionId == q_items[p1].Q_options!![p0].id)
                        return 2
                    count2++
                }

                return 1
            }
        }
        p0.gridView.layoutManager = gridLayoutManager//(activity, LinearLayout.VERTICAL, false)


    }

    private fun gridMultiSelect(p0: ViewHolder, p1: Int) {
        p0.gridView.visibility = View.VISIBLE
        p0.includeLayouttextVw.visibility = View.GONE
        p0.includeLayoutcalender.visibility = View.GONE
        p0.includeLayoutcurrency.visibility = View.GONE
        p0.includeLayoutnumberVw.visibility = View.GONE

        var count = 0

        /*while (count != q_items[p1].Q_options!!.size) {
            q_option.add(q_items[p1].Q_options!![count].split("&lt&")[0].trim())
            count++
        }*/

        rvGridMultiSelectAnswerAdapter = RvGridMultiSelectAnswerAdapter(context)
        p0.gridView.adapter = rvGridMultiSelectAnswerAdapter
        rvGridMultiSelectAnswerAdapter.setCallBack(this)
        rvGridMultiSelectAnswerAdapter.setdata(q_items[p1].Q_options!!)

        p0.gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        p0.gridView.layoutManager = GridLayoutManager(context, 2)

    }

    fun setCallBack(allQuestionsFragment: Any?) {
        callBackIntephase = allQuestionsFragment!! as CallBackIntephase
    }

    interface CallBackIntephase {
        fun setCallbackSingleselects(q_pos: Int, data: String)
        fun setCallbackSingleselectDatas(data: String, pos: String)
        fun setCallbackSModel(data: ArrayList<AnswersOneLevelModel>)
    }

    override fun setPSmallCallback(Q_pos: Int, p0: ViewHolder) {
        var data = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
        rvGridPercentageAnswerAdapter.setdata(q_items[Q_pos].Q_options!!, q_items[Q_pos].Q_opportunity_field, q_items[Q_pos].A_answer!!, Q_pos, p0)
        var gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                var count2 = 0
                while (count2 != q_items[Q_pos].A_answer!!.size) {
                    if (q_items[Q_pos].A_answer!![count2].optionId == q_items[Q_pos].Q_options!![p0].id &&
                            q_items[Q_pos].A_answer!![count2].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "").trim().isNotEmpty())
                        return 2
                    count2++
                }
                count2 = 1
                while (count2 != data.size) {
                    if (data[count2].split("##")[1] == q_items[Q_pos].Q_options!![p0].id.toString())
                        return 2
                    count2++
                }

                return 1
            }
        }
        p0.gridView.layoutManager = gridLayoutManager//(activity, LinearLayout.VERTICAL, false)
    }

    override fun setPTextCallback(Q_pos: Int, p0: ViewHolder, id: String, text: String) {

        var data = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
        var dummy = " "
        var count2 = 1
        while (count2 != data.size) {
            dummy = if (data[count2].split("##")[1] == id)
                "$dummy#lt#$text##${data[count2].split("##")[1]}##${data[count2].split("##")[2]}"
            else
                "$dummy#lt#${data[count2].split("##")[0]}##${data[count2].split("##")[1]}##${data[count2].split("##")[2]}"

            count2++
        }
        val prefs = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("DATA", dummy)
        editor.apply()

        var p00 = 0
        while (p00 != q_items[Q_pos].Q_options!!.size) {
            var count2 = 0
            while (count2 != q_items[Q_pos].A_answer!!.size) {
                if (q_items[Q_pos].A_answer!![count2].optionId == q_items[Q_pos].Q_options!![p00].id)
                    if (q_items[Q_pos].A_answer!![count2].optionId.toString() == id) {
                        q_items[Q_pos].A_answer!![count2].metadata!![0].setAdditionalProperty("value", text)
                    }
                count2++
            }
            p00++
        }

    }

    override fun setPLargeCallback(Q_pos: Int, p0: ViewHolder, id: String) {

        var data = context.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
        rvGridPercentageAnswerAdapter.setdata(q_items[Q_pos].Q_options!!, q_items[Q_pos].Q_opportunity_field, q_items[Q_pos].A_answer!!, Q_pos, p0)
        var gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                var count2 = 0
                while (count2 != q_items[Q_pos].A_answer!!.size) {
                    if (q_items[Q_pos].A_answer!![count2].optionId == q_items[Q_pos].Q_options!![p0].id &&
                            q_items[Q_pos].A_answer!![count2].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "").trim().isNotEmpty())
                        if (q_items[Q_pos].A_answer!![count2].optionId.toString() == id) {
                            q_items[Q_pos].A_answer!![count2].metadata!![0].setAdditionalProperty("value", "")
                        } else
                            return 2
                    count2++
                }
                count2 = 1
                while (count2 != data.size) {
                    if (data[count2].split("##")[1] == q_items[Q_pos].Q_options!![p0].id.toString())
                        return 2
                    count2++
                }

                return 1
            }
        }
        p0.gridView.layoutManager = gridLayoutManager//(activity, LinearLayout.VERTICAL, false)

        callBackIntephase.setCallbackSModel(q_items)
    }

    override fun setCallbackSingleselect(data: String, pos: String) {
        callBackIntephase.setCallbackSingleselectDatas(data, pos)
    }

    override fun setCallbackSingleselectData(data: String, q_pos: Int) {
        q_items[q_pos].A_answer!![0].optionId = data.toInt()
        callBackIntephase.setCallbackSingleselects(q_pos, data)
    }

}