package com.pranav.user.leadtracking.view.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.*
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.controller.responce.AllBillingsResponce
import com.pranav.user.leadtracking.controller.responce.DealDetailResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.activity.DealProfileActivity
import com.pranav.user.leadtracking.view.activity.DealQuestionsActivity
import faranjit.currency.edittext.CurrencyEditText
import java.text.SimpleDateFormat
import java.util.*

class AddDealsFragment : Fragment() {

    lateinit var spnrBilling: Spinner
    lateinit var spnrDeliverable: Spinner
    lateinit var spnrAccount: Spinner
    lateinit var spnrLead: Spinner

    lateinit var edtTxtName: EditText
    lateinit var txtVwStartDate: TextView
    lateinit var txtVwEndDate: TextView
    lateinit var edtTxtCurrency: CurrencyEditText

    lateinit var btnAddDeal: Button

    var accountName = ArrayList<String>()
    var account_pos = 0
    var accountId = ArrayList<String>()

    var leadName = ArrayList<String>()
    var lead_pos = 0
    var leadId = ArrayList<String>()

    var billingName = ArrayList<String>()
    var billing_pos = 0
    var billingId = ArrayList<String>()

    var deliverableTypeName = ArrayList<String>()
    var deliverableType_pos = 0
    var deliverableTypeId = ArrayList<String>()

    lateinit var adapterAccount: ArrayAdapter<String>
    lateinit var adapterLead: ArrayAdapter<String>
    lateinit var adapterBilling: ArrayAdapter<String>
    lateinit var adapterDeliverable: ArrayAdapter<String>

    var myCalendar = Calendar.getInstance()
    lateinit var date: DatePickerDialog.OnDateSetListener
    lateinit var dates: DatePickerDialog.OnDateSetListener
    lateinit var dpDialog: DatePickerDialog

    var tuday: Long = 0
    var ID = "-1"
    var NAME = ""
    var ACCOUNT_NAME = ""
    var VALUE = ""
    var NAME2 = ""
    var END_DATE = ""
    var START_DATE = ""
    var BILLING_TYPE = ""
    var DELIVERABLE_TYPE = ""

    var COUNTRY_CODE = ""
    var PHONE = ""
    var DESIGNATION = ""
    var DESIGNATION_ID = ""
    var LAST_CONNECTED_AT = ""
    var IMAGE = ""
    var GROUP_ID = ""
    var MAIL = ""
    var LEVEL_VALUE = ""
    var LEVEL_ID = ""
    var MEMBERS = ""
    var CONTACT_ID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_add_details, container, false)
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)

        spnrBilling = mView.findViewById(R.id.spnrBilling)
        spnrDeliverable = mView.findViewById(R.id.spnrDeliverable)
        spnrAccount = mView.findViewById(R.id.spnrAccount)
        spnrLead = mView.findViewById(R.id.spnrLead)
        txtVwEndDate = mView.findViewById(R.id.txtVwEndDate)
        txtVwStartDate = mView.findViewById(R.id.txtVwStartDate)
        btnAddDeal = mView.findViewById(R.id.btnAddDeal)
        edtTxtName = mView.findViewById(R.id.edtTxtName)
        edtTxtCurrency = mView.findViewById(R.id.edtTxtCurrency)
        edtTxtCurrency.showSymbol(true)

        ID = arguments!!.getString("ID")
        NAME = arguments!!.getString("NAME")
        VALUE = arguments!!.getString("VALUE")
        END_DATE = arguments!!.getString("END_DATE")
        START_DATE = arguments!!.getString("START_DATE")
        NAME2 = arguments!!.getString("NAME2")
        ACCOUNT_NAME = arguments!!.getString("ACCOUNT_NAME")
        BILLING_TYPE = arguments!!.getString("BILLING_TYPE")
        DELIVERABLE_TYPE = arguments!!.getString("DELIVERABLE_TYPE")

        COUNTRY_CODE = arguments!!.getString("COUNTRY_CODE")
        PHONE = arguments!!.getString("PHONE")
        DESIGNATION = arguments!!.getString("DESIGNATION")
        DESIGNATION_ID = arguments!!.getString("DESIGNATION_ID")
        LAST_CONNECTED_AT = arguments!!.getString("LAST_CONNECTED_AT")
        IMAGE = arguments!!.getString("IMAGE")
        GROUP_ID = arguments!!.getString("GROUP_ID")
        MAIL = arguments!!.getString("MAIL")
        LEVEL_VALUE = arguments!!.getString("LEVEL_VALUE")
        LEVEL_ID = arguments!!.getString("LEVEL_ID")
        MEMBERS = arguments!!.getString("MEMBERS")
        CONTACT_ID = arguments!!.getString("CONTACT_ID")


        tuday = myCalendar.timeInMillis

        if (ID != "-1") {
            edtTxtName.setText(NAME)
            edtTxtCurrency.setText(VALUE)

            txtVwStartDate.text = START_DATE
            txtVwEndDate.text = END_DATE
        }

        leadName.add("--Select Lead Type--")
        leadId.add("-1")
        adapterLead = ArrayAdapter(activity, R.layout.spinner_design, leadName)
        spnrLead.adapter = adapterLead
        date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            txtVwStartDate.text = updateLabel()
        }
        dates = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            txtVwEndDate.text = updateLabel()
        }

        AllBillingRequest(activity!!, ResponceProcessorAllBilling()).AllBilling()
        AllDeliverablesRequest(activity!!, ResponceProcessorAllDeliverables()).AllDeliverables()
        GroupRequest(activity!!, ResponceProcessorGroup()).groups()


        txtVwStartDate.setOnClickListener {
            dpDialog = DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            dpDialog.datePicker.minDate = tuday
            dpDialog.show()

        }
        txtVwEndDate.setOnClickListener {
            dpDialog = DatePickerDialog(activity, dates, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            dpDialog.datePicker.minDate = tuday
            dpDialog.show()

        }

        spnrAccount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leadName.clear()
                leadId.clear()
                leadName.add("--Select Lead Type--")
                leadId.add("-1")
                if (position != 0) {
                    DealDetailRequest(activity!!, ResponceProcessorLead()).getCategories(accountId[position])
                }
                account_pos = position
            }
        }
        spnrBilling.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                billing_pos = position
            }
        }
        spnrDeliverable.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                deliverableType_pos = position
            }
        }
        spnrLead.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lead_pos = position
            }
        }

        btnAddDeal.setOnClickListener {
            if (edtTxtName.text.trim().isEmpty())
                Toast.makeText(activity, "Enter Deal Name", Toast.LENGTH_SHORT).show()
            else {
                if (account_pos == 0)
                    Toast.makeText(activity, "Select Account", Toast.LENGTH_SHORT).show()
                else {
                    if (lead_pos == 0)
                        Toast.makeText(activity, "Select Lead", Toast.LENGTH_SHORT).show()
                    else {
                        if (txtVwStartDate.text.trim().isEmpty() || txtVwStartDate.text.trim().toString() == "Est. Start Date")
                            Toast.makeText(activity, "Select Est. Start Date", Toast.LENGTH_SHORT).show()
                        else {
                            if (txtVwEndDate.text.trim().isEmpty() || txtVwEndDate.text.trim().toString() == "Est. End Date")
                                Toast.makeText(activity, "Select Est. End Date", Toast.LENGTH_SHORT).show()
                            else {
                                if (billing_pos == 0)
                                    Toast.makeText(activity, "Select Billing Type", Toast.LENGTH_SHORT).show()
                                else {
                                    if (deliverableType_pos == 0)
                                        Toast.makeText(activity, "Select Deliverable Type", Toast.LENGTH_SHORT).show()
                                    else {
                                        if (edtTxtCurrency.text.trim().isEmpty())
                                            Toast.makeText(activity, "Enter the Amount", Toast.LENGTH_SHORT).show()
                                        else {
                                            if (txtVwStartDate.text.split("-")[0].toInt() == txtVwEndDate.text.split("-")[0].toInt()) {
                                                if (txtVwStartDate.text.split("-")[1].toInt() == txtVwEndDate.text.split("-")[1].toInt()) {
                                                    if (txtVwStartDate.text.split("-")[2].toInt() < txtVwEndDate.text.split("-")[2].toInt()) {
                                                        if (ID == "-1")
                                                            AddDealRequest(activity!!, ResponceProcessorDealCreate())
                                                                    .AddDeal(billingId[billing_pos],
                                                                            leadId[lead_pos],
                                                                            deliverableTypeId[deliverableType_pos],
                                                                            txtVwEndDate.text.toString(),
                                                                            edtTxtCurrency.currencyText.toString(),
                                                                            txtVwStartDate.text.toString(),
                                                                            accountId[account_pos],
                                                                            edtTxtName.text.trim().toString())
                                                        else
                                                            AddDealRequest(activity!!, ResponceProcessorDealUpdate())
                                                                    .AddDealUpdate(billingId[billing_pos],
                                                                            leadId[lead_pos],
                                                                            deliverableTypeId[deliverableType_pos],
                                                                            txtVwEndDate.text.toString(),
                                                                            edtTxtCurrency.currencyText.toString(),
                                                                            txtVwStartDate.text.toString(),
                                                                            accountId[account_pos], ID,
                                                                            edtTxtName.text.trim().toString())
                                                    } else
                                                        Toast.makeText(activity, """"Est. Start date" should be before "Est. end date".""", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    if (txtVwStartDate.text.split("-")[1].toInt() < txtVwEndDate.text.split("-")[1].toInt()) {
                                                        if (ID == "-1")
                                                            AddDealRequest(activity!!, ResponceProcessorDealCreate())
                                                                    .AddDeal(billingId[billing_pos],
                                                                            leadId[lead_pos],
                                                                            deliverableTypeId[deliverableType_pos],
                                                                            txtVwEndDate.text.toString(),
                                                                            edtTxtCurrency.currencyText.toString(),
                                                                            txtVwStartDate.text.toString(),
                                                                            accountId[account_pos],
                                                                            edtTxtName.text.trim().toString())
                                                        else
                                                            AddDealRequest(activity!!, ResponceProcessorDealUpdate())
                                                                    .AddDealUpdate(billingId[billing_pos],
                                                                            leadId[lead_pos],
                                                                            deliverableTypeId[deliverableType_pos],
                                                                            txtVwEndDate.text.toString(),
                                                                            edtTxtCurrency.currencyText.toString(),
                                                                            txtVwStartDate.text.toString(),
                                                                            accountId[account_pos], ID,
                                                                            edtTxtName.text.trim().toString())
                                                    } else
                                                        Toast.makeText(activity, """"Est. Start date" should be before "Est. end date".""", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                if (txtVwStartDate.text.split("-")[0].toInt() < txtVwEndDate.text.split("-")[0].toInt()) {
                                                    if (ID == "-1")
                                                        AddDealRequest(activity!!, ResponceProcessorDealCreate())
                                                                .AddDeal(billingId[billing_pos],
                                                                        leadId[lead_pos],
                                                                        deliverableTypeId[deliverableType_pos],
                                                                        txtVwEndDate.text.toString(),
                                                                        edtTxtCurrency.currencyText.toString(),
                                                                        txtVwStartDate.text.toString(),
                                                                        accountId[account_pos],
                                                                        edtTxtName.text.trim().toString())
                                                    else
                                                        AddDealRequest(activity!!, ResponceProcessorDealUpdate())
                                                                .AddDealUpdate(billingId[billing_pos],
                                                                        leadId[lead_pos],
                                                                        deliverableTypeId[deliverableType_pos],
                                                                        txtVwEndDate.text.toString(),
                                                                        edtTxtCurrency.currencyText.toString(),
                                                                        txtVwStartDate.text.toString(),
                                                                        accountId[account_pos], ID,
                                                                        edtTxtName.text.trim().toString())
                                                } else
                                                    Toast.makeText(activity, """"Est. Start date" should be before "Est. end date".""", Toast.LENGTH_SHORT).show()

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return mView
    }

    inner class ResponceProcessorDealUpdate : ProcessResponcceInterphase<AddDealResponce> {
        override fun processResponce(responce: AddDealResponce) {
            Toast.makeText(activity!!, responce.message, Toast.LENGTH_SHORT).show()
            val next = Intent(context, DealProfileActivity::class.java)
            next.putExtra("NAME", edtTxtName.text.toString())
            next.putExtra("ACCOUNT_NAME", accountName[account_pos])
            next.putExtra("COUNTRY_CODE", COUNTRY_CODE)
            next.putExtra("PHONE", PHONE)
            next.putExtra("DESIGNATION", DESIGNATION)
            next.putExtra("DESIGNATION_ID", DESIGNATION_ID)
            next.putExtra("LAST_CONNECTED_AT", LAST_CONNECTED_AT)
            next.putExtra("MAIL", MAIL)
            next.putExtra("IMAGE", IMAGE)
            next.putExtra("GROUP_ID", GROUP_ID)
            next.putExtra("VALUE", edtTxtCurrency.text.toString())
            next.putExtra("NAME2", leadName[lead_pos])
            next.putExtra("LEVEL_VALUE", LEVEL_VALUE)
            next.putExtra("LEVEL_ID", LEVEL_ID)
            next.putExtra("CONTACT_ID", CONTACT_ID)
            next.putExtra("START_DATE", txtVwStartDate.text.toString())
            next.putExtra("END_DATE", txtVwEndDate.text.toString())
            next.putExtra("BILLING_TYPE", deliverableTypeId[billing_pos])
            next.putExtra("DELIVERABLE_TYPE", deliverableTypeId[deliverableType_pos])
            next.putExtra("MEMBERS", MEMBERS)
            next.putExtra("ID", ID)
            startActivity(next)
            activity!!.finish()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorDealCreate : ProcessResponcceInterphase<AddDealResponce> {
        override fun processResponce(responce: AddDealResponce) {
            val next = Intent(context, DealQuestionsActivity::class.java)
            next.putExtra("FLAG", "1")
            next.putExtra("ID", responce.getAdditionalProperties()["lead_id"].toString())
            next.putExtra("LEVEL_ID", "1")
            activity!!.startActivity(next)
            activity!!.finish()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorLead : ProcessResponcceInterphase<Array<DealDetailResponce>> {
        override fun processResponce(responce: Array<DealDetailResponce>) {
            var count = 0
            var po = 0
            if (ID == "-1") {
                while (count != responce.size) {
                    leadName.add(responce[count].name!!)
                    leadId.add(responce[count].id.toString())
                    count++
                }
                adapterLead = ArrayAdapter(activity, R.layout.spinner_design, leadName)
                spnrLead.adapter = adapterLead
            } else {
                while (count != responce.size) {
                    leadName.add(responce[count].name!!)
                    leadId.add(responce[count].id.toString())
                    if (responce[count].name == NAME2) {
                        po = count + 1
                    }
                    count++
                }
                adapterLead = ArrayAdapter(activity, R.layout.spinner_design, leadName)
                spnrLead.adapter = adapterLead
                spnrLead.setSelection(po)
            }
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorGroup : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {
            var count = 0
            var po = 0
            accountName.add("--Select Account Type--")
            accountId.add("-1")
            if (ID == "-1") {
                while (count != responce.size) {
                    accountName.add(responce[count].accountName!!)
                    accountId.add(responce[count].id.toString())
                    count++
                }
                adapterAccount = ArrayAdapter(activity, R.layout.spinner_design, accountName)
                spnrAccount.adapter = adapterAccount
            } else {
                while (count != responce.size) {
                    accountName.add(responce[count].accountName!!)
                    accountId.add(responce[count].id.toString())
                    if (responce[count].accountName == ACCOUNT_NAME) {
                        po = count + 1
                    }
                    count++
                }
                adapterAccount = ArrayAdapter(activity, R.layout.spinner_design, accountName)
                spnrAccount.adapter = adapterAccount
                spnrAccount.setSelection(po)
            }
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorAllDeliverables : ProcessResponcceInterphase<Array<AllBillingsResponce>> {
        override fun processResponce(responce: Array<AllBillingsResponce>) {
            var count = 0
            var po = 0
            deliverableTypeName.add("--Select Deliverable Type--")
            deliverableTypeId.add("-1")
            if (ID == "-1") {
                while (count != responce.size) {
                    deliverableTypeName.add(responce[count].getAdditionalProperties()["name"].toString())
                    deliverableTypeId.add(responce[count].id.toString())
                    count++
                }

                adapterDeliverable = ArrayAdapter(activity, R.layout.spinner_design, deliverableTypeName)
                spnrDeliverable.adapter = adapterDeliverable
            } else {
                while (count != responce.size) {
                    deliverableTypeName.add(responce[count].getAdditionalProperties()["name"].toString())
                    deliverableTypeId.add(responce[count].id.toString())
                    if (responce[count].id.toString() == DELIVERABLE_TYPE) {
                        po = count + 1
                    }
                    count++
                }

                adapterDeliverable = ArrayAdapter(activity, R.layout.spinner_design, deliverableTypeName)
                spnrDeliverable.adapter = adapterDeliverable
                spnrDeliverable.setSelection(po)
            }
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorAllBilling : ProcessResponcceInterphase<Array<AllBillingsResponce>> {
        override fun processResponce(responce: Array<AllBillingsResponce>) {
            var count = 0
            var po = 0
            billingName.add("--Select Billing Type--")
            billingId.add("-1")
            if (ID == "-1") {
                while (count != responce.size) {
                    billingName.add(responce[count].getAdditionalProperties()["name"].toString())
                    billingId.add(responce[count].id.toString())
                    count++
                }
                adapterBilling = ArrayAdapter(activity, R.layout.spinner_design, billingName)
                spnrBilling.adapter = adapterBilling
            } else {
                while (count != responce.size) {
                    billingName.add(responce[count].getAdditionalProperties()["name"].toString())
                    billingId.add(responce[count].id.toString())
                    if (responce[count].id.toString() == BILLING_TYPE) {
                        po = count + 1
                    }
                    count++
                }
                adapterBilling = ArrayAdapter(activity, R.layout.spinner_design, billingName)
                spnrBilling.adapter = adapterBilling
                spnrBilling.setSelection(po)
            }
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    private fun updateLabel(): String? {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        txtVwStartDate.text = sdf.format(myCalendar.time)
        return sdf.format(myCalendar.time)
    }
}
