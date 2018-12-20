package com.pranav.user.leadtracking.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DesignationRequest
import com.pranav.user.leadtracking.controller.request.GroupRequest
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.activity.NetworkActivity
import com.pranav.user.leadtracking.view.adapter.RvFiltersDataAdapter
import com.pranav.user.leadtracking.view.adapter.RvFiltersDataDesignationAdapter

class FiltersFragment : Fragment(), RvFiltersDataAdapter.CallBack, RvFiltersDataDesignationAdapter.CallBack {

    lateinit var recViewAccount: RecyclerView
    lateinit var recViewDesignation: RecyclerView
    lateinit var rvFiltersDataAdapter: RvFiltersDataAdapter
    lateinit var rvFiltersDataDesignationAdapter: RvFiltersDataDesignationAdapter
    lateinit var btnAccount: Button
    lateinit var btnDesignation: Button
    lateinit var btnDone: Button
    lateinit var rlAccount: RelativeLayout
    lateinit var rlDesignation: RelativeLayout
    lateinit var chkBxDesignation: CheckBox
    lateinit var chkBxAccount: CheckBox

    private var accountF = ""
    private var designationF = ""
    lateinit var listA: Array<GroupResponce>
    lateinit var listD: Array<DesignationResponce>


    override fun addAccount(name: String, list: Array<GroupResponce>, pos: Int) {
        chkBxAccount.isChecked = false
        accountF = name
        rvFiltersDataAdapter.set(list, name)
        recViewAccount.recycledViewPool.setMaxRecycledViews(0, 0)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(pos, 10)
        recViewAccount.layoutManager = linearLayoutManager
    }

    override fun addDesignation(name: String, list: Array<DesignationResponce>, pos: Int) {
        chkBxDesignation.isChecked = false
        designationF = name
        rvFiltersDataDesignationAdapter.set(list, name)
        recViewDesignation.recycledViewPool.setMaxRecycledViews(0, 0)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(pos, 10)
        recViewDesignation.layoutManager = linearLayoutManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_filters, container, false)
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)

        recViewAccount = mView.findViewById(R.id.recViewAccount)
        rlAccount = mView.findViewById(R.id.rlAccount)
        recViewDesignation = mView.findViewById(R.id.RecViewDesignation)
        rlDesignation = mView.findViewById(R.id.rlDesignation)
        btnAccount = mView.findViewById(R.id.btnAccount)
        btnDesignation = mView.findViewById(R.id.btnDesignation)
        btnDone = mView.findViewById(R.id.btnDone)
        chkBxDesignation = mView.findViewById(R.id.chkBxDesignation)
        chkBxAccount = mView.findViewById(R.id.chkBxAccount)

        DesignationRequest(activity!!, ResponceProcessorDesignationss()).desgination()
        GroupRequest(activity!!, ResponceProcessorGroupss()).groups()

        try {
            accountF = arguments!!.getString("GROUP")
            designationF = arguments!!.getString("DESIGNATION")
        } catch (e: Exception) {
            accountF = ""
            designationF = ""
        }

        rvFiltersDataAdapter = RvFiltersDataAdapter(activity!!)
        recViewAccount.adapter = rvFiltersDataAdapter
        rvFiltersDataAdapter.setCallBack(this)

        rvFiltersDataDesignationAdapter = RvFiltersDataDesignationAdapter(activity!!)
        recViewDesignation.adapter = rvFiltersDataDesignationAdapter
        rvFiltersDataDesignationAdapter.setCallBack(this)

        chkBxDesignation.setOnClickListener {
            designationF = ""
            chkBxDesignation.isChecked = true
            rvFiltersDataDesignationAdapter.set(listD, designationF)
            recViewDesignation.recycledViewPool.setMaxRecycledViews(0, 0)
            recViewDesignation.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }
        chkBxAccount.setOnClickListener {
            accountF = ""
            chkBxAccount.isChecked = true
            rvFiltersDataAdapter.set(listA, accountF)
            recViewAccount.recycledViewPool.setMaxRecycledViews(0, 0)
            recViewAccount.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        btnAccount.setOnClickListener {
            btnAccount.setTextColor(ContextCompat.getColor(activity!!, R.color.textOrange))
            btnDesignation.setTextColor(ContextCompat.getColor(activity!!, R.color.textBlack))
            recViewAccount.visibility = View.VISIBLE
            rlAccount.visibility = View.VISIBLE
            recViewDesignation.visibility = View.GONE
            rlDesignation.visibility = View.GONE
        }
        btnDesignation.setOnClickListener {
            btnDesignation.setTextColor(ContextCompat.getColor(activity!!, R.color.textOrange))
            btnAccount.setTextColor(ContextCompat.getColor(activity!!, R.color.textBlack))
            recViewAccount.visibility = View.GONE
            rlAccount.visibility = View.GONE
            recViewDesignation.visibility = View.VISIBLE
            rlDesignation.visibility = View.VISIBLE
        }

        btnDone.setOnClickListener {
            val next = Intent(activity, NetworkActivity::class.java)
            next.putExtra("GROUP", accountF)
            next.putExtra("DESIGNATION", designationF)
            startActivity(next)
            activity!!.finish()
        }

        return mView
    }

    inner class ResponceProcessorGroupss : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {
            listA = responce
            rvFiltersDataAdapter.set(responce, accountF)
            recViewAccount.recycledViewPool.setMaxRecycledViews(0, 0)
            recViewAccount.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorDesignationss : ProcessResponcceInterphase<Array<DesignationResponce>> {
        override fun processResponce(responce: Array<DesignationResponce>) {
            listD = responce
            rvFiltersDataDesignationAdapter.set(responce, designationF)
            recViewDesignation.recycledViewPool.setMaxRecycledViews(0, 0)
            recViewDesignation.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

}
