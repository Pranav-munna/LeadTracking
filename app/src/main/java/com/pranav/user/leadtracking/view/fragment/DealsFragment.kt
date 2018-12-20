package com.pranav.user.leadtracking.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealsRequest
import com.pranav.user.leadtracking.controller.responce.DealsResponce
import com.pranav.user.leadtracking.view.activity.*
import com.pranav.user.leadtracking.view.adapter.RvDealsAdapter

//@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DealsFragment : Fragment() {

    lateinit var rvDeals: RecyclerView
    lateinit var btnHome: Button
    lateinit var btnScoreBd: Button
    lateinit var btnNetwork: Button
    lateinit var btnProfile: Button
    lateinit var rvDealsAdapter: RvDealsAdapter
    lateinit var imgVwAddDeal: ImageView
    lateinit var imgBtnFilter: ImageButton
    lateinit var progressDialog: Dialog
    lateinit var edtTxtSearch: EditText
    private var accountF = ""
    private var levelF = ""

    override fun onResume() {
        super.onResume()
        progressDialog.show()
        DealsRequest(activity!!, ResponceProcessorDeals()).dealList(1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_deals, container, false)

        rvDeals = mView.findViewById(R.id.rvDeals)
        btnHome = mView.findViewById(R.id.btnHome)
        btnNetwork = mView.findViewById(R.id.btnNetwork)
        btnScoreBd = mView.findViewById(R.id.btnScoreBd)
        btnProfile = mView.findViewById(R.id.btnProfile)
        imgVwAddDeal = mView.findViewById(R.id.imgVwAddDeal)
        imgBtnFilter = mView.findViewById(R.id.imgBtnFilter)
        edtTxtSearch = mView.findViewById(R.id.edtTxtSearch)

        /*edtTxtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                DealsRequest(activity!!, ResponceProcessorDeals()).dealListSearch(edtTxtSearch.text.trim().toString(), 1)
            }
            false
        }*/
        edtTxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                DealsRequest(activity!!, ResponceProcessorDeals()).dealListSearch(s.toString(), 1)
            }
        })

        try {
            accountF = arguments!!.getString("GROUP")
            levelF = arguments!!.getString("LEVEL")
        } catch (e: Exception) {
            accountF = ""
            levelF = ""
        }

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)


        rvDealsAdapter = RvDealsAdapter(activity!!)
        rvDeals.adapter = rvDealsAdapter


        btnHome.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, HomeFeedsActivity::class.java)) }
        btnNetwork.setOnClickListener {activity!!.finish()
            startActivity(Intent(activity, NetworkActivity::class.java)) }
        btnScoreBd.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, ScoreboardActivity::class.java)) }
        btnProfile.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, MyProfileActivity::class.java)) }
        imgVwAddDeal.setOnClickListener {
            val next = Intent(activity, AddDealsActivity::class.java)
            next.putExtra("ID", "-1")
            next.putExtra("NAME", "")
            next.putExtra("VALUE", "")
            next.putExtra("START_DATE", "")
            next.putExtra("END_DATE", "")
            next.putExtra("NAME2", "")
            next.putExtra("ACCOUNT_NAME", "")
            next.putExtra("BILLING_TYPE", "")
            next.putExtra("DELIVERABLE_TYPE", "")

            next.putExtra("COUNTRY_CODE", "")
            next.putExtra("PHONE", "")
            next.putExtra("DESIGNATION", "")
            next.putExtra("DESIGNATION_ID", "")
            next.putExtra("LAST_CONNECTED_AT", "")
            next.putExtra("IMAGE", "")
            next.putExtra("GROUP_ID", "")
            next.putExtra("MAIL", "")
            next.putExtra("LEVEL_VALUE", "")
            next.putExtra("LEVEL_ID", "")
            next.putExtra("MEMBERS", "")
            next.putExtra("CONTACT_ID", "")
            startActivity(next)


        }
        imgBtnFilter.setOnClickListener {
            val next = Intent(activity, DealFilterActivity::class.java)
            next.putExtra("GROUP", accountF)
            next.putExtra("LEVEL", levelF)
            startActivity(next)
        }

        return mView
    }

    inner class ResponceProcessorDeals : ProcessResponcceInterphase<DealsResponce> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: DealsResponce) {
            if (responce.data!!.isNotEmpty()) {
                rvDealsAdapter.set(responce.data as ArrayList, accountF, levelF,edtTxtSearch.text.trim().toString())
                rvDeals.recycledViewPool.setMaxRecycledViews(0, 0)
                rvDeals.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            }
            val handler = Handler()
            handler.postDelayed({
                progressDialog.dismiss()
            }, 800)
        }
    }
}
