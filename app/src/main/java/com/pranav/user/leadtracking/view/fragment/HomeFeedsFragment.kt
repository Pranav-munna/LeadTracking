package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AllFeedsRequest
import com.pranav.user.leadtracking.controller.responce.AllFeedsResponce
import com.pranav.user.leadtracking.view.activity.*
import com.pranav.user.leadtracking.view.adapter.RvFeedAdapter

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFeedsFragment : Fragment() {

    lateinit var rvFeeds: RecyclerView
    lateinit var btnNetwork: Button
    lateinit var btnScoreBd: Button
    lateinit var btnDeals: Button
    lateinit var btnProfile: Button
    lateinit var imgBtnUpload: ImageButton
    lateinit var rvFeedAdapter: RvFeedAdapter
    lateinit var progressDialog: Dialog

    override fun onResume() {
        super.onResume()
        AllFeedsRequest(activity!!, ResponceProcessorFeeds()).getCategories(1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_home_feeds, container, false)

        rvFeeds = mView.findViewById(R.id.rvFeeds)
        btnNetwork = mView.findViewById(R.id.btnNetwork)
        btnScoreBd = mView.findViewById(R.id.btnScoreBd)
        btnDeals = mView.findViewById(R.id.btnDeals)
        btnProfile = mView.findViewById(R.id.btnProfile)
        imgBtnUpload = mView.findViewById(R.id.imgBtnUpload)

        val permissions = arrayOf(Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_CALENDAR)

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, permissions, 101)

        }

        rvFeedAdapter = RvFeedAdapter(activity!!)
        rvFeeds.adapter = rvFeedAdapter
        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressDialog.show()

        btnNetwork.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, NetworkActivity::class.java))
        }
        btnScoreBd.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, ScoreboardActivity::class.java))
        }
        btnDeals.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, DealsActivity::class.java))
        }
        btnProfile.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, MyProfileActivity::class.java))
        }
        imgBtnUpload.setOnClickListener {
            activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(Intent(activity, FeedsUploadActivity::class.java))
        }

        return mView
    }

    inner class ResponceProcessorFeeds : ProcessResponcceInterphase<Array<AllFeedsResponce>> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: Array<AllFeedsResponce>) {

            if (responce.isNotEmpty()) {
                rvFeedAdapter.set(responce)
                rvFeeds.recycledViewPool.setMaxRecycledViews(0, 0)
                rvFeeds.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            }
            progressDialog.dismiss()
        }

    }

}
