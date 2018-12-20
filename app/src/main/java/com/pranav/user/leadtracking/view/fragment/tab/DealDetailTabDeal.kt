package com.pranav.user.leadtracking.view.fragment.tab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AddContactsDealRequest
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce
import com.pranav.user.leadtracking.view.activity.ContactsDealActivity
import com.pranav.user.leadtracking.view.activity.DealQuestionsActivity
import com.pranav.user.leadtracking.view.adapter.RvAddContactDealAdapter

class DealDetailTabDeal : Fragment(), RvAddContactDealAdapter.McallBack {

    lateinit var SeekBarId: SeekBar
    lateinit var btnSetChange: Button
    lateinit var imgBtnAddContact: ImageButton
    var ids_name = ""
    lateinit var rvAddContactDealAdapter: RvAddContactDealAdapter
    lateinit var rvContacts: RecyclerView
    lateinit var oppIDSt: TextView
    var LEVEL_VALUE = ""
    var ID = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.deal_detail_tab_deal, container, false)

        SeekBarId = mView.findViewById(R.id.SeekBarId)
        btnSetChange = mView.findViewById(R.id.btnSetChange)
        imgBtnAddContact = mView.findViewById(R.id.imgBtnAddContact)
        rvContacts = mView.findViewById(R.id.rvContacts)
        oppIDSt = mView.findViewById(R.id.oppIDSt)

        LEVEL_VALUE = arguments!!.getString("LEVEL_VALUE")
        ID = arguments!!.getString("ID")
        var GROUP_ID = arguments!!.getString("GROUP_ID")
        var LEVEL_ID = arguments!!.getString("LEVEL_ID")
        var MEMBERS = arguments!!.getString("MEMBERS")
        var STAGE = arguments!!.getString("STAGE")
        var CONTACT_ID = arguments!!.getString("CONTACT_ID")
        ids_name = MEMBERS
        val prefs = activity!!.getSharedPreferences("LT_STAGE", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("STAGE", STAGE)
        editor.apply()

        rvAddContactDealAdapter = RvAddContactDealAdapter(activity!!)
        rvContacts.adapter = rvAddContactDealAdapter
        rvAddContactDealAdapter.setCallBack(this)

        if (MEMBERS != "") {
            rvAddContactDealAdapter.set(MEMBERS.split(","))
            rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
            rvContacts.layoutManager = GridLayoutManager(activity!!, 2)
        }

        oppIDSt.text = STAGE
        SeekBarId.progress = LEVEL_VALUE.toInt() * 10


        btnSetChange.setOnClickListener {
            val next = Intent(context, DealQuestionsActivity::class.java)
            next.putExtra("FLAG", "0")
            next.putExtra("ID", ID)
            next.putExtra("LEVEL_ID", LEVEL_ID)
            activity!!.startActivity(next)
        }

        imgBtnAddContact.setOnClickListener {
            val next = Intent(activity!!, ContactsDealActivity::class.java)
            next.putExtra("ID", ID)
            next.putExtra("ids_name", ids_name)
            next.putExtra("CONTACT_ID", CONTACT_ID)
            startActivity(next)
        }

        return mView
    }

    override fun onResume() {
        super.onResume()
//        SeekBarId.progress = LEVEL_VALUE.toInt() + 2 * 10
        val id = activity!!.getSharedPreferences("Contact_", Context.MODE_PRIVATE).getString("ID", "")
        val name = activity!!.getSharedPreferences("Contact_", Context.MODE_PRIVATE).getString("NAME", "")
        val sTAGE = activity!!.getSharedPreferences("LT_STAGE", Context.MODE_PRIVATE).getString("STAGE", "")
        oppIDSt.text = sTAGE

        if (id != "") {
            Log.e("id and name", ids_name)
            ids_name = ids_name.replace("$id##$name,", "")
            ids_name = "$ids_name$id##$name,"
            Log.e("id and name", ids_name)

            val prefs = activity!!.getSharedPreferences("Contact_", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("ID", "")
            editor.putString("NAME", "")
            editor.apply()

            var id = ids_name.split(",")
            var count = 0
            var idString = ""
            while (count != id.size - 1) {
                if (count == 0)
                    idString = id[count].split("##")[0]
                else
                    idString += ",${id[count].split("##")[0]}"
                count++
            }
            AddContactsDealRequest(activity!!, ResponcePRocessorAddContacts()).Addcontacts("[$idString]", ID)

        }

        if (ids_name != "") {
            rvAddContactDealAdapter.set(ids_name.split(","))
            rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
            rvContacts.layoutManager = GridLayoutManager(activity!!, 2)
        }
    }

    override fun setCallback(name: String) {
        ids_name = ids_name.replace("$name,", "")
        rvAddContactDealAdapter.set(ids_name.split(","))
        rvContacts.recycledViewPool.setMaxRecycledViews(0, 0)
        rvContacts.layoutManager = GridLayoutManager(activity!!, 2)
        var id = ids_name.split(",")
        var count = 0
        var idString = ""
        while (count != id.size - 1) {
            if (count == 0)
                idString = id[count].split("##")[0]
            else
                idString += ",${id[count].split("##")[0]}"
            count++
        }
        AddContactsDealRequest(activity!!, ResponcePRocessorAddContacts()).Addcontacts("[$idString]", ID)

    }

    inner class ResponcePRocessorAddContacts : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

}