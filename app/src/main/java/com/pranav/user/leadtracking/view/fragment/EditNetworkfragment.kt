package com.pranav.user.leadtracking.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DesignationRequest
import com.pranav.user.leadtracking.controller.request.EditNetworkRequest
import com.pranav.user.leadtracking.controller.request.GroupRequest
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.controller.responce.EditNetworkResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.activity.NetworkDetailsActivity
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.*

class EditNetworkfragment : Fragment() {

    lateinit var cirImgVwImage: CircleImageView
    lateinit var edtTxtName: EditText
    lateinit var edtTxtMobile: EditText
    lateinit var edtTxtMAil: EditText
    lateinit var txtVWImage: TextView
    lateinit var spnrDesignation: Spinner
    lateinit var spnrAccount: Spinner
    lateinit var spnrCountry: Spinner
    lateinit var butnBack: Button
    lateinit var btnSave: Button

    var imgDecodableString = ""
    var finalFile: File? = null

    var decName = ArrayList<String>()
    var dec_pos = 0
    var decId = ArrayList<String>()

    var account_name = ArrayList<String>()
    var account_pos = 0
    var account_id = ArrayList<String>()

    var country_name = ArrayList<String>()


    lateinit var adapterDesgination: ArrayAdapter<String>
    lateinit var adapterAccount: ArrayAdapter<String>
    lateinit var adapterCountry: ArrayAdapter<String>

    var DESIGNATION_ID = ""
    var GROUP_ID = ""
    var ID = ""
    var LAST_CONNECTED_AT = ""
    var IMAGE = ""
    var COUNTRY_CODE = "+1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_edit_network, container, false)
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)
        country_name.add("India")
        country_name.add("United States")
        adapterCountry = ArrayAdapter(activity, R.layout.spinner_design, country_name)

        cirImgVwImage = mView.findViewById(R.id.cirImgVwImage)
        edtTxtName = mView.findViewById(R.id.edtTxtName)
        txtVWImage = mView.findViewById(R.id.txtVWImage)
        edtTxtMobile = mView.findViewById(R.id.edtTxtMobile)
        edtTxtMAil = mView.findViewById(R.id.edtTxtMAil)
        spnrDesignation = mView.findViewById(R.id.spnrDesignation)
        spnrAccount = mView.findViewById(R.id.spnrAccount)
        spnrCountry = mView.findViewById(R.id.spnrCountry)
        butnBack = mView.findViewById(R.id.butnBack)
        btnSave = mView.findViewById(R.id.btnSave)

        DesignationRequest(activity!!, ResponceProcessorDesignations()).desgination()
        GroupRequest(activity!!, ResponceProcessorGroups()).groups()


        IMAGE = arguments!!.getString("IMAGE")
        var NAME = arguments!!.getString("NAME")
        DESIGNATION_ID = arguments!!.getString("DESIGNATION_ID")
        COUNTRY_CODE = arguments!!.getString("COUNTRY_CODE")
        var PHONE = arguments!!.getString("PHONE")
        var MAIL = arguments!!.getString("MAIL")
        GROUP_ID = arguments!!.getString("GROUP_ID")
        ID = arguments!!.getString("ID")
        LAST_CONNECTED_AT = arguments!!.getString("LAST_CONNECTED_AT")

        spnrCountry.adapter = adapterCountry!!
        if (COUNTRY_CODE == "+91") {
            spnrCountry.setSelection(0)
        } else
            spnrCountry.setSelection(1)
        adapterCountry.notifyDataSetChanged()
        if (IMAGE != "null") {
            Glide.with(activity!!)
                    .asBitmap()
                    .load(IMAGE)
                    .into(cirImgVwImage)
        } else
            txtVWImage.text = NAME
        edtTxtName.setText(NAME)
        edtTxtMobile.setText(PHONE)
        edtTxtMAil.setText(MAIL)

        adapterDesgination = ArrayAdapter<String>(activity, R.layout.spinner_design, decName)
        adapterAccount = ArrayAdapter<String>(activity, R.layout.spinner_design, account_name)


        spnrDesignation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(activity, spnrDesignation.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                dec_pos = spnrDesignation.selectedItemPosition.toInt()
            }
        }
        spnrAccount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(activity, spnrAccount.selectedItemPosition.toString(), Toast.LENGTH_SHORT).show()
                account_pos = spnrAccount.selectedItemPosition.toInt()
            }
        }
        spnrCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spnrCountry.selectedItemPosition == 0)
                    COUNTRY_CODE = "+91"
                else
                    COUNTRY_CODE = "+1"
//                Toast.makeText(activity, spnrCountry.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        cirImgVwImage.setOnClickListener {

            CropImage.activity()
                    .setAllowFlipping(false)
                    .setAspectRatio(1, 1)
                    .start(activity!!, this)

        }

        butnBack.setOnClickListener { activity!!.onBackPressed() }


        btnSave.setOnClickListener {
            if (imgDecodableString == "") {
                EditNetworkRequest(activity!!, ResponceProcessorEdit()).EditNetwork(edtTxtMAil.text.toString(),
                        account_id[account_pos].toString(),
                        edtTxtName.text.toString(),
                        COUNTRY_CODE,
                        edtTxtMobile.text.toString(),
                        decId[dec_pos],
                        ID)
            } else {
                EditNetworkRequest(activity!!, ResponceProcessorEdit2()).EditNetworkImage(edtTxtMAil.text.toString(),
                        account_id[account_pos],
                        edtTxtName.text.toString(),
                        COUNTRY_CODE,
                        edtTxtMobile.text.toString(),
                        decId[dec_pos],
                        ID, finalFile!!)
                EditNetworkRequest(activity!!, ResponceProcessorEdit()).EditNetwork(edtTxtMAil.text.toString(),
                        account_id[account_pos],
                        edtTxtName.text.toString(),
                        COUNTRY_CODE,
                        edtTxtMobile.text.toString(),
                        decId[dec_pos],
                        ID)
//                EditNetworkRequest(activity!!, ResponceProcessorEdit()).image(ID, finalFile!!)
            }
        }

        return mView
    }

    inner class ResponceProcessorEdit2 : ProcessResponcceInterphase<EditNetworkResponce> {
        override fun processResponce(responce: EditNetworkResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorEdit : ProcessResponcceInterphase<EditNetworkResponce> {
        override fun processResponce(responce: EditNetworkResponce) {
            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            val next = Intent(context, NetworkDetailsActivity::class.java)
            next.putExtra("NAME", edtTxtName.text.toString())
            next.putExtra("ACCOUNT_NAME", account_name[account_pos].toString())
            next.putExtra("COUNTRY_CODE", COUNTRY_CODE)
            next.putExtra("PHONE", edtTxtMobile.text.toString())
            next.putExtra("DESIGNATION", decName[dec_pos].toString())
            next.putExtra("DESIGNATION_ID", decId[dec_pos].toString())
            next.putExtra("LAST_CONNECTED_AT", LAST_CONNECTED_AT.toString())
            next.putExtra("MAIL", edtTxtMAil.text.toString())
            next.putExtra("IMAGE", IMAGE)
            next.putExtra("GROUP_ID", account_id[account_pos].toString())
            next.putExtra("ID", ID)
            activity!!.startActivity(next)
        }

        override fun processResponceError(responce: Any?) {
            Toast.makeText(activity!!, "Number/Email already taken", Toast.LENGTH_SHORT).show()
        }

    }

    inner class ResponceProcessorGroups : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {
            var count = 0
            var pos = 0

            while (count < responce.size) {
                account_name.add(responce[count].accountName.toString())
                account_id.add(responce[count].id.toString())
                if (responce[count].id.toString() == GROUP_ID)
                    pos = count
                count++
            }
            spnrAccount.adapter = adapterAccount
            spnrAccount.setSelection(pos)
            adapterAccount.notifyDataSetChanged()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorDesignations : ProcessResponcceInterphase<Array<DesignationResponce>> {
        override fun processResponce(responce: Array<DesignationResponce>) {
            var count = 0
            var pos = 0

            while (count < responce.size) {
                decName.add(responce[count].name.toString())
                decId.add(responce[count].id.toString())
                if (responce[count].id.toString() == DESIGNATION_ID)
                    pos = count
                count++
            }
            spnrDesignation.adapter = adapterDesgination
            spnrDesignation.setSelection(pos)
            adapterDesgination.notifyDataSetChanged()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val results = CropImage.getActivityResult(data)
                val resultUri = results.uri

                finalFile = File(resultUri.path)
                imgDecodableString = finalFile.toString()

                cirImgVwImage.setImageURI(resultUri)
                txtVWImage.text = ""

//                Toast.makeText(activity, finalFile!!.length().toString(), Toast.LENGTH_SHORT).show()


//                val bitmap = data!!.extras.get("data") as Bitmap

//                val selectedImage = getImageUri(activity!!.applicationContext, bitmap)


//                Request(activity, ResponceProcessor()).sendImage("title", "name", "image", File(imgDecodableString))


            }
        } catch (e: Exception) {


        }


    }


}
