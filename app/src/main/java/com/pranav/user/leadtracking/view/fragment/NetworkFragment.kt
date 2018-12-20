package com.pranav.user.leadtracking.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.NetworkRequest
import com.pranav.user.leadtracking.controller.responce.NetworkResponce
import com.pranav.user.leadtracking.view.activity.*
import com.pranav.user.leadtracking.view.adapter.RvNetworkAdapter

class NetworkFragment : Fragment() {

    lateinit var rvNetwork: RecyclerView
    lateinit var btnHome: Button
    lateinit var btnScoreBd: Button
    lateinit var btnDeals: Button
    lateinit var btnProfile: Button
    lateinit var rvNetworkAdapter: RvNetworkAdapter
    lateinit var imgBtnAddnetwork: ImageButton
    lateinit var imgBtnFilter: ImageButton
    lateinit var edtTxtSearch: EditText
    lateinit var progressDialog: Dialog

    var accountF = ""
    var designationF = ""
    var searchTag = ""

    override fun onResume() {
        super.onResume()
        NetworkRequest(activity!!, ResponceProcessorNetwork()).networkList(1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val mView = inflater.inflate(R.layout.fragment_network, container, false)

        rvNetwork = mView.findViewById(R.id.rvNetwork)
        btnHome = mView.findViewById(R.id.btnHome)
        btnScoreBd = mView.findViewById(R.id.btnScoreBd)
        btnDeals = mView.findViewById(R.id.btnDeals)
        btnProfile = mView.findViewById(R.id.btnProfile)
        imgBtnAddnetwork = mView.findViewById(R.id.imgBtnAddnetwork)
        imgBtnFilter = mView.findViewById(R.id.imgBtnFilter)
        edtTxtSearch = mView.findViewById(R.id.edtTxtSearch)
        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)


        try {
            accountF = arguments!!.getString("GROUP")
            designationF = arguments!!.getString("DESIGNATION")
        } catch (e: Exception) {
            accountF = ""
            designationF = ""
        }

        edtTxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchTag = p0.toString()
                NetworkRequest(activity!!, ResponceProcessorNetworks()).networkList(searchTag, 1)
            }
        })

        rvNetworkAdapter = RvNetworkAdapter(activity!!)
        rvNetwork.adapter = rvNetworkAdapter

        progressDialog.show()



        btnHome.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, HomeFeedsActivity::class.java)) }
        btnScoreBd.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, ScoreboardActivity::class.java)) }
        btnDeals.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, DealsActivity::class.java)) }
        btnProfile.setOnClickListener { activity!!.finish()
            startActivity(Intent(activity, MyProfileActivity::class.java)) }
        imgBtnAddnetwork.setOnClickListener { startActivity(Intent(activity, NetworkUploadActivity::class.java)) }
        imgBtnFilter.setOnClickListener {
            val next=Intent(activity, FiltersActivity::class.java)
            next.putExtra("GROUP", accountF)
            next.putExtra("DESIGNATION", designationF)
            startActivity(next)
        }

        return mView
    }

    inner class ResponceProcessorNetwork : ProcessResponcceInterphase<NetworkResponce> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: NetworkResponce) {

            if (responce.data!!.isNotEmpty()) {
                rvNetworkAdapter.set(responce.data!!, accountF, designationF, "")
                rvNetwork.recycledViewPool.setMaxRecycledViews(0, 0)
                rvNetwork.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            }
            progressDialog.dismiss()
        }

    }

    inner class ResponceProcessorNetworks : ProcessResponcceInterphase<NetworkResponce> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: NetworkResponce) {

            if (responce.data!!.isNotEmpty()) {
                rvNetworkAdapter.set(responce.data!!, accountF, designationF, searchTag)
                rvNetwork.recycledViewPool.setMaxRecycledViews(0, 0)
                rvNetwork.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            }
            progressDialog.dismiss()
        }

    }


}
