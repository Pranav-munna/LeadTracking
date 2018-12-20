package com.pranav.user.leadtracking.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.*
import com.pranav.user.leadtracking.controller.responce.*
import com.pranav.user.leadtracking.controller.responce3.SubQuestion
import com.pranav.user.leadtracking.model.QuestionsOneLevelModel
import com.pranav.user.leadtracking.view.activity.AllQuestionsActivity
import com.pranav.user.leadtracking.view.activity.CupActivity
import com.pranav.user.leadtracking.view.adapter.RvGridMultiSelectAdapter
import com.pranav.user.leadtracking.view.adapter.RvGridPercentageAdapter
import com.pranav.user.leadtracking.view.adapter.RvGridSingleSelectAdapter
import faranjit.currency.edittext.CurrencyEditText
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DealQuestionsFragment : Fragment(), RvGridPercentageAdapter.CardViewCallBack, RvGridMultiSelectAdapter.CardViewCallBackSelect, RvGridSingleSelectAdapter.CardViewCallBackSelect {

    //region var

    var myCalendar = Calendar.getInstance()!!
    internal lateinit var tablayout: TabLayout
    lateinit var linearLayout2: LinearLayout
    lateinit var includeLayouttextVw: LinearLayout
    lateinit var includeLayoutnumberVw: LinearLayout
    lateinit var includeLayoutCup: ImageView
    lateinit var includeLayoutcalender: LinearLayout
    lateinit var includeLayoutcurrency: LinearLayout
    internal lateinit var edtTxt: EditText
    internal lateinit var edtTxtNumber: EditText
    internal lateinit var edtTxtCurrency: CurrencyEditText
    private lateinit var txtVwcalander: TextView
    internal lateinit var txtVwQuestion: TextView
    internal lateinit var txtVwBack: TextView
    internal lateinit var txtVwOk: TextView
    internal lateinit var txtVwStageTitle: TextView
    internal lateinit var btnNextSave: Button
    internal lateinit var btnPrev: Button
    internal lateinit var btnAllQuestions: Button
    internal lateinit var gridView: RecyclerView
    private lateinit var rvGridPercentageAdapter: RvGridPercentageAdapter
    private lateinit var rvGridMultiSelectAdapter: RvGridMultiSelectAdapter
    private lateinit var rvGridSingleSelectAdapter: RvGridSingleSelectAdapter
    var accountF = ""
    var FLAG = ""
    var item = ArrayList<QuestionsOneLevelResponce>()
    var Q_item = ArrayList<QuestionsOneLevelModel>()
    var itemextre = ArrayList<SubQuestion>()
    var QuestCount = 0
    var TabCount = 0
    lateinit var inflate: View
    var expand = ArrayList<String>()
    var selectItems = ArrayList<String>()
    var selectItem = ""
    var singleselectGridData = ""
    var LEVEL_ID = ""
    var DealsRequestcount = 1
    var cupFlag = 1
    var percentageCount = 0

    var approvals = ArrayList<String>()
    var LEVEL_TITLE = ArrayList<String>()
    var is_exceptional = ArrayList<String>()
    var approvalsString = ""
    lateinit var dialog_approval: Dialog

    var answers = ""
    var dummy_answers = ""
    var cur_acl: Long = 0
    var gridPercentageFlag = 0
    var nextFlag = 0
    lateinit var progressDialog: Dialog


    //endregion
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_deal_questions, container, false)
        expand.clear()

        //region findViewById

        tablayout = mView.findViewById(R.id.tablayout)
        includeLayouttextVw = mView.findViewById(R.id.includeLayouttextVw)
        includeLayoutcalender = mView.findViewById(R.id.includeLayoutcalender)
        includeLayoutcurrency = mView.findViewById(R.id.includeLayoutcurrency)
        includeLayoutnumberVw = mView.findViewById(R.id.includeLayoutnumberVw)
        includeLayoutCup = mView.findViewById(R.id.includeLayoutCup)
        txtVwQuestion = mView.findViewById(R.id.txtVwQuestion)
        btnNextSave = mView.findViewById(R.id.btnNextSave)
        btnPrev = mView.findViewById(R.id.btnPrev)
        gridView = mView.findViewById(R.id.gridView)
        edtTxt = mView.findViewById(R.id.edtTxt)
        txtVwcalander = mView.findViewById(R.id.txtVwcalander)
        edtTxtCurrency = mView.findViewById(R.id.edtTxtCurrency)
        edtTxtNumber = mView.findViewById(R.id.edtTxtNumber)
        txtVwBack = mView.findViewById(R.id.txtVwBack)
        btnAllQuestions = mView.findViewById(R.id.btnAllQuestions)
        txtVwStageTitle = mView.findViewById(R.id.txtVwStageTitle)
        linearLayout2 = mView.findViewById(R.id.linearLayout2)

//endregion

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)

        val prefs = activity!!.getSharedPreferences("LT_STAGE", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        FLAG = arguments!!.getString("FLAG")
        accountF = arguments!!.getString("ID")
        LEVEL_ID = arguments!!.getString("LEVEL_ID")

        if (FLAG == "1") {
            txtVwBack.visibility = View.INVISIBLE
            btnAllQuestions.visibility = View.INVISIBLE
        }

        cur_acl = myCalendar.timeInMillis

        progressDialog.show()
        QuestionAllLevelsRequest(activity!!, RespoceProcessorLevels()).AllLevel()

        dialog_approval = Dialog(activity!!)
        dialog_approval.setContentView(R.layout.dialog_approval)
        dialog_approval.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog_approval.setCancelable(false)
        txtVwOk = dialog_approval.findViewById(R.id.txtVwOk)
        txtVwOk.setOnClickListener { dialog_approval.dismiss() }
        tablayout.setSelectedTabIndicator(R.drawable.ic_tab_bottom)
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
        tablayout.background = (ContextCompat.getDrawable(activity!!, R.drawable.ic_tab_back))

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                var cnt = 0
                var flg = 0
                while (cnt != approvals.size) {
                    if (p0!!.position == approvals[cnt].split("&lt&")[0].trim().toInt()) {
                        flg = 1
                        approvalsString = approvals[cnt].split("&lt&")[1].trim()
                        DealsRequestcount = 1
                        DealsRequest(activity!!, ResponceProcessorDeal()).dealList(DealsRequestcount)
                    }
                    cnt++
                }
                if (flg == 0) {
                    TabCount = p0!!.position
                    txtVwStageTitle.text = LEVEL_TITLE[p0!!.position]
                    editor.putString("STAGE", LEVEL_TITLE[p0!!.position])
                    editor.apply()
                    QuestionsOneLevelRequest(activity!!, ResponceProcessorQuestions()).Question(accountF, (p0!!.position + 1).toString())
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                var cnt = 0
                var flg = 0
                while (cnt != approvals.size) {
                    if (p0!!.position + 1 == approvals[cnt].split("&lt&")[0].trim().toInt()) {
                        flg = 1
                        approvalsString = approvals[cnt].split("&lt&")[1].trim()
                        DealsRequestcount = 1
                        progressDialog.show()
                        DealsRequest(activity!!, ResponceProcessorDeal()).dealList(DealsRequestcount)
                    }
                    cnt++
                }
                if (flg == 0) {
                    TabCount = p0!!.position
                    progressDialog.show()
                    txtVwStageTitle.text = LEVEL_TITLE[p0!!.position]
                    editor.putString("STAGE", LEVEL_TITLE[p0!!.position])
                    editor.apply()
                    QuestionsOneLevelRequest(activity!!, ResponceProcessorQuestions()).Question(accountF, (p0!!.position + 1).toString())
                }
            }
        })

        txtVwBack.setOnClickListener { activity!!.onBackPressed() }
        btnAllQuestions.setOnClickListener {
            var next = Intent(activity, AllQuestionsActivity::class.java)
            next.putExtra("ID", accountF)
            startActivity(next)
            activity!!.finish()
        }

        return mView
    }

    inner class ResponceProcessorDeal : ProcessResponcceInterphase<DealsResponce> {
        override fun processResponce(responce: DealsResponce) {
            val prefs = activity!!.getSharedPreferences("LT_STAGE", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            var count = 1
            while (count != responce.data!!.size + 1) {
                if (responce.data!![count - 1].id.toString() == accountF) {
                    if (responce.data!![count - 1].getAdditionalProperties()[approvalsString] == true) {
                        TabCount = tablayout.selectedTabPosition
                        progressDialog.show()
                        txtVwStageTitle.text = LEVEL_TITLE[TabCount]
                        editor.putString("STAGE", LEVEL_TITLE[TabCount])
                        editor.apply()
                        QuestionsOneLevelRequest(activity!!, ResponceProcessorQuestions()).Question(accountF, (TabCount + 1).toString())
                    } else {
                        progressDialog.dismiss()
                        dialog_approval.show()
                        txtVwStageTitle.text = LEVEL_TITLE[TabCount]
                        editor.putString("STAGE", LEVEL_TITLE[TabCount])
                        editor.apply()
                        tablayout.setScrollPosition(TabCount, 0f, true)
                    }
                }
                count++
            }
            if (count != 1 && count == responce.data!!.size + 1) {
                DealsRequest(activity!!, ResponceProcessorDeal()).dealList(++DealsRequestcount)
            }
        }

        override fun processResponceError(responce: Any?) {
            Log.e("onTabSelected3", "nooooooooooooooo")
        }

    }

    inner class RespoceProcessorLevels : ProcessResponcceInterphase<Array<QuestionAllLevelsResponce>> {
        override fun processResponce(responce: Array<QuestionAllLevelsResponce>) {
            val prefs = activity!!.getSharedPreferences("LT_STAGE", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            var count = 1
//            approvals.clear()
            while (count != responce.size + 1) {
                LEVEL_TITLE.add(responce[count - 1].getAdditionalProperties()["title"].toString())
                is_exceptional.add(responce[count - 1].getAdditionalProperties()["is_exceptional"].toString())
                tablayout.addTab(tablayout.newTab().setText(count++.toString()))
            }
            try {
                TabCount = (LEVEL_ID.toInt() - 1)
                tablayout.setScrollPosition((LEVEL_ID.toInt() - 1), 0f, true)
                txtVwStageTitle.text = LEVEL_TITLE[TabCount]
                editor.putString("STAGE", LEVEL_TITLE[TabCount])
                editor.apply()
            } catch (e: Exception) {
            }
            count = 1
            var data = ""
            while (count != responce.size) {
                if (responce[count].getAdditionalProperties()["is_exceptional"] == 0) {
                    if (responce[count].approvals!!.isNotEmpty()) {
//                        approvals.add(responce[count].getAdditionalProperties()["rank"].toString() + " &lt& " + responce[count].approvals!![0].name.toString())
                        data = responce[count].approvals!![0].name.toString()
                    } else
                        approvals.add(responce[count].getAdditionalProperties()["rank"].toString() + " &lt& " + data)

                }
                count++
            }
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorQuestions : ProcessResponcceInterphase<Array<QuestionsOneLevelResponce>> {

        override fun processResponce(responce: Array<QuestionsOneLevelResponce>) {
//            Toast.makeText(activity!!, responce[0].levels!![0].getAdditionalProperties()["title"].toString(), Toast.LENGTH_SHORT).show()
            if (responce[0].getAdditionalProperties()["answers"] == null || responce[0].getAdditionalProperties()["answers"].toString() == "[]") {
                QuestCount = 0
                Q_item.clear()
                item = ArrayList(Arrays.asList(*responce))
                while (QuestCount != responce.size) {
                    Q_item.add(QuestCount, QuestionsOneLevelModel(item[QuestCount].id.toString(),
                            item[QuestCount].getAdditionalProperties()["title"].toString(),
                            item[QuestCount].field!!.getAdditionalProperties()["type"].toString(),
                            item[QuestCount].getAdditionalProperties()["opportunity_field"].toString(),
                            item[QuestCount].options!!, "",
                            /*responce[QuestCount].getAdditionalProperties()["sort_order"].toString().toInt()*/0,
                            "-1"))
                    QuestCount++
                }
//                sortmaodel(Q_item)
                QuestCount = 0
                displayQuestion()
            } else {
                cup()
            }
        }

        override fun processResponceError(responce: Any?) {
            cup()
        }
    }

    fun displayQuestion() {
        progressDialog.dismiss()
        nextFlag = 0
        txtVwQuestion.text = Q_item[QuestCount].Q_title
        if (Q_item[QuestCount].Q_type == "text")
            textVw()
        if (Q_item[QuestCount].Q_type == "multi-select") {
            if (Q_item[QuestCount].Q_options!![0].getAdditionalProperties()["has_metadata"].toString() == "1")
                gridPercentage()
            else
                gridMultiSelect()
        }
        if (Q_item[QuestCount].Q_type == "date")
            calender()
        if (Q_item[QuestCount].Q_type == "number")
            numberVw()
        if (Q_item[QuestCount].Q_type == "percentage")
            gridPercentage()
        if (Q_item[QuestCount].Q_type == "single-select")
            gridSingleSelect()
        if (Q_item[QuestCount].Q_type == "currency")
            currency()

        if (QuestCount == Q_item.size - 1)
            btnNextSave.text = "Save"
        else
            btnNextSave.text = "Next"

        btnNextSave.setOnClickListener {
            if (nextFlag == 1)
                if (btnNextSave.text == "Next") {
                    if (gridPercentageFlag == 1) {
                        if (gridPercentageCalcu() != 100) {
                            gridPercentageFlag = 1
                            Toast.makeText(activity, "Overall Percentage should be 100%", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    answers = answers + """"""" + Q_item[QuestCount].Q_Id + """":[""" + dummy_answers + "],"
                    QuestCount++
                    displayQuestion()

                } else {
                    if (gridPercentageFlag == 1) {
                        if (gridPercentageCalcu() != 100) {
                            gridPercentageFlag = 1
                            Toast.makeText(activity, "Overall Percentage should be 100%", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    if (gridPercentageFlag == 1) {
                        var gridCount = 0
                        dummy_answers = ""
                        while (gridCount != expand.size) {
                            dummy_answers = dummy_answers + """{"metadata":"""" +
                                    expand[gridCount].split("&lt&")[1].trim() + """","opportunity_field":"""" +
                                    Q_item[QuestCount].Q_opportunity_field + """","option_id":"""" +
                                    expand[gridCount].split("&lt&")[2].trim() + """"},"""
                            gridCount++
                        }
                        dummy_answers = dummy_answers.substring(0, dummy_answers.length - 1)
                        gridPercentageFlag = 0
                    }
                    answers = answers + """"""" + Q_item[QuestCount].Q_Id + """":[""" + dummy_answers + "]"
                    progressDialog.show()
                    AnswersRequest(activity!!, ResponceProcessorAnswers()).Answers("{$answers}", accountF)
                }
            else
                Toast.makeText(activity!!, "enter all fields", Toast.LENGTH_SHORT).show()

        }

        btnPrev.setOnClickListener {
            if (QuestCount != 0) {
                QuestCount--
                answers = answers.split((""""""" + Q_item[QuestCount].Q_Id + """":["""))[0]

                if (Q_item[QuestCount].Q_options!!.isNotEmpty()) {
                    var count = 0
                    while (count != -1) {
                        if (Q_item[QuestCount].Q_Id == Q_item[QuestCount + 1].Q_ref_id)
                            Q_item.removeAt(QuestCount + 1)
                        else
                            count = -1
                    }
                }
                gridPercentageFlag = 0
                displayQuestion()
            } else {
                if (TabCount != 0) {
//                    tablayout.setScrollPosition((T abCount--) - 1, 0F, true)
//                    Q  uestionsOneLevelRequest(activity!!, ResponceProcessorQuestions()).Question(accountF, (T abCount - 1).toString())
                }
            }
        }

    }

    inner class ResponceProcessorAnswers : ProcessResponcceInterphase<AnswersResponce> {
        override fun processResponce(responce: AnswersResponce) {
            cupFlag = 0
            cup()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    private fun gridPercentageCalcu(): Int {
        var gridCount = 0
        dummy_answers = ""
        percentageCount = 0
        while (gridCount != expand.size) {
            dummy_answers = dummy_answers + """{"metadata":"""" +
                    expand[gridCount].split("&lt&")[1].trim() + """","opportunity_field":"""" +
                    Q_item[QuestCount].Q_opportunity_field + """","option_id":"""" +
                    expand[gridCount].split("&lt&")[2].trim() + """"},"""

            try {
                percentageCount += expand[gridCount].split("&lt&")[1].trim().substring(0,
                        expand[gridCount].split("&lt&")[1].trim().length - 1).toInt()

            } catch (e: Exception) {
                percentageCount = 0
            }

            gridCount++
        }

//        T oast.makeText(activity!!, percentageCount.toString(), T oast.LENGTH_SHORT).show()

        try {
            dummy_answers = dummy_answers.substring(0, dummy_answers.length - 1)
        } catch (e: Exception) {

        }


        gridPercentageFlag = 0

        return percentageCount
    }

    private fun textVw() {
        includeLayouttextVw.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE
        gridView.visibility = View.GONE

        /*nextFlag = if (edtTxt.text.trim().isNotEmpty())
            1
        else
            0*/
        edtTxt.setText("")

        edtTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
                    """{"answer":"""" + s.toString() + """"}"""
                else
                    """{"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"answer":"""" + s.toString() + """"}"""

                nextFlag = if (s.toString().trim().isNotEmpty())
                    1
                else
                    0
            }
        })
    }

    private fun calender() {
        gridView.visibility = View.GONE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.VISIBLE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE

        /*nextFlag = if (txtVwcalander.text.trim().isNotEmpty())
            1
        else
            0*/
        txtVwcalander.text = ""

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        txtVwcalander.setOnClickListener {
            var dpDialog = DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            dpDialog.datePicker.minDate = cur_acl
            dpDialog.show()

        }

    }

    private fun currency() {
        gridView.visibility = View.GONE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.VISIBLE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE

        nextFlag = if (edtTxtCurrency.text.trim().isNotEmpty())
            1
        else
            0
        edtTxtCurrency.showSymbol(true)
        edtTxtCurrency.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
                    """{"answer":"""" + s.toString() + """"}"""
                else
                    """{"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"answer":"""" + s.toString() + """"}"""

                nextFlag = if (s.toString().trim().isNotEmpty()) 1 else 0
            }
        })

    }

    private fun numberVw() {
        gridView.visibility = View.GONE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.VISIBLE
        includeLayoutCup.visibility = View.GONE

        nextFlag = if (edtTxtNumber.text.trim().isNotEmpty())
            1
        else
            0

        edtTxtNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
                    """{"answer":"""" + s.toString() + """"}"""
                else
                    """{"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"answer":"""" + s.toString() + """}"""

                nextFlag = if (s.toString().trim().isNotEmpty()) 1 else 0

            }
        })

    }

    private fun cup() {
        progressDialog.dismiss()
        linearLayout2.visibility = View.INVISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        gridView.visibility = View.GONE


        includeLayoutCup.visibility = View.VISIBLE
        if (is_exceptional[TabCount] == "0") {
            txtVwQuestion.text = "Congratulations!"
            includeLayoutCup.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_cup))
        } else {
            txtVwQuestion.text = "Better luck next time!"
            includeLayoutCup.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_better_luck))
        }
        if (cupFlag == 0) {
            UpdateLevelRequest(activity!!, ResponceprocessorUpdateLevel()).UpdateLevel(accountF, (TabCount + 1).toString())
            var next = Intent(activity, CupActivity::class.java)
            next.putExtra("Flag", is_exceptional[TabCount])
            startActivity(next)
            activity!!.finish()
        } else
            cupFlag = 0
        progressDialog.dismiss()
    }

    inner class ResponceprocessorUpdateLevel : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }
    }

    private fun gridPercentage() {
        gridPercentageFlag = 1
        nextFlag = 1
        gridView.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE

        var exp_Cnt = 0
        while (exp_Cnt != expand.size) {
            if (expand[exp_Cnt].split("&lt&")[1] == " ") {
                expand.removeAt(exp_Cnt)
            } else
                exp_Cnt++
        }

        rvGridPercentageAdapter = RvGridPercentageAdapter(activity!!)
        gridView.adapter = rvGridPercentageAdapter
        rvGridPercentageAdapter.setCallBack(this)
        rvGridPercentageAdapter.set(expand)
        rvGridPercentageAdapter.setdata(Q_item[QuestCount].Q_options!!, Q_item[QuestCount].Q_opportunity_field)
        gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        var gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                var count = 0
                while (count != expand.size) {
                    if (p0.toString() == expand[count].split("&lt&")[0].trim())
                        return 2
                    count++
                }
                return 1
            }
        }
        gridView.layoutManager = gridLayoutManager//(activity, LinearLayout.VERTICAL, false)


    }

    private fun gridMultiSelect() {
        gridView.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE

        rvGridMultiSelectAdapter = RvGridMultiSelectAdapter(activity!!)
        gridView.adapter = rvGridMultiSelectAdapter
        rvGridMultiSelectAdapter.setCallBack(this)
        rvGridMultiSelectAdapter.setdata(Q_item[QuestCount].Q_options!!)
        gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        gridView.layoutManager = GridLayoutManager(activity, 2)//(activity, LinearLayout.VERTICAL, false)

    }

    private fun gridSingleSelect() {
        gridView.visibility = View.VISIBLE
        linearLayout2.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
        includeLayoutCup.visibility = View.GONE

        rvGridSingleSelectAdapter = RvGridSingleSelectAdapter(activity!!)
        gridView.adapter = rvGridSingleSelectAdapter
        rvGridSingleSelectAdapter.setCallBack(this)
        rvGridSingleSelectAdapter.setdata(Q_item[QuestCount].Q_options!!)
        gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        gridView.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun setCallback(pos: String, dummy_value: String) {
        dummy_answers = dummy_value
        var counts = 0
        var flag = 0

        while (counts != expand.size) {
//            T oast.makeText(activity!!, pos + "==" + expand[counts].split("&lt&")[0], T oast.LENGTH_SHORT).show()
            if (pos == expand[counts].split("&lt&")[0].trim()) {
                expand.remove(expand[counts])
                flag = 1
                break
            }
            counts++
        }
        if (flag == 0)
            expand.add("$pos &lt& ")
        var gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                var count = 0
                while (count != expand.size) {
                    if (p0.toString() == expand[count].split("&lt&")[0].trim())
                        return 2
                    count++
                }
                return 1
            }
        }
        gridLayoutManager.scrollToPositionWithOffset(pos.split("&lt&")[0].trim().toInt(), 10)
        rvGridPercentageAdapter.set(expand)
//        gridView.layoutManager!!.scrollToPosition(pos)
        gridView.layoutManager = gridLayoutManager
    }

    override fun settexts(pos: String, data: String) {
        var counts = 0
        var flag = 0
        gridPercentageFlag = 1
        while (counts != expand.size) {
            if (pos == expand[counts].split("&lt&")[0].trim()) {
                expand.remove(expand[counts])
                expand.add(counts, "$pos &lt& $data")
                flag = 1
                break
            }
            counts++
        }
        if (flag == 0) {
            expand.add("$pos &lt& $data")
        }
        counts = 0

//        gridPercentage()
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtVwcalander.text = sdf.format(myCalendar.time)


        dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
            """{"answer":"""" + sdf.format(myCalendar.time).toString() + """"}"""
        else
            """{"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"answer":"""" + sdf.format(myCalendar.time).toString() + """"}"""

        nextFlag = 1
    }

    override fun setCallbackselect(pos: String) {
        var counts = 0
        var flag = 0

        while (counts != selectItems.size) {
            if (pos == selectItems[counts]) {
                selectItems.remove(selectItems[counts])
                flag = 1
                break
            }
            counts++
        }
        if (flag == 0)
            selectItems.add(pos)

        nextFlag = if (selectItems.size > 0) 1
        else 0

        var gridLayoutManager = GridLayoutManager(activity, 2)

        gridLayoutManager.scrollToPositionWithOffset(pos.split("&lt&")[0].trim().toInt(), 10)
        rvGridMultiSelectAdapter.set(selectItems)
        gridView.layoutManager = gridLayoutManager
    }

    override fun setCallbackSingleselectData(data: String, pos: String) {
        singleselectGridData = data
        dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
            """"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""
        else
            """"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""

        dummy_answers = "{\"metadata\":\"$data\",$dummy_answers"


        nextFlag = if (data.trim().isNotEmpty()) 1 else 0


    }

    override fun setCallbackSingleselect(pos: String) {
        nextFlag = 1
        selectItem = pos
//        a nswers = ""
        dummy_answers = ""
        var gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.scrollToPositionWithOffset(pos.split("&lt&")[0].trim().toInt(), 10)
        rvGridSingleSelectAdapter.set(selectItem)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
//                var count = 0
//                while (count != expand.size) {
//                T oast.makeText(activity!!, p0.toString() + " " + pos.split("&lt&")[2].trim(), T oast.LENGTH_SHORT).show()
                if (p0.toString() == pos.split("&lt&")[2].trim()) {
//                    nextFlag = 0
                    return 2
                }
//                    count++
//                }
                return 1
            }
        }
        gridView.layoutManager = gridLayoutManager

        var cnt_extra_q = 0
        while (cnt_extra_q != Q_item.size) {
            if (Q_item[cnt_extra_q].Q_main_q_id == Q_item[QuestCount].Q_Id)
                Q_item.removeAt(cnt_extra_q)
            else
                cnt_extra_q++
        }




        dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
            """{"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""
        else
            """{"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""


        var q_no = QuestCount
        cnt_extra_q = 0
        while (cnt_extra_q != Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!!.size) {
            Q_item.add(/*(q_no + cnt_extra_q + 1),*/ QuestionsOneLevelModel(Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].id.toString(),
                    Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].title.toString(),
                    Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].field!!.getAdditionalProperties()["type"].toString(),
                    Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].opportunityField.toString(),
                    Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].options, Q_item[QuestCount].Q_Id,
                    /*Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].getAdditionalProperties()["sort_order"].toString().toInt()*/0,
                    Q_item[QuestCount].Q_Id))
            cnt_extra_q++
        }
//        sortmaodel(Q_item)
        //        itemextre = ite m[QuestCount].options!![pos.split("&lt&")[0].trim().toInt()].subQuestion as ArrayList<SubQuestion>

        /*ite m[QuestCount].options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![pos.split("&lt&")[0].trim().toInt()].id,
        ite m[QuestCount].options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![pos.split("&lt&")[0].trim().toInt()].options,
        ite m[QuestCount].options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![pos.split("&lt&")[0].trim().toInt()].field,
        null*/

    }

    fun sortmaodel(q_item: java.util.ArrayList<QuestionsOneLevelModel>) {
        var count = q_item.size
        for (i in 0 until count - 1)
            for (j in 0 until count - i - 1)
                if (q_item[j].Q_sort_order > q_item[j + 1].Q_sort_order) {
                    val temp = q_item[j]
                    q_item[j] = q_item[j + 1]
                    q_item[j + 1] = temp
                } else if (q_item[j].Q_sort_order == q_item[j + 1].Q_sort_order) {
                    q_item.removeAt(j)
                    count = q_item.size
                }
        Q_item = q_item
    }


}
