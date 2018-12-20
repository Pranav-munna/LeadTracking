package com.pranav.user.leadtracking.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.ScoreboardRequest
import com.pranav.user.leadtracking.controller.responce.ScoreboardResponce
import com.pranav.user.leadtracking.view.activity.DealsActivity
import com.pranav.user.leadtracking.view.activity.HomeFeedsActivity
import com.pranav.user.leadtracking.view.activity.MyProfileActivity
import com.pranav.user.leadtracking.view.activity.NetworkActivity
import com.pranav.user.leadtracking.view.adapter.RvScoreboardAdapter
import java.util.*

class ScoreboardFragment : Fragment() {

    lateinit var rvScoreboard: RecyclerView
    lateinit var btnHome: Button
    lateinit var btnNetwork: Button
    lateinit var btnDeals: Button
    lateinit var btnProfile: Button
    lateinit var rvScoreboardAdapter: RvScoreboardAdapter
    lateinit var progressDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_scoreboard, container, false)

        rvScoreboard = mView.findViewById(R.id.rvScoreboard)
        btnHome = mView.findViewById(R.id.btnHome)
        btnNetwork = mView.findViewById(R.id.btnNetwork)
        btnDeals = mView.findViewById(R.id.btnDeals)
        btnProfile = mView.findViewById(R.id.btnProfile)

        rvScoreboardAdapter = RvScoreboardAdapter(activity!!)
        rvScoreboard.adapter = rvScoreboardAdapter

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ScoreboardRequest(activity!!, ResponceProcessorScoredoard()).ScoreboardList(1)

        btnHome.setOnClickListener { startActivity(Intent(activity, HomeFeedsActivity::class.java)) }
        btnNetwork.setOnClickListener { startActivity(Intent(activity, NetworkActivity::class.java)) }
        btnDeals.setOnClickListener { startActivity(Intent(activity, DealsActivity::class.java)) }
        btnProfile.setOnClickListener { startActivity(Intent(activity, MyProfileActivity::class.java)) }

        return mView
    }

    inner class ResponceProcessorScoredoard : ProcessResponcceInterphase<Array<ScoreboardResponce>> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: Array<ScoreboardResponce>) {
            rvScoreboardAdapter.set(ArrayList(Arrays.asList(*responce)))
            rvScoreboard.recycledViewPool.setMaxRecycledViews(0, 0)
            rvScoreboard.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            progressDialog.dismiss()
        }

    }

}
