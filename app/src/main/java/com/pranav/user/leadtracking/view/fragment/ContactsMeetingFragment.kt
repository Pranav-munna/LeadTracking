package com.pranav.user.leadtracking.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealDetailContactRequest
import com.pranav.user.leadtracking.controller.request.NetworkRequest
import com.pranav.user.leadtracking.controller.responce.DealDetailContactResponce
import com.pranav.user.leadtracking.view.adapter.RvAddContactsMeetingAdapter

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ContactsMeetingFragment : Fragment(), RvAddContactsMeetingAdapter.MeetingCallBack {


    lateinit var rvAddContactsMeetingAdapter: RvAddContactsMeetingAdapter
    lateinit var rvContacts: RecyclerView
    lateinit var imgBtnBack: ImageButton
    lateinit var edtTxtSearch: EditText
    var CONTACT_ID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_contacts, container, false)

        rvContacts = mView.findViewById(R.id.rvContacts)
        imgBtnBack = mView.findViewById(R.id.imgBtnBack)
        edtTxtSearch = mView.findViewById(R.id.edtTxtSearch)
        rvAddContactsMeetingAdapter = RvAddContactsMeetingAdapter(activity!!)
        rvContacts.adapter = rvAddContactsMeetingAdapter
        rvAddContactsMeetingAdapter.setCallBack(this)
        CONTACT_ID = arguments!!.getString("CONTACT_ID")
        DealDetailContactRequest(activity!!, ResponceProcessorContac("0")).contact(1)

        edtTxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                DealDetailContactRequest(activity!!, ResponceProcessorContac(p0.toString()))
                        .contactSearch(p0.toString(), 1)
            }
        })

        imgBtnBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorContac(s: String) : ProcessResponcceInterphase<DealDetailContactResponce> {
        var a = s
        override fun processResponce(responce: DealDetailContactResponce) {
            rvAddContactsMeetingAdapter.set(responce.data!!, "0", a, CONTACT_ID)
            rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
            rvContacts.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun setCallback(name: String, id: String) {
        val prefs = activity!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
        var id_name = prefs.getString("ID_NAME", "")
        val editor = prefs.edit()
        id_name = id_name.replace("$id##$name,", "")
        editor.putString("ID_NAME", "$id_name$id##$name,".trim())
        editor.apply()
        activity!!.onBackPressed()
    }
}
