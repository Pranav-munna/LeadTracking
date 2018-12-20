package com.pranav.user.leadtracking.view.fragment.tab

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealDetailRequest
import com.pranav.user.leadtracking.controller.responce.DealDetailResponce
import com.pranav.user.leadtracking.controller.responce.ErrorMessageResponce
import com.pranav.user.leadtracking.view.adapter.RvContactAdapter

class DealDetailTab : Fragment() {

    lateinit var rvContacts: RecyclerView
    lateinit var txtVwDesig: TextView
    lateinit var txtVwLastContacted: TextView
    lateinit var rvContactAdapter: RvContactAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.tab_deal_detail, container, false)

        rvContacts = mView.findViewById(R.id.rvContacts)
        txtVwDesig = mView.findViewById(R.id.txtVwDesig)
        txtVwLastContacted = mView.findViewById(R.id.txtVwLastContacted)

        var DESIGNATION = arguments!!.getString("DESIGNATION")
        var LAST_CONNECTED_AT = arguments!!.getString("LAST_CONNECTED_AT")
        var GROUP_ID = arguments!!.getString("GROUP_ID")
        var ID = arguments!!.getString("ID")

        txtVwDesig.text = DESIGNATION
        if (LAST_CONNECTED_AT == "null")
            txtVwLastContacted.text = "Not yet contacted"
        else
            txtVwLastContacted.text = date(LAST_CONNECTED_AT.toLong())

        rvContactAdapter = RvContactAdapter(activity!!)

        DealDetailRequest(activity!!, ResponceProcessorDetailsData()).getCategories(GROUP_ID)


//        SeekBarId.setOnTouchListener(View.OnTouchListener { view, motionEvent -> true })

        return mView
    }

    inner class ResponceProcessorDetailsData : ProcessResponcceInterphase<Array<DealDetailResponce>> {
        override fun processResponceError(responce:Any?) {
        }

        override fun processResponce(responce: Array<DealDetailResponce>) {
            if (responce.size >= 2) {
                rvContacts.adapter = rvContactAdapter
                rvContacts.getRecycledViewPool().setMaxRecycledViews(0, 0)
                rvContactAdapter.set(responce)
                rvContacts.layoutManager = GridLayoutManager(activity, 4)
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun date(sec: Long): String? {
        val unixSeconds: Long = sec
        val date = java.util.Date(unixSeconds * 1000L)
        val sdf = java.text.SimpleDateFormat("yyyy MMM dd HH:mma")
        return sdf.format(date)
    }
}