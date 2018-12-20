package com.pranav.user.leadtracking.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealDetailContactRequest
import com.pranav.user.leadtracking.controller.responce.DealDetailContactResponce
import com.pranav.user.leadtracking.view.adapter.RvAddContactsDealAdapter
import android.view.inputmethod.EditorInfo
import android.widget.*


class ContactsDealFragment : Fragment(), RvAddContactsDealAdapter.ContactsDealCallBack {

    lateinit var rvAddContactsAdapter: RvAddContactsDealAdapter
    lateinit var rvContacts: RecyclerView
    lateinit var imgBtnBack: ImageButton
    lateinit var edtTxtSearch: EditText
    var ids = ""
    var ids_name = ""
    var CONTACT_ID = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_contacts, container, false)
        rvContacts = mView.findViewById(R.id.rvContacts)
        imgBtnBack = mView.findViewById(R.id.imgBtnBack)
        edtTxtSearch = mView.findViewById(R.id.edtTxtSearch)
        rvAddContactsAdapter = RvAddContactsDealAdapter(activity!!)
        rvContacts.adapter = rvAddContactsAdapter
        rvAddContactsAdapter.setCallBack(this)

        ids = arguments!!.getString("ID")
        ids_name = arguments!!.getString("ids_name")
        CONTACT_ID = arguments!!.getString("CONTACT_ID")

        DealDetailContactRequest(activity!!, ResponceProcessorContact("0")).contact(1)

        edtTxtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                DealDetailContactRequest(activity!!, ResponceProcessorContact("1")).contactSearch(edtTxtSearch.text.toString(), 1)

            }
            false
        }

        /*edtTxtSearch.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                *//* Write your logic here that will be executed when user taps next button *//*


                handled = true
            }
            handled
        }*/

        imgBtnBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorContact(i: String) : ProcessResponcceInterphase<DealDetailContactResponce> {
        var a = i
        override fun processResponce(responce: DealDetailContactResponce) {
            rvAddContactsAdapter.set(responce.data!!, "0", a, ids_name,CONTACT_ID)
            rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
            rvContacts.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }

        override fun processResponceError(responce: Any?) {

        }

    }

    override fun setCallback(name: String, id: String) {
//        AddContactsDealRequest(activity!!, ResponcePRocessorAddContacts()).Addcontacts("[$id]", ids)

        val prefs = activity!!.getSharedPreferences("Contact_", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("ID", id)
        editor.putString("NAME", name)
        editor.apply()

        activity!!.onBackPressed()

    }

    /*inner class ResponcePRocessorAddContacts : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }*/
}
