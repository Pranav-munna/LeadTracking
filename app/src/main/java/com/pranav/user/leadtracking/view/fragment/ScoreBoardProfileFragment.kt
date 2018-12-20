package com.pranav.user.leadtracking.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DesignationRequest
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.view.activity.DealsActivity
import com.pranav.user.leadtracking.view.activity.HomeFeedsActivity
import com.pranav.user.leadtracking.view.activity.MyProfileActivity
import com.pranav.user.leadtracking.view.activity.NetworkActivity

class ScoreBoardProfileFragment : Fragment() {

    lateinit var imgBtnBack: ImageButton
    lateinit var imbVwPRofile: ImageView
    lateinit var txtVwname: TextView
    lateinit var txtName: TextView
    lateinit var txtLname: TextView
    lateinit var txtdesig: TextView
    lateinit var txtLocation: TextView
    lateinit var txtEmail: TextView
    lateinit var txtPhone: TextView
    lateinit var txtOfficeNumber: TextView
    lateinit var txtVwPRofile: TextView
    lateinit var txtAbout: TextView
    lateinit var btnProfile: Button
    lateinit var btnDeals: Button
    lateinit var btnHome: Button
    lateinit var btnNetwork: Button
    var DESIGNATION_ID = "-1"
    var F_NAME = ""
    var L_NAME = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_score_board_profile, container, false)
        var IMAGE = arguments!!.getString("IMAGE")
        F_NAME = arguments!!.getString("F_NAME")
        L_NAME = arguments!!.getString("L_NAME")
        DESIGNATION_ID = arguments!!.getString("DESIGNATION_ID")
        var LOCATION = arguments!!.getString("LOCATION")
        var EMAIL = arguments!!.getString("EMAIL")
        var NUMBER = arguments!!.getString("NUMBER")
        var OFFICE_NUMBER = arguments!!.getString("OFFICE_NUMBER")
        var ABOUT = arguments!!.getString("ABOUT")

        DesignationRequest(activity!!, ResponceProcessorDesignation()).desgination()

        imgBtnBack = mView.findViewById(R.id.imgBtnBack)
        imbVwPRofile = mView.findViewById(R.id.imbVwPRofile)
        txtVwname = mView.findViewById(R.id.txtVwname)
        txtName = mView.findViewById(R.id.txtName)
        txtLname = mView.findViewById(R.id.txtLname)
        txtLocation = mView.findViewById(R.id.txtLocation)
        txtEmail = mView.findViewById(R.id.txtEmail)
        txtPhone = mView.findViewById(R.id.txtPhone)
        txtOfficeNumber = mView.findViewById(R.id.txtOfficeNumber)
        txtAbout = mView.findViewById(R.id.txtAbout)
        txtdesig = mView.findViewById(R.id.txtdesig)
        btnProfile = mView.findViewById(R.id.btnProfile)
        btnDeals = mView.findViewById(R.id.btnDeals)
        btnHome = mView.findViewById(R.id.btnHome)
        btnNetwork = mView.findViewById(R.id.btnNetwork)
        txtVwPRofile = mView.findViewById(R.id.txtVwPRofile)

        txtVwname.text = F_NAME + " " + L_NAME
        txtVwPRofile.text = F_NAME[0].toString().toUpperCase()
        txtLname.text = L_NAME
        txtName.text = F_NAME
        txtLocation.text = LOCATION
        txtEmail.text = EMAIL
        txtPhone.text = NUMBER
        txtOfficeNumber.text = OFFICE_NUMBER
        txtAbout.text = ABOUT
        Glide.with(activity!!)
                .asBitmap()
                .load(IMAGE)
                .into(imbVwPRofile)

        btnNetwork.setOnClickListener {
            startActivity(Intent(activity, NetworkActivity::class.java))
        }
        btnHome.setOnClickListener {
            startActivity(Intent(activity, HomeFeedsActivity::class.java))
        }
        btnDeals.setOnClickListener {
            startActivity(Intent(activity, DealsActivity::class.java))
        }
        btnProfile.setOnClickListener {
            startActivity(Intent(activity, MyProfileActivity::class.java))
        }
        imgBtnBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorDesignation : ProcessResponcceInterphase<Array<DesignationResponce>> {
        override fun processResponce(responce: Array<DesignationResponce>) {
            var count = 0
            while (count != responce.size) {
                if (responce[count].id.toString() == DESIGNATION_ID) {
                    txtVwname.text = txtVwname.text.toString() + "\n" + responce[count].name
                    txtdesig.text = responce[count].name
                }
                count++
            }

        }

        override fun processResponceError(responce: Any?) {
        }

    }
}
