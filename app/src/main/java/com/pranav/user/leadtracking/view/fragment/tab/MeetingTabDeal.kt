package com.pranav.user.leadtracking.view.fragment.tab

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.LocationTypeRequest
import com.pranav.user.leadtracking.controller.responce.LocationResponce
import com.pranav.user.leadtracking.controller.responce.SetMeetingResponce
import com.pranav.user.leadtracking.view.activity.ContactsMeetingActivity
import com.pranav.user.leadtracking.view.adapter.RvAddContactMeetingAdapter
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.pranav.user.leadtracking.controller.request.DeleteMeetingRequest
import com.pranav.user.leadtracking.controller.request.ScheduledMeetingRequest
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce
import com.pranav.user.leadtracking.controller.responce.ScheduledMeetingResponce
import com.pranav.user.leadtracking.view.activity.MeetingQuestionActivity
import com.pranav.user.leadtracking.view.adapter.RvScheduledMeetingDealAdapter


class MeetingTabDeal : Fragment(), RvAddContactMeetingAdapter.McallBack, RvScheduledMeetingDealAdapter.Callback {

    var myCalendar = Calendar.getInstance()
    lateinit var TxtVwDate: TextView
    lateinit var TxtVwTime: TextView
    lateinit var edtTxtSubject: EditText
    lateinit var edtTxtAgenda: EditText
    lateinit var rVSheduledMeeting: RecyclerView
    lateinit var rvContacts: RecyclerView
    lateinit var spnrType: Spinner
    lateinit var spnrLocation: Spinner
    var strType = ArrayList<String>()
    var intType = "-1"
    var strTypeId = ArrayList<String>()
    var strLocation = ArrayList<String>()
    var intLocation = "-1"
    var strLocationId = ArrayList<String>()
    lateinit var adapterType: ArrayAdapter<String>
    lateinit var adapterLoc: ArrayAdapter<String>
    lateinit var rvAddContactMeetingAdapter: RvAddContactMeetingAdapter
    lateinit var imgBtnAddContact: ImageButton
    lateinit var btnSetMeeting: Button
    lateinit var btnNeg: Button
    lateinit var btnPos: Button
    lateinit var rvScheduledMeetingDealAdapter: RvScheduledMeetingDealAdapter
    var cur_acl: Long = 0
    var ids_name = ""
    lateinit var progressDialog: Dialog
    var ID = ""
    var FLAG = "DEAL"
    var str = ""
    var CONTACT_ID = ""

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.tab_meeting, container, false)

        TxtVwDate = mView.findViewById(R.id.TxtVwDate)
        TxtVwTime = mView.findViewById(R.id.TxtVwTime)
        rVSheduledMeeting = mView.findViewById(R.id.rVSheduledMeeting)
        spnrType = mView.findViewById(R.id.spnrType)
        spnrLocation = mView.findViewById(R.id.spnrLocation)
        rvContacts = mView.findViewById(R.id.rvContacts)
        imgBtnAddContact = mView.findViewById(R.id.imgBtnAddContact)
        btnSetMeeting = mView.findViewById(R.id.btnSetMeeting)
        edtTxtSubject = mView.findViewById(R.id.edtTxtSubject)
        edtTxtAgenda = mView.findViewById(R.id.edtTxtAgenda)


        rvAddContactMeetingAdapter = RvAddContactMeetingAdapter(activity!!)
        rvContacts.adapter = rvAddContactMeetingAdapter
        rvAddContactMeetingAdapter.setCallBack(this)
        cur_acl = myCalendar.timeInMillis

        ID = arguments!!.getString("ID")
        CONTACT_ID = arguments!!.getString("CONTACT_ID")
        FLAG = arguments!!.getString("FLAG")

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.dialog_warning)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)

        btnNeg = progressDialog.findViewById(R.id.btnNeg)
        btnPos = progressDialog.findViewById(R.id.btnPos)


        val ALL_PERMISSIONS = 101
        val permissions = arrayOf(Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR)

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, permissions, ALL_PERMISSIONS)
        }

        LocationTypeRequest(activity!!, ResponceProcessorLocation()).locations()
        LocationTypeRequest(activity!!, ResponceProcessorType()).type()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        rvScheduledMeetingDealAdapter = RvScheduledMeetingDealAdapter(activity!!)
        rVSheduledMeeting.adapter = rvScheduledMeetingDealAdapter
        rvScheduledMeetingDealAdapter.setCallback(this)
        if (FLAG == "DEAL")
            ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetings(ID)
        else
            ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetingNetwork(ID)
        btnSetMeeting.setOnClickListener {
            if (TxtVwDate.text.trim().toString() == "") {
                Log.e("callback 2", str)
                Toast.makeText(activity, "Set Date", Toast.LENGTH_SHORT).show()
            } else
                if (TxtVwTime.text.trim().toString() == "") {
                    Toast.makeText(activity, "Set Time", Toast.LENGTH_SHORT).show()
                } else
                    if (edtTxtSubject.text.trim().toString() == "") {
                        Toast.makeText(activity, "Enter Subject", Toast.LENGTH_SHORT).show()
                    } else
                        if (edtTxtAgenda.text.trim().toString() == "") {
                            Toast.makeText(activity, "Enter Agenda", Toast.LENGTH_SHORT).show()
                        } else
                        /*if (intType == "-1") {
                            Toast.makeText(activity, "Select Type", Toast.LENGTH_SHORT).show()
                        } else
                            if (intLocation == "-1") {
                                Toast.makeText(activity, "Select Location", Toast.LENGTH_SHORT).show()
                            } else */ {
                            /*if (str.trim().toString() == "" || str == "[$CONTACT_ID")
                                str = "[$CONTACT_ID]"
                            else {
                                str = str.replace("$CONTACT_ID,", "")
                                str = str.replace(",$CONTACT_ID", "")
                                str = "$str,$CONTACT_ID]"
                            }*/
                            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                                    || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

                                /* SetMeetingRequest(activity!!, ResponceProcessorSetMEeting())
                                         .setmeeting(edtTxtAgenda.text.toString()
                                                 , str
                                                 , *//*intLocation*//*"0"
                                                , TxtVwDate.text.toString() + " " + TxtVwTime.text.toString() + ":00"
                                                , edtTxtSubject.text.toString()
                                                , *//*intType*//*"0"
                                                , CalanderID())*/
                                val prefs = activity!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
                                val editor = prefs.edit()
                                editor.clear()
                                editor.apply()

                                var next = Intent(activity!!, MeetingQuestionActivity::class.java)
                                next.putExtra("AGENDA", edtTxtAgenda.text.toString())
                                next.putExtra("CONTACTS", str)
                                next.putExtra("DATE", TxtVwDate.text.toString())
                                next.putExtra("TIME", TxtVwTime.text.toString())
                                next.putExtra("SUBJECT", edtTxtSubject.text.toString())
                                next.putExtra("LEAD_ID", ID)
                                next.putExtra("FLAG", FLAG)
                                startActivity(next)
                                Log.e("CONTACTS", "$CONTACT_ID $str")

                            } else {
                                Toast.makeText(activity!!, "CALENDAR permission denied", Toast.LENGTH_SHORT).show()
                            }


                        }
        }

        TxtVwDate.setOnClickListener {
            var dpDialog = DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            dpDialog.datePicker.minDate = cur_acl
            dpDialog.show()
        }

        TxtVwTime.setOnClickListener {
            val c = Calendar.getInstance()
            var mHour = c.get(Calendar.HOUR_OF_DAY)
            var mMinute = c.get(Calendar.MINUTE)

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(activity,
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        var min = "00"
                        if (minute != 0)
                            min = minute.toString()
                        if (hourOfDay <= 12) {
                            if (hourOfDay == 12)
                                TxtVwTime.text = "$hourOfDay:$min PM"
                            else
                                if (hourOfDay == 0)
                                    TxtVwTime.text = "12:$min AM"
                                else
                                    TxtVwTime.text = "$hourOfDay:$min AM"
                        } else {
                            var hOfDay = hourOfDay % 12
                            TxtVwTime.text = "$hOfDay:$min PM"
                        }
                    }, mHour, mMinute, false)
            timePickerDialog.show()
        }

        spnrType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                intType = strTypeId[p2]
            }
        }

        spnrLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                intLocation = strLocationId[p2]
            }
        }

        imgBtnAddContact.setOnClickListener {
            val next = Intent(activity!!, ContactsMeetingActivity::class.java)
            next.putExtra("CONTACT_ID", CONTACT_ID)
            startActivity(next)
        }
        return mView
    }

    inner class ResponceProcessorMeetings : ProcessResponcceInterphase<Array<ScheduledMeetingResponce>> {
        override fun processResponce(responce: Array<ScheduledMeetingResponce>) {

            rvScheduledMeetingDealAdapter.set(responce)
            rVSheduledMeeting.recycledViewPool.setMaxRecycledViews(0, 0)
            rVSheduledMeeting.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun callback(id: String) {
        progressDialog.show()
        btnPos.setOnClickListener {
            DeleteMeetingRequest(activity!!, ResponceProcessorDeleteMeeting()).DeleteMeeting(id)
        }
        btnNeg.setOnClickListener { progressDialog.dismiss() }
    }

    inner class ResponceProcessorDeleteMeeting : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
            progressDialog.dismiss()
            if (FLAG == "DEAL")
                ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetings(ID)
            else
                ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetingNetwork(ID)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun setCallback(name: String) {

        str = str.replace("${name.split("##")[0]},", "")
        str = str.replace(",${name.split("##")[0]}", "")

        val prefs = activity!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
        var names = prefs.getString("ID_NAME", "")
        ids_name = names.replace("$name,", "")
        val editor = prefs.edit()
        editor.putString("ID_NAME", ids_name)
        editor.apply()

        str = "["
        var count = 0
        val contacts_ids = ids_name.split(",")
        while (count < contacts_ids.size - 1) {
            str = str + contacts_ids[count].split("##")[0] + ","
            count++
        }
        str = str.substring(0, str.length - 1)
        if (str.trim() == "" || str == "[$CONTACT_ID")
            str = "[$CONTACT_ID]"
        else {
            str = str.replace("$CONTACT_ID,", "")
            str = str.replace(",$CONTACT_ID", "")
            str = "$str,$CONTACT_ID]"
        }
        Log.e("callback asd", "$str $ids_name")

        rvAddContactMeetingAdapter.set(ids_name.split(","))
        rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
        rvContacts.layoutManager = GridLayoutManager(activity!!, 2)

    }

    override fun onResume() {
        super.onResume()

        var name = activity!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE).getString("ID_NAME", "")
        ids_name = name
        str = "["
        var count = 0
        var contacts_ids = ids_name.split(",")
        while (count < contacts_ids.size - 1) {
            str = str + contacts_ids[count].split("##")[0] + ","
            count++
        }
        str = str.substring(0, str.length - 1)
        if (str.trim() == "" || str == "[$CONTACT_ID")
            str = "[$CONTACT_ID]"
        else {
            str = str.replace("$CONTACT_ID,", "")
            str = str.replace(",$CONTACT_ID", "")
            str = "$str,$CONTACT_ID]"
        }

        rvAddContactMeetingAdapter.set(ids_name.split(","))
        rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
        rvContacts.layoutManager = GridLayoutManager(activity!!, 2)

        if (FLAG == "DEAL")
            ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetings(ID)
        else
            ScheduledMeetingRequest(activity!!, ResponceProcessorMeetings()).myMeetingNetwork(ID)
        TxtVwDate.text = ""
        TxtVwTime.text = ""
        edtTxtSubject.setText("")
        edtTxtAgenda.setText("")
        spnrType.setSelection(0)
        spnrLocation.setSelection(0)
        intType = "-1"
        intLocation = "-1"
        ids_name = ""

    }

    inner class ResponceProcessorType : ProcessResponcceInterphase<Array<LocationResponce>> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: Array<LocationResponce>) {
            var count = 0
            strType.add("--Type--")
            strTypeId.add("-1")

            while (count < responce.size) {
                strType.add(responce[count].name.toString())
                strTypeId.add(responce[count].id.toString())
                count++
            }

            adapterType = ArrayAdapter(activity, R.layout.spinner_design, strType)

            spnrType.adapter = adapterType
            adapterType.notifyDataSetChanged()

        }

    }

    inner class ResponceProcessorLocation : ProcessResponcceInterphase<Array<LocationResponce>> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: Array<LocationResponce>) {
            var count = 0
            strLocation.add("--Location--")
            strLocationId.add("-1")

            while (count < responce.size) {
                strLocation.add(responce[count].name.toString())
                strLocationId.add(responce[count].id.toString())
                count++
            }

            adapterLoc = ArrayAdapter(activity, R.layout.spinner_design, strLocation)

            spnrLocation.adapter = adapterLoc
            adapterLoc.notifyDataSetChanged()


        }

    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
//        val myFormat = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val myDate = Date()

        /*val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("UTC")
         calendar.time = myDate
         val time = calendar.time
        val outputFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val dateAsString = outputFmt.format(myCalendar.time)*/



        TxtVwDate.text = sdf.format(myCalendar.time)
    }

}