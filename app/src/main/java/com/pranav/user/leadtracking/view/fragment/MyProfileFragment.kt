package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DesignationRequest
import com.pranav.user.leadtracking.controller.request.ProfileRequest
import com.pranav.user.leadtracking.controller.request.ProfileUpdateRequest
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.controller.responce.EditNetworkResponce
import com.pranav.user.leadtracking.controller.responce.ProfileResponce
import com.pranav.user.leadtracking.view.activity.*
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.util.*


class MyProfileFragment : Fragment() {

    lateinit var btnHome: Button
    lateinit var btnNetwork: Button
    lateinit var btnDeals: Button
    lateinit var btnScoreBd: Button
    lateinit var txtVwEdit: TextView
    lateinit var edtName: EditText
    lateinit var edtLname: EditText
    lateinit var edtdesig: Spinner
    //    lateinit var edtEmail: EditText
    lateinit var edtPhone: EditText
    lateinit var edtLocation: EditText
    lateinit var edtOfficeNumber: EditText
    lateinit var edtAbout: EditText
    lateinit var txtName: TextView
    lateinit var txtLname: TextView
    lateinit var txtdesig: TextView
    lateinit var txtLocation: TextView
    lateinit var txtEmail: TextView
    lateinit var txtPhone: TextView
    lateinit var tvAddEdit: TextView
    lateinit var txtOfficeNumber: TextView
    lateinit var txtAbout: TextView
    lateinit var txtVwPRofile: TextView
    lateinit var txtVwname: TextView
    lateinit var imbVwPRofile: ImageView
    lateinit var logout: ImageButton
    lateinit var imgBtnCamera: ImageButton
    var decName = ""
    var decId = "0"
    var finalFile: File? = null
    var imgDecodableString = ""
    var seletion = 0

    var decNameSpinner = ArrayList<String>()
    var dec_pos = 0
    var decIdSpinner = ArrayList<String>()
    lateinit var adapterDesgination: ArrayAdapter<String>
    lateinit var progressDialog: Dialog
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_my_profile, container, false)
        btnHome = mView.findViewById(R.id.btnHome)
        btnNetwork = mView.findViewById(R.id.btnNetwork)
        btnDeals = mView.findViewById(R.id.btnDeals)
        btnScoreBd = mView.findViewById(R.id.btnScoreBd)
        txtVwEdit = mView.findViewById(R.id.txtVwEdit)
        edtName = mView.findViewById(R.id.edtName)
        edtLname = mView.findViewById(R.id.edtLname)
        edtdesig = mView.findViewById(R.id.edtdesig)
//        edtEmail = mView.findViewById(R.id.edtEmail)
        edtPhone = mView.findViewById(R.id.edtPhone)
        edtLocation = mView.findViewById(R.id.edtLocation)
        edtOfficeNumber = mView.findViewById(R.id.edtOfficeNumber)
        edtAbout = mView.findViewById(R.id.edtAbout)
        txtName = mView.findViewById(R.id.txtName)
        txtLname = mView.findViewById(R.id.txtLname)
        txtdesig = mView.findViewById(R.id.txtdesig)
        txtLocation = mView.findViewById(R.id.txtLocation)
        txtEmail = mView.findViewById(R.id.txtEmail)
        txtPhone = mView.findViewById(R.id.txtPhone)
        txtOfficeNumber = mView.findViewById(R.id.txtOfficeNumber)
        txtAbout = mView.findViewById(R.id.txtAbout)
        imbVwPRofile = mView.findViewById(R.id.imbVwPRofile)
        logout = mView.findViewById(R.id.logout)
        txtVwPRofile = mView.findViewById(R.id.txtVwPRofile)
        txtVwname = mView.findViewById(R.id.txtVwname)
        imgBtnCamera = mView.findViewById(R.id.imgBtnCamera)
        tvAddEdit = mView.findViewById(R.id.tvAddEdit)


        /*val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        activity!!.window.setLayout((width * .8).toInt(), height)
        activity!!.window.setGravity(Gravity.START)*/
        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)
        progressDialog.show()

        ProfileRequest(activity!!, ResponceProcessorProfile()).profile()

        adapterDesgination = ArrayAdapter(activity, R.layout.spinner_design, decNameSpinner)


        imgBtnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity!!, permissions, 101)
                    Toast.makeText(activity, "permission denied accessing camera", Toast.LENGTH_SHORT).show()
                }
                try {
                    CropImage.activity()
                            .setAllowFlipping(false)
                            .start(activity!!, this)
                } catch (e: Exception) {
                }

            } else {
                if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity!!, permissions, 101)
                }
                Toast.makeText(activity, "permission denied accessing camera and gallery", Toast.LENGTH_SHORT).show()
            }
        }

        logout.setOnClickListener {
            val sharedpreference: SharedPreferences = context!!.getSharedPreferences("Token_", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedpreference.edit()
            editor.putString("LOGIN", "0")
            editor.clear()
            editor.apply()
            val i = activity!!.baseContext.packageManager
                    .getLaunchIntentForPackage(activity!!.baseContext.packageName)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }

        edtdesig.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                txtdesig.text = edtdesig.selectedItem.toString()
                decId = decIdSpinner[p2]
            }
        }


        btnHome.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, HomeFeedsActivity::class.java))
        }
        btnNetwork.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, NetworkActivity::class.java))
        }
        btnDeals.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, DealsActivity::class.java))
        }
        btnScoreBd.setOnClickListener {
            activity!!.finish()
            startActivity(Intent(activity, ScoreboardActivity::class.java))
        }
        txtVwEdit.setOnClickListener {
            if (edtName.visibility == View.GONE) {
                txtVwEdit.text = "Save"

                edtdesig.setSelection(seletion)

                edtName.setText(txtName.text)
                edtName.visibility = View.VISIBLE
                txtName.visibility = View.GONE

                edtLname.setText(txtLname.text)
                edtLname.visibility = View.VISIBLE
                txtLname.visibility = View.GONE

//                edtdesig.setText(txtdesig.text)
                edtdesig.visibility = View.VISIBLE
                txtdesig.visibility = View.GONE

                /*edtEmail.setText(txtEmail.text)
                edtEmail.visibility = View.VISIBLE
                txtEmail.visibility = View.GONE*/

                edtPhone.setText(txtPhone.text)
                edtPhone.visibility = View.VISIBLE
                txtPhone.visibility = View.GONE

                edtLocation.setText(txtLocation.text)
                edtLocation.visibility = View.VISIBLE
                txtLocation.visibility = View.GONE

                edtOfficeNumber.setText(txtOfficeNumber.text)
                edtOfficeNumber.visibility = View.VISIBLE
                txtOfficeNumber.visibility = View.GONE

                edtAbout.setText(txtAbout.text)
                edtAbout.visibility = View.VISIBLE
                txtAbout.visibility = View.GONE

            } else {
                txtVwEdit.text = "Edit"

                txtName.text = edtName.text
                edtName.visibility = View.GONE
                txtName.visibility = View.VISIBLE

                txtLname.text = edtLname.text
                edtLname.visibility = View.GONE
                txtLname.visibility = View.VISIBLE

//                txtdesig.text = edtdesig.text
                edtdesig.visibility = View.GONE
                txtdesig.visibility = View.VISIBLE

                txtLocation.text = edtLocation.text
                edtLocation.visibility = View.GONE
                txtLocation.visibility = View.VISIBLE

                /*txtEmail.setText(edtEmail.text)
                edtEmail.visibility = View.GONE
                txtEmail.visibility = View.VISIBLE*/

                txtPhone.text = edtPhone.text
                edtPhone.visibility = View.GONE
                txtPhone.visibility = View.VISIBLE

                txtOfficeNumber.text = edtOfficeNumber.text
                edtOfficeNumber.visibility = View.GONE
                txtOfficeNumber.visibility = View.VISIBLE

                txtAbout.text = edtAbout.text
                edtAbout.visibility = View.GONE
                txtAbout.visibility = View.VISIBLE

                ProfileUpdateRequest(activity!!, ResponceProcessorUpdatedProfile()).update(edtAbout.text.toString(),
                        edtPhone.text.toString(), decId, edtName.text.toString(), edtLname.text.toString(),
                        edtLocation.text.toString(), edtOfficeNumber.text.toString())

            }
        }

        tvAddEdit.setOnClickListener {
            startActivity(Intent(activity, AccountAddEditActivity::class.java))
        }

        return mView
    }

    inner class ResponceProcessorUpdatedProfile : ProcessResponcceInterphase<EditNetworkResponce> {
        override fun processResponce(responce: EditNetworkResponce) {
            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            ProfileRequest(activity!!, ResponceProcessorProfile()).profile()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorDesignation : ProcessResponcceInterphase<Array<DesignationResponce>> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: Array<DesignationResponce>) {
            var count = 0
            decNameSpinner.add("--select--")
            decIdSpinner.add("0")
            while (count != responce.size) {
                if (txtdesig.text.toString() == responce[count].name.toString()) {
                    seletion = (count + 1)
                    decId = responce[count].id.toString()
                }
                decNameSpinner.add(responce[count].name.toString())
                decIdSpinner.add(responce[count].id.toString())
                count++
            }

            edtdesig.adapter = adapterDesgination
            adapterDesgination.notifyDataSetChanged()
        }

    }

    inner class ResponceProcessorProfile : ProcessResponcceInterphase<ProfileResponce> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

        override fun processResponce(responce: ProfileResponce) {
            DesignationRequest(activity!!, ResponceProcessorDesignation()).desgination()
            if (responce.firstName != null) {
                txtName.text = responce.firstName
                txtVwname.text = responce.firstName
                txtVwPRofile.text = responce.firstName!![0].toString()
            }
            if (responce.lastName != null) {
                txtLname.text = responce.lastName
                txtVwname.text = txtVwname.text.toString() + " " + responce.lastName
            }
            if (responce.designation != null) {
                txtdesig.text = responce.designation!!.name
                txtVwname.text = txtVwname.text.toString() + "\n" + responce.designation!!.name
            }

            if (responce.location != null)
                txtLocation.text = responce.location

            if (responce.email != null)
                txtEmail.text = responce.email

            if (responce.contactNumber != null)
                txtPhone.text = responce.contactNumber

            if (responce.officeNumber != null)
                txtOfficeNumber.text = responce.officeNumber

            if (responce.about != null)
                txtAbout.text = responce.about


            if (responce.profileImage != null)
                Glide.with(activity!!)
                        .asBitmap()
                        .load(responce.profileImage/*!!.replace("""\""", "")*/)
                        .into(imbVwPRofile)

            progressDialog.dismiss()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val results = CropImage.getActivityResult(data)
                val resultUri = results.uri
                imbVwPRofile.setImageURI(resultUri)

                finalFile = File(resultUri.path)
                imgDecodableString = finalFile.toString()

                try {
                    ProfileUpdateRequest(activity!!, ResponceProcessorUpdatedProfile()).updateImage(finalFile!!)
                } catch (e: Exception) {
                    Toast.makeText(activity, "Select Image", Toast.LENGTH_SHORT).show()
                }

            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Select Image", Toast.LENGTH_SHORT).show()
        }

    }

}
