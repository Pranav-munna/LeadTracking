package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import android.widget.*
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.CallRequest
import com.pranav.user.leadtracking.controller.request.DeleteContactRequest
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.controller.responce.AnswersResponce
import com.pranav.user.leadtracking.view.activity.EditNetworkActivity
import com.pranav.user.leadtracking.view.activity.NetworkActivity
import com.pranav.user.leadtracking.view.adapter.DealNetworkTabAdapter

class NetworkDetailsFragment : Fragment() {

    internal lateinit var tablayout: TabLayout
    internal lateinit var viewPager: ViewPager
    internal lateinit var imgVwProfileImg: ImageView
    internal lateinit var txtVwProfileImg: TextView
    internal lateinit var TxtVwNameTop: TextView
    internal lateinit var txtVwName: TextView
    internal lateinit var txtVwCmpny: TextView
    internal lateinit var btnBack: Button
    internal lateinit var btnCall: Button
    internal lateinit var imgBtnEdit: ImageButton
    lateinit var btnNeg: Button
    lateinit var btnPos: Button
    lateinit var txtVwContent: TextView
    lateinit var txtVwTitle: TextView
    private val ALL_PERMISSIONS = 101
    lateinit var dialogEditDelete: Dialog
    private val permissions = arrayOf(Manifest.permission.CALL_PHONE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_network_details, container, false)

        tablayout = mView.findViewById(R.id.tablayout)
        viewPager = mView.findViewById(R.id.viewPager)
        imgVwProfileImg = mView.findViewById(R.id.imgVwProfileImg)
        txtVwProfileImg = mView.findViewById(R.id.txtVwProfileImg)
        btnBack = mView.findViewById(R.id.btnBack)
        TxtVwNameTop = mView.findViewById(R.id.TxtVwNameTop)
        txtVwName = mView.findViewById(R.id.txtVwName)
        txtVwCmpny = mView.findViewById(R.id.txtVwCmpny)
        btnCall = mView.findViewById(R.id.btnCall)
        imgBtnEdit = mView.findViewById(R.id.imgBtnEdit)

        dialogEditDelete = Dialog(activity)
        dialogEditDelete.setContentView(R.layout.dialog_warning)
        dialogEditDelete.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogEditDelete.setCancelable(false)
        btnNeg = dialogEditDelete.findViewById(R.id.btnNeg)
        btnPos = dialogEditDelete.findViewById(R.id.btnPos)
        txtVwContent = dialogEditDelete.findViewById(R.id.txtVwContent)
        txtVwTitle = dialogEditDelete.findViewById(R.id.txtVwTitle)
        txtVwContent.text = ""
        txtVwTitle.text = "Choose an action"
        btnNeg.text = "Delete"
        btnPos.text = "Edit"

        var NAME = arguments!!.getString("NAME")
        var ACCOUNT_NAME = arguments!!.getString("ACCOUNT_NAME")
        var COUNTRY_CODE = arguments!!.getString("COUNTRY_CODE")
        var PHONE = arguments!!.getString("PHONE")
        var DESIGNATION = arguments!!.getString("DESIGNATION")
        var DESIGNATION_ID = arguments!!.getString("DESIGNATION_ID")
        var LAST_CONNECTED_AT = arguments!!.getString("LAST_CONNECTED_AT")
        var IMAGE = arguments!!.getString("IMAGE")
        var GROUP_ID = arguments!!.getString("GROUP_ID")
        var ID = arguments!!.getString("ID")
        var MAIL = arguments!!.getString("MAIL")

//Toast.makeText(activity!!,ACCOUNT_NAME,Toast.LENGTH_SHORT).show()

        tablayout.addTab(tablayout.newTab().setText("Deal Detail"))
        tablayout.addTab(tablayout.newTab().setText("Meetings"))
        tablayout.addTab(tablayout.newTab().setText("Notes"))
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))

        val tabPagerAdapter = DealNetworkTabAdapter(fragmentManager!!, tablayout.tabCount)
        viewPager.adapter = tabPagerAdapter
        tabPagerAdapter.set(DESIGNATION, LAST_CONNECTED_AT, GROUP_ID, ID, NAME)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = p0!!.position
            }
        })

        if (IMAGE != "null") {
            Glide.with(activity!!)
                    .asBitmap()
                    .load(IMAGE)
                    .into(imgVwProfileImg)
        } else
            txtVwProfileImg.text = NAME
        TxtVwNameTop.text = NAME
        txtVwName.text = NAME
        txtVwCmpny.text = ACCOUNT_NAME

        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$COUNTRY_CODE$PHONE"))
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                CallRequest(activity!!, ResponceProcessorCall()).NetworkCall(ID)
                activity!!.startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(activity!!, permissions, ALL_PERMISSIONS)
                Toast.makeText(activity, "Call Permission denied... ", Toast.LENGTH_SHORT).show()
            }
        }

        btnPos.setOnClickListener {
            val next = Intent(context, EditNetworkActivity::class.java)
            next.putExtra("IMAGE", IMAGE)
            next.putExtra("NAME", NAME)
            next.putExtra("DESIGNATION_ID", DESIGNATION_ID)
            next.putExtra("COUNTRY_CODE", COUNTRY_CODE)
            next.putExtra("PHONE", PHONE)
            next.putExtra("MAIL", MAIL)
            next.putExtra("GROUP_ID", GROUP_ID)
            next.putExtra("ID", ID)
            next.putExtra("LAST_CONNECTED_AT", LAST_CONNECTED_AT)
            startActivity(next)
            dialogEditDelete.dismiss()
        }
        btnNeg.setOnClickListener {

            DeleteContactRequest(activity!!, ResponceProcessorDeleteContact()).DeleteContact(ID)
        }

        imgBtnEdit.setOnClickListener {
            dialogEditDelete.show()

        }
        btnBack.setOnClickListener {
            val prefs = activity!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(activity, NetworkActivity::class.java))
        }
        return mView
    }

    inner class ResponceProcessorDeleteContact : ProcessResponcceInterphase<AnswersResponce> {
        override fun processResponce(responce: AnswersResponce) {
            activity!!.finish()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorCall : ProcessResponcceInterphase<AddDealResponce> {
        override fun processResponce(responce: AddDealResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

}