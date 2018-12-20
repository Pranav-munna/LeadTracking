package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.CallRequest
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.view.activity.AddDealsActivity
import com.pranav.user.leadtracking.view.adapter.DealDetailTabAdapter
import de.hdodenhof.circleimageview.CircleImageView

class DealProfileFragment : Fragment(), TabLayout.OnTabSelectedListener {


    internal lateinit var tablayout: TabLayout
    internal lateinit var viewPager: ViewPager
    internal lateinit var TxtVwNameTop: TextView
    internal lateinit var txtVwEstimate: TextView
    internal lateinit var cirImgVwProfile: CircleImageView
    internal lateinit var txtVwName: TextView
    internal lateinit var btnCall: Button
    internal lateinit var btnBack: Button
    internal lateinit var imgBtnEdit: ImageButton
    private val ALL_PERMISSIONS = 101
    private val permissions = arrayOf(Manifest.permission.CALL_PHONE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_deal_profile, container, false)

        tablayout = mView.findViewById(R.id.tablayout)
        viewPager = mView.findViewById(R.id.viewPager)
        TxtVwNameTop = mView.findViewById(R.id.TxtVwNameTop)
        txtVwEstimate = mView.findViewById(R.id.txtVwEstimate)
        cirImgVwProfile = mView.findViewById(R.id.cirImgVwProfile)
        txtVwName = mView.findViewById(R.id.txtVwName)
        btnCall = mView.findViewById(R.id.btnCall)
        btnBack = mView.findViewById(R.id.btnBack)
        imgBtnEdit = mView.findViewById(R.id.imgBtnEdit)


        var ID = arguments!!.getString("ID")
        var NAME = arguments!!.getString("NAME")
        var VALUE = arguments!!.getString("VALUE")
        var START_DATE = arguments!!.getString("START_DATE")
        var END_DATE = arguments!!.getString("END_DATE")
        var NAME2 = arguments!!.getString("NAME2")
        var ACCOUNT_NAME = arguments!!.getString("ACCOUNT_NAME")
        var BILLING_TYPE = arguments!!.getString("BILLING_TYPE")
        var DELIVERABLE_TYPE = arguments!!.getString("DELIVERABLE_TYPE")


        var COUNTRY_CODE = arguments!!.getString("COUNTRY_CODE")
        var PHONE = arguments!!.getString("PHONE")
        var DESIGNATION = arguments!!.getString("DESIGNATION")
        var DESIGNATION_ID = arguments!!.getString("DESIGNATION_ID")
        var LAST_CONNECTED_AT = arguments!!.getString("LAST_CONNECTED_AT")
        var IMAGE = arguments!!.getString("IMAGE")
        var GROUP_ID = arguments!!.getString("GROUP_ID")
        var MAIL = arguments!!.getString("MAIL")
        var LEVEL_VALUE = arguments!!.getString("LEVEL_VALUE")
        var LEVEL_ID = arguments!!.getString("LEVEL_ID")
        var MEMBERS = arguments!!.getString("MEMBERS")
        var CONTACT_ID = arguments!!.getString("CONTACT_ID")
        var STAGE = arguments!!.getString("STAGE")


        TxtVwNameTop.text = NAME
        txtVwEstimate.text = "Estimated Revenue : $$VALUE"
        txtVwName.text = NAME2
        if (IMAGE != "null")
            Glide.with(activity!!)
                    .asBitmap()
                    .load(IMAGE)
                    .into(cirImgVwProfile)

        btnCall.setOnClickListener {
            Toast.makeText(activity, "Calling $COUNTRY_CODE $PHONE", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$COUNTRY_CODE${PHONE.replace(COUNTRY_CODE, "")}"))
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                CallRequest(activity!!, ResponceProcesssorCall()).DealCall(ID)
                activity!!.startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(activity!!, permissions, ALL_PERMISSIONS)
                Toast.makeText(activity, "Call Permission denied... ", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener { activity!!.onBackPressed() }


        imgBtnEdit.setOnClickListener {
            var next = Intent(activity, AddDealsActivity::class.java)
            next.putExtra("ID", ID)
            next.putExtra("NAME", NAME)
            next.putExtra("VALUE", VALUE)
            next.putExtra("START_DATE", START_DATE)
            next.putExtra("END_DATE", END_DATE)
            next.putExtra("NAME2", NAME2)
            next.putExtra("ACCOUNT_NAME", ACCOUNT_NAME)
            next.putExtra("BILLING_TYPE", BILLING_TYPE)
            next.putExtra("DELIVERABLE_TYPE", DELIVERABLE_TYPE)

            next.putExtra("COUNTRY_CODE", COUNTRY_CODE)
            next.putExtra("PHONE", PHONE)
            next.putExtra("DESIGNATION", DESIGNATION)
            next.putExtra("DESIGNATION_ID", DESIGNATION_ID)
            next.putExtra("LAST_CONNECTED_AT", LAST_CONNECTED_AT)
            next.putExtra("IMAGE", IMAGE)
            next.putExtra("GROUP_ID", GROUP_ID)
            next.putExtra("MAIL", MAIL)
            next.putExtra("LEVEL_VALUE", LEVEL_VALUE)
            next.putExtra("LEVEL_ID", LEVEL_ID)
            next.putExtra("MEMBERS", MEMBERS)
            next.putExtra("CONTACT_ID", CONTACT_ID)
            startActivity(next)
        }

        tablayout.addTab(tablayout.newTab().setText("Deal Detail"))
        tablayout.addTab(tablayout.newTab().setText("Meetings"))
        tablayout.addTab(tablayout.newTab().setText("Timeline"))
        tablayout.addTab(tablayout.newTab().setText("Notes"))
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))

        val tabPagerAdapter = DealDetailTabAdapter(fragmentManager!!, tablayout.tabCount)
        viewPager.adapter = tabPagerAdapter
        tabPagerAdapter.set(LEVEL_VALUE, ID, GROUP_ID, LEVEL_ID, MEMBERS, CONTACT_ID, NAME, STAGE)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = p0!!.position
            }
        })

        return mView
    }

    inner class ResponceProcesssorCall : ProcessResponcceInterphase<AddDealResponce> {
        override fun processResponce(responce: AddDealResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun onTabReselected(p0: TabLayout.Tab?) {}
    override fun onTabUnselected(p0: TabLayout.Tab?) {}
    override fun onTabSelected(p0: TabLayout.Tab?) {}

}
