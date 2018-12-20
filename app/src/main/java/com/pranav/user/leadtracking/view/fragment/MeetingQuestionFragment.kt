package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
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
import com.pranav.user.leadtracking.controller.request.FeedbackQuestionsRequest
import com.pranav.user.leadtracking.controller.request.QuestionsOneLevelRequest
import com.pranav.user.leadtracking.controller.request.SetMeetingRequest
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce
import com.pranav.user.leadtracking.controller.responce.QuestionsOneLevelResponce
import com.pranav.user.leadtracking.controller.responce.SetMeetingResponce
import com.pranav.user.leadtracking.model.QuestionsOneLevelModel
import com.pranav.user.leadtracking.view.adapter.RvGridMultiSelectAdapter
import com.pranav.user.leadtracking.view.adapter.RvGridPercentageAdapter
import com.pranav.user.leadtracking.view.adapter.RvGridSingleSelectAdapter
import faranjit.currency.edittext.CurrencyEditText
import java.text.SimpleDateFormat
import java.util.*

class MeetingQuestionFragment : Fragment(), RvGridPercentageAdapter.CardViewCallBack, RvGridMultiSelectAdapter.CardViewCallBackSelect, RvGridSingleSelectAdapter.CardViewCallBackSelect {

    lateinit var txtVwBack: TextView
    lateinit var txtVwQuestion: TextView
    lateinit var txtVwcalander: TextView

    lateinit var btnNextSave: Button
    lateinit var btnPrev: Button

    lateinit var includeLayouttextVw: LinearLayout
    lateinit var includeLayoutnumberVw: LinearLayout
    lateinit var includeLayoutcalender: LinearLayout
    lateinit var includeLayoutcurrency: LinearLayout

    internal lateinit var edtTxtCurrency: CurrencyEditText
    internal lateinit var edtTxt: EditText
    internal lateinit var edtTxtNumber: EditText

    internal lateinit var gridView: RecyclerView

    private lateinit var rvGridSingleSelectAdapter: RvGridSingleSelectAdapter
    private lateinit var rvGridPercentageAdapter: RvGridPercentageAdapter
    private lateinit var rvGridMultiSelectAdapter: RvGridMultiSelectAdapter

    var Q_item = ArrayList<QuestionsOneLevelModel>()
    var selectItems = ArrayList<String>()
    var QuestCount = 0
    lateinit var progressDialog: Dialog
    var nextFlag = 0
    var dummy_answers = ""
    var cur_acl: Long = 0
    var myCalendar = Calendar.getInstance()!!
    var gridPercentageFlag = 0
    var expand = ArrayList<String>()
    var percentageCount = 0
    var answers = ""
    var selectItem = ""
    var singleselectGridData = ""

    var AGENDA = ""
    var CONTACTS = ""
    var DATE = ""
    var TIME = ""
    var SUBJECT = ""
    var LEAD_ID = ""
    var FLAG = ""

    var C_YEAR = 2019
    var C_MONTH = 2
    var C_DATE = 2
    var C_Hr_START = 2
    var C_Hr_END = 3
    var C_MIN = 30

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.meeting_question_activity, container, false)
        expand.clear()

        val prefs = activity!!.getSharedPreferences("email", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("EMAIL", "")
        editor.apply()

        txtVwBack = mView.findViewById(R.id.txtVwBack)
        includeLayouttextVw = mView.findViewById(R.id.includeLayouttextVw)
        includeLayoutcalender = mView.findViewById(R.id.includeLayoutcalender)
        includeLayoutcurrency = mView.findViewById(R.id.includeLayoutcurrency)
        includeLayoutnumberVw = mView.findViewById(R.id.includeLayoutnumberVw)
        txtVwQuestion = mView.findViewById(R.id.txtVwQuestion)
        gridView = mView.findViewById(R.id.gridView)
        edtTxt = mView.findViewById(R.id.edtTxt)
        txtVwcalander = mView.findViewById(R.id.txtVwcalander)
        edtTxtNumber = mView.findViewById(R.id.edtTxtNumber)
        edtTxtCurrency = mView.findViewById(R.id.edtTxtCurrency)
        btnNextSave = mView.findViewById(R.id.btnNextSave)
        btnPrev = mView.findViewById(R.id.btnPrev)

        AGENDA = arguments!!.getString("AGENDA")
        CONTACTS = arguments!!.getString("CONTACTS")
        DATE = arguments!!.getString("DATE")
        TIME = arguments!!.getString("TIME")
        SUBJECT = arguments!!.getString("SUBJECT")
        LEAD_ID = arguments!!.getString("LEAD_ID")
        FLAG = arguments!!.getString("FLAG")

        culateTime()

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressDialog.show()

        cur_acl = myCalendar.timeInMillis

        QuestionsOneLevelRequest(activity!!, ResponceProcessorQuestion()).MeetingQuestion()

        txtVwBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorQuestion : ProcessResponcceInterphase<Array<QuestionsOneLevelResponce>> {
        override fun processResponce(responce: Array<QuestionsOneLevelResponce>) {
            QuestCount = 0
            Q_item.clear()
            while (QuestCount != responce.size) {
                Q_item.add(/*QuestCount,*/ QuestionsOneLevelModel(responce[QuestCount].id.toString(),
                        responce[QuestCount].getAdditionalProperties()["title"].toString(),
                        responce[QuestCount].field!!.getAdditionalProperties()["type"].toString(),
                        responce[QuestCount].getAdditionalProperties()["opportunity_field"].toString(),
                        responce[QuestCount].options!!, " ",
                        responce[QuestCount].getAdditionalProperties()["sort_order"].toString().toInt(),
                        "-1"))
                QuestCount++
            }
            sortmaodel(Q_item)
            QuestCount = 0
            displayQuestion()
        }

        override fun processResponceError(responce: Any?) {
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
                            Toast.makeText(activity, "100% required", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }
                    answers = answers + """"""" + Q_item[QuestCount].Q_Id + """":[""" + dummy_answers + "],"
                    QuestCount++
                    Log.e("answer next", answers)
                    displayQuestion()

                } else {
                    if (gridPercentageFlag == 1) {
                        var gridCount = 0
                        dummy_answers = ""
//                    T oast.makeText(activity, expand.size.toString(), T oast.LENGTH_SHORT).show()
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
//                    T oast.makeText(activity!!, CalendarContract.Events.CALENDAR_ID.toString(), T oast.LENGTH_SHORT).show()

                    //new code
                    /*var next = Intent(activity!!, AddCalanderActivity::class.java)
                    next.putExtra("AGENDA", AGENDA) 1
                    next.putExtra("CONTACTS", CONTACTS)
                    next.putExtra("DATE", DATE)
                    next.putExtra("TIME", TIME) 1
                    next.putExtra("SUBJECT", SUBJECT) 1
                    startActivity(next)
                    activity!!.finish()*/
                    if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

                        val startMillis: Long = Calendar.getInstance().run {
                            set(C_YEAR, C_MONTH, C_DATE, C_Hr_START, C_MIN)
                            timeInMillis
                        }
                        val endMillis: Long = Calendar.getInstance().run {
                            set(C_YEAR, C_MONTH, C_DATE, C_Hr_END, C_MIN)
                            timeInMillis
                        }
                        if (FLAG == "DEAL") {
                            FeedbackQuestionsRequest(activity!!, ResponceProcessorQAnswers()).MeetingQuestion("{$answers}", LEAD_ID)

                            val calID: Long = 3
                            val cal = Calendar.getInstance()
                            val intent = Intent(Intent.ACTION_EDIT)
                            intent.type = "vnd.android.cursor.item/event"
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                            intent.putExtra("allDay", false)
                            intent.putExtra("rrule", "FREQ=YEARLY")
                            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                            intent.putExtra(CalendarContract.Events.TITLE, SUBJECT)
                            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Agenda: $AGENDA ")
                            startActivity(intent)

                            SetMeetingRequest(activity!!, ResponceProcessorSetMEtings())
                                    .setmeeting(AGENDA
                                            , CONTACTS
                                            , "0"
                                            , "$DATE ${TIME.split(" ")[0]}:00"
                                            , SUBJECT
                                            , "0"
                                            , "1"
                                            , LEAD_ID)
                        } else {
                            Log.e("AGENDA", AGENDA)
                            Log.e("CONTACTS", CONTACTS)
                            Log.e("LOCATION", "0")
                            Log.e("DATE", "$DATE ${TIME.split(" ")[0]}:00")
                            Log.e("SUBJECT", SUBJECT)
                            Log.e("TYPE", TIME)


                            SetMeetingRequest(activity!!, ResponceProcessorSetMEtings())
                                    .setmeetingNetwork(AGENDA
                                            , CONTACTS
                                            , "0"
                                            , "$DATE ${TIME.split(" ")[0]}:00"
                                            , SUBJECT
                                            , "0"
                                            , TIME)

                        }


                    } else {
                        Toast.makeText(activity!!, "CALENDAR permission denied", Toast.LENGTH_SHORT).show()
                    }
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
            } /*else {
                if (TabCount != 0) {
                }
            }*/
        }

    }

    inner class ResponceProcessorQAnswers : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
//            Toast.makeText(activity, responce.message, Toast.LENGTH_SHORT).show()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorSetMEtings : ProcessResponcceInterphase<SetMeetingResponce> {
        override fun processResponce(responce: SetMeetingResponce) {
//            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            if (FLAG != "DEAL") {
//                Toast.makeText(activity, responce.getAdditionalProperties()["meeting_id"].toString(), Toast.LENGTH_SHORT).show()
                FeedbackQuestionsRequest(activity!!, ResponceProcessorQAnswers()).MeetingQuestion("{$answers}", responce.getAdditionalProperties()["meeting_id"].toString())
                val startMillis: Long = Calendar.getInstance().run {
                    set(C_YEAR, C_MONTH, C_DATE, C_Hr_START, C_MIN)
                    timeInMillis
                }
                val endMillis: Long = Calendar.getInstance().run {
                    set(C_YEAR, C_MONTH, C_DATE, C_Hr_END, C_MIN)
                    timeInMillis
                }
                val calID: Long = 3
                val cal = Calendar.getInstance()
                val intent = Intent(Intent.ACTION_EDIT)
                intent.type = "vnd.android.cursor.item/event"
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                intent.putExtra("allDay", false)
                intent.putExtra("rrule", "FREQ=YEARLY")
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                intent.putExtra(CalendarContract.Events.TITLE, SUBJECT)
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "Agenda: $AGENDA ")
                startActivity(intent)
            }
            activity!!.finish()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    fun culateTime() {
//        10:14 AM 2018-11-22

        C_YEAR = DATE.split("-")[0].toInt()
        C_MONTH = DATE.split("-")[1].toInt()
        C_DATE = DATE.split("-")[2].toInt()

        C_MIN = TIME.split(":")[1].split(" ")[0].toInt()

        if (TIME.split(":")[0].toInt() == 12) {
            if (TIME.split(" ")[1] == "PM") {
                C_Hr_START = 12
                C_Hr_END = 13
            } else {
                C_Hr_START = 0
                C_Hr_END = 1
            }
        } else
            if (TIME.split(" ")[1] == "AM") {
                C_Hr_START = TIME.split(":")[0].toInt()
                C_Hr_END = TIME.split(":")[0].toInt() + 1
            } else {
                C_Hr_START = TIME.split(":")[0].toInt() + 12
                C_Hr_END = TIME.split(":")[0].toInt() + 13
            }
    }

    private fun textVw() {
        includeLayouttextVw.visibility = View.VISIBLE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE
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
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.VISIBLE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE

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

    private fun numberVw() {
        gridView.visibility = View.GONE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.VISIBLE

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

    private fun currency() {
        gridView.visibility = View.GONE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.VISIBLE
        includeLayoutnumberVw.visibility = View.GONE

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

    private fun gridPercentage() {
        gridPercentageFlag = 1
        nextFlag = 1
        gridView.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE

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
        gridView.layoutManager = gridLayoutManager


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

    private fun gridMultiSelect() {
        gridView.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE

        rvGridMultiSelectAdapter = RvGridMultiSelectAdapter(activity!!)
        gridView.adapter = rvGridMultiSelectAdapter
        rvGridMultiSelectAdapter.setCallBack(this)
        rvGridMultiSelectAdapter.setdata(Q_item[QuestCount].Q_options!!)
        gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        gridView.layoutManager = GridLayoutManager(activity, 2)//(activity, LinearLayout.VERTICAL, false)

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

    private fun gridSingleSelect() {
        gridView.visibility = View.VISIBLE
        includeLayouttextVw.visibility = View.GONE
        includeLayoutcalender.visibility = View.GONE
        includeLayoutcurrency.visibility = View.GONE
        includeLayoutnumberVw.visibility = View.GONE

        rvGridSingleSelectAdapter = RvGridSingleSelectAdapter(activity!!)
        gridView.adapter = rvGridSingleSelectAdapter
        rvGridSingleSelectAdapter.setCallBack(this)
        rvGridSingleSelectAdapter.setdata(Q_item[QuestCount].Q_options!!)
        gridView.recycledViewPool.setMaxRecycledViews(0, 0)
        gridView.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun setCallbackSingleselect(pos: String) {
        nextFlag = 1
        selectItem = pos
        dummy_answers = ""
        var gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.scrollToPositionWithOffset(pos.split("&lt&")[0].trim().toInt(), 10)
        rvGridSingleSelectAdapter.set(selectItem)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(p0: Int): Int {
                if (p0.toString() == pos.split("&lt&")[2].trim()) {
                    return 2
                }
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
                    Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].subQuestion!![cnt_extra_q].getAdditionalProperties()["sort_order"].toString().toInt(),
                    Q_item[QuestCount].Q_Id))
            cnt_extra_q++
        }
        sortmaodel(Q_item)
    }

    override fun setCallbackSingleselectData(data: String, pos: String) {
        singleselectGridData = data
        dummy_answers = if (Q_item[QuestCount].Q_opportunity_field.toString() == "null")
            """"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""
        else
            """"opportunity_field":"""" + Q_item[QuestCount].Q_opportunity_field + """",""" + """"option_id":"""" + Q_item[QuestCount].Q_options!![pos.split("&lt&")[0].trim().toInt()].id.toString() + """"}"""

        dummy_answers = "{\"metadata\":\"$data\",$dummy_answers"


        nextFlag = if (data.trim().isNotEmpty()) 1 else 0

//        T oast.makeText(activity!!, nextFlag.toString(), T oast.LENGTH_SHORT).show()
        //        {"metadata":"23",,"option_id":"126"}

    }

    fun sortmaodel(q_item: ArrayList<QuestionsOneLevelModel>) {
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
