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
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.GroupRequest
import com.pranav.user.leadtracking.controller.request.QuestionAllLevelsRequest
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.controller.responce.QuestionAllLevelsResponce
import com.pranav.user.leadtracking.view.activity.DealsActivity
import com.pranav.user.leadtracking.view.adapter.RvFiltersDataAdapter
import com.pranav.user.leadtracking.view.adapter.RvFiltersLevelsAdapter

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DealFilterFragment : Fragment(), RvFiltersDataAdapter.CallBack, RvFiltersLevelsAdapter.CallBack {

    lateinit var btnAccount: Button
    lateinit var btnLevels: Button
    lateinit var btnDone: Button
    lateinit var recViewAccount: RecyclerView
    lateinit var rvFiltersDataAdapter: RvFiltersDataAdapter
    lateinit var rvFiltersLevelsAdapter: RvFiltersLevelsAdapter
    lateinit var RecViewLevels: RecyclerView
    lateinit var rlAccount: RelativeLayout
    lateinit var rlDesignation: RelativeLayout
    private var accountF = ""
    private var levelF = ""
    lateinit var chkBxDesignation: CheckBox
    lateinit var chkBxAccount: CheckBox

    lateinit var listA: Array<GroupResponce>
    lateinit var listD: Array<QuestionAllLevelsResponce>

    override fun addAccount(name: String, list: Array<GroupResponce>, pos: Int) {
        chkBxAccount.isChecked = false
        accountF = name
        rvFiltersDataAdapter.set(list, name)
        recViewAccount.recycledViewPool.setMaxRecycledViews(0, 0)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(pos, 10)
        recViewAccount.layoutManager = linearLayoutManager
    }

    override fun addLevels(name: String, list: Array<QuestionAllLevelsResponce>, pos: Int) {
        chkBxDesignation.isChecked = false
        levelF = name
        rvFiltersLevelsAdapter.set(list, name)
        RecViewLevels.recycledViewPool.setMaxRecycledViews(0, 0)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(pos, 10)
        RecViewLevels.layoutManager = linearLayoutManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_filters, container, false)

        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)

        btnAccount = mView.findViewById(R.id.btnAccount)
        rlAccount = mView.findViewById(R.id.rlAccount)
        btnAccount.text = "ACCOUNTS"
        btnLevels = mView.findViewById(R.id.btnDesignation)
        rlDesignation = mView.findViewById(R.id.rlDesignation)
        btnLevels.text = "LEVELS"
        recViewAccount = mView.findViewById(R.id.recViewAccount)
        RecViewLevels = mView.findViewById(R.id.RecViewDesignation)
        btnDone = mView.findViewById(R.id.btnDone)
        chkBxDesignation = mView.findViewById(R.id.chkBxDesignation)
        chkBxAccount = mView.findViewById(R.id.chkBxAccount)

        GroupRequest(activity!!, ResponceProcessorGrps()).groups()
        QuestionAllLevelsRequest(activity!!, RespoceProcessorLevel()).AllLevel()

        try {
            accountF = arguments!!.getString("GROUP")
            levelF = arguments!!.getString("LEVEL")
        } catch (e: Exception) {
            accountF = ""
            levelF = ""
        }

        rvFiltersDataAdapter = RvFiltersDataAdapter(activity!!)
        recViewAccount.adapter = rvFiltersDataAdapter
        rvFiltersDataAdapter.setCallBack(this)

        rvFiltersLevelsAdapter = RvFiltersLevelsAdapter(activity!!)
        RecViewLevels.adapter = rvFiltersLevelsAdapter
        rvFiltersLevelsAdapter.setCallBack(this)

        chkBxDesignation.setOnClickListener {
            levelF = ""
            chkBxDesignation.isChecked = true
            rvFiltersLevelsAdapter.set(listD, levelF)
            RecViewLevels.recycledViewPool.setMaxRecycledViews(0, 0)
            RecViewLevels.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

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
            btnLevels.setTextColor(ContextCompat.getColor(activity!!, R.color.textBlack))
            recViewAccount.visibility = View.VISIBLE
            rlAccount.visibility = View.VISIBLE
            RecViewLevels.visibility = View.GONE
            rlDesignation.visibility = View.GONE
        }
        btnLevels.setOnClickListener {
            btnLevels.setTextColor(ContextCompat.getColor(activity!!, R.color.textOrange))
            btnAccount.setTextColor(ContextCompat.getColor(activity!!, R.color.textBlack))
            recViewAccount.visibility = View.GONE
            rlAccount.visibility = View.GONE
            RecViewLevels.visibility = View.VISIBLE
            rlDesignation.visibility = View.VISIBLE
        }

        btnDone.setOnClickListener {
            val next = Intent(activity, DealsActivity::class.java)
            next.putExtra("GROUP", accountF)
            next.putExtra("LEVEL", levelF)
            startActivity(next)
            activity!!.finish()
        }
        return mView
    }

    inner class RespoceProcessorLevel : ProcessResponcceInterphase<Array<QuestionAllLevelsResponce>> {
        override fun processResponce(responce: Array<QuestionAllLevelsResponce>) {
            listD = responce
            rvFiltersLevelsAdapter.set(responce, levelF)
            RecViewLevels.recycledViewPool.setMaxRecycledViews(0, 0)
            RecViewLevels.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorGrps : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {
            listA = responce
            rvFiltersDataAdapter.set(responce, accountF)
            recViewAccount.recycledViewPool.setMaxRecycledViews(0, 0)
            recViewAccount.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        override fun processResponceError(responce: Any?) {

        }

    }

}
