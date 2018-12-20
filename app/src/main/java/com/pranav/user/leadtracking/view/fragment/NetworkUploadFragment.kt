package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AddNetworkRequest
import com.pranav.user.leadtracking.controller.request.DesignationRequest
import com.pranav.user.leadtracking.controller.request.GroupRequest
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce
import com.pranav.user.leadtracking.controller.responce.DesignationResponce
import com.pranav.user.leadtracking.controller.responce.ErrorMessageResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.activity.NetworkActivity
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.util.*
import android.provider.ContactsContract
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log


class NetworkUploadFragment : Fragment() {

    var decName = ArrayList<String>()
    var dec_pos = 0
    var decId = ArrayList<String>()

    var account_name = ArrayList<String>()
    var account_pos = 0
    var account_id = ArrayList<String>()

    var country_name = ArrayList<String>()
    var COUNTRY_CODE = ""

    var imgDecodableString = ""
    var finalFile: File? = null

    lateinit var spnrDesignation: Spinner
    lateinit var spnrAccount: Spinner
    lateinit var spnrCountry: Spinner
    lateinit var cirImgVwImage: CircleImageView
    lateinit var btnSave: Button
    lateinit var edtTxtNAme: EditText
    lateinit var EdtTxtMobile: EditText
    lateinit var EdtTxtEmail: EditText

    lateinit var adapterDesgination: ArrayAdapter<String>
    lateinit var adapterAccount: ArrayAdapter<String>
    lateinit var adapterCountry: ArrayAdapter<String>
    lateinit var progressDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_network_upload, container, false)
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)

        spnrDesignation = mView.findViewById(R.id.spnrDesignation)
        spnrAccount = mView.findViewById(R.id.spnrAccount)
        spnrCountry = mView.findViewById(R.id.spnrCountry)
        cirImgVwImage = mView.findViewById(R.id.cirImgVwImage)
        btnSave = mView.findViewById(R.id.btnSave)
        edtTxtNAme = mView.findViewById(R.id.edtTxtNAme)
        EdtTxtMobile = mView.findViewById(R.id.EdtTxtMobile)
        EdtTxtEmail = mView.findViewById(R.id.EdtTxtEmail)

//        readContacts()

        DesignationRequest(activity!!, ResponceProcessorDesignations1()).desgination()
        GroupRequest(activity!!, ResponceProcessorGroups1()).groups()

        adapterDesgination = ArrayAdapter<String>(activity, R.layout.spinner_design, decName)
        adapterAccount = ArrayAdapter<String>(activity, R.layout.spinner_design, account_name)

        country_name.add("--Country--")
        country_name.add("India")
        country_name.add("United States")
        adapterCountry = ArrayAdapter<String>(activity, R.layout.spinner_design, country_name)
        spnrCountry.adapter = adapterCountry
        adapterCountry.notifyDataSetChanged()

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
                if (spnrCountry.selectedItemPosition == 1)
                    COUNTRY_CODE = "+91"
                else
                    if (spnrCountry.selectedItemPosition == 2)
                        COUNTRY_CODE = "+1"
                    else
                        COUNTRY_CODE = ""
//                Toast.makeText(activity, spnrCountry.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        cirImgVwImage.setOnClickListener {
            CropImage.activity()
                    .setAllowFlipping(false)
                    .setAspectRatio(1, 1)
                    .start(activity!!, this)
        }
        btnSave.setOnClickListener {
            if (edtTxtNAme.text.trim().isEmpty()) {
                Toast.makeText(activity, "Enter The Name", Toast.LENGTH_SHORT).show()
            } else
                if (spnrDesignation.selectedItemPosition == 0) {
                    Toast.makeText(activity, "Select Designation", Toast.LENGTH_SHORT).show()
                } else
                    if (spnrAccount.selectedItemPosition == 0) {
                        Toast.makeText(activity, "Select Account", Toast.LENGTH_SHORT).show()
                    } else
                        if (spnrCountry.selectedItemPosition == 0) {
                            Toast.makeText(activity, "Select Country", Toast.LENGTH_SHORT).show()
                        } else
                            if (EdtTxtMobile.text.trim().isEmpty()) {
                                Toast.makeText(activity, "Enter The Phone Number", Toast.LENGTH_SHORT).show()
                            } else
                                if (EdtTxtEmail.text.trim().isEmpty()) {
                                    Toast.makeText(activity, "Enter Email address", Toast.LENGTH_SHORT).show()
                                } else {
                                    progressDialog.show()
                                    AddNetworkRequest(activity!!, ResponceProcessorAppNetwork2()).AddNetwork(EdtTxtMobile.text.toString(),
                                            EdtTxtEmail.text.toString(), account_id[spnrAccount.selectedItemPosition],
                                            decId[spnrDesignation.selectedItemPosition],
                                            edtTxtNAme.text.toString(), COUNTRY_CODE)

                                    AddNetworkRequest(activity!!, ResponceProcessorAppNetwork()).AddNetworkImage(EdtTxtMobile.text.toString(),
                                            EdtTxtEmail.text.toString(), account_id[spnrAccount.selectedItemPosition],
                                            decId[spnrDesignation.selectedItemPosition],
                                            edtTxtNAme.text.toString(), COUNTRY_CODE, finalFile!!)
                                }
        }

        return mView
    }

    inner class ResponceProcessorAppNetwork2 : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorAppNetwork : ProcessResponcceInterphase<AddNetworkResponce> {
        override fun processResponce(responce: AddNetworkResponce) {
            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            activity!!.finish()
            startActivity(Intent(activity, NetworkActivity::class.java))
            progressDialog.dismiss()
        }

        override fun processResponceError(responce: Any?) {
            Toast.makeText(activity, "User Already Exist", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }

    }

    inner class ResponceProcessorGroups1 : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {
            var count = 0
            var pos = 0

            account_name.add("--Account--")
            account_id.add("-1")

            while (count < responce.size) {
                account_name.add(responce[count].accountName.toString())
                account_id.add(responce[count].id.toString())
                /*if (responce[count].id.toString() == GROUP_ID)
                    pos = count*/
                count++
            }
            spnrAccount.adapter = adapterAccount
//            spnrAccount.setSelection(pos)
            adapterAccount.notifyDataSetChanged()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorDesignations1 : ProcessResponcceInterphase<Array<DesignationResponce>> {
        override fun processResponce(responce: Array<DesignationResponce>) {
            var count = 0
            var pos = 0
            decName.add("--Designation--")
            decId.add("-1")
            while (count < responce.size) {
                decName.add(responce[count].name.toString())
                decId.add(responce[count].id.toString())
                /*if (responce[count].id.toString() == DESIGNATION_ID)
                    pos = count*/
                count++
            }
            spnrDesignation.adapter = adapterDesgination
//            spnrDesignation.setSelection(pos)
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

                finalFile = File(resultUri.getPath())
                imgDecodableString = finalFile.toString()

                cirImgVwImage.setImageURI(resultUri)

//                val bitmap = data!!.extras.get("data") as Bitmap
//                val selectedImage = getImageUri(activity!!.applicationContext, bitmap)

//                Request(activity, ResponceProcessor()).sendImage("title", "name", "image", File(imgDecodableString))

            }
        } catch (e: Exception) {
        }
    }

    /*fun readContacts() {
        Log.e("Contacts :", "asdfefr")
        val cr = activity!!.contentResolver
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        if (cur!!.count > 0) {
            while (cur!!.moveToNext()) {
                val id = cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                if (Integer.parseInt(cur!!.getString(cur!!.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    println("name : $name, ID : $id")
                    Log.e("Contacts :", "name : $name, ID : $id")

                    // get the phone number
                    val pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf<String>(id), null)
                    while (pCur!!.moveToNext()) {
                        val phone = pCur!!.getString(
                                pCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        println("phone$phone")
                        Log.e("Contacts :", "phone$phone")
                    }
                    pCur!!.close()


                    // get email and type

                    val emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            arrayOf<String>(id), null)
                    while (emailCur!!.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        val email = emailCur!!.getString(
                                emailCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        val emailType = emailCur!!.getString(
                                emailCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))

                        println("Email $email Email Type : $emailType")
                        Log.e("Contacts :", "Email $email Email Type : $emailType")
                    }
                    emailCur!!.close()

                    // Get note.......
                    val noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                    val noteWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    val noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null)
                    if (noteCur!!.moveToFirst()) {
                        val note = noteCur!!.getString(noteCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE))
                        println("Note $note")
                        Log.e("Contacts :", "Note $note")
                    }
                    noteCur!!.close()

                    //Get Postal Address....

                    val addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                    val addrWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                    val addrCur = cr.query(ContactsContract.Data.CONTENT_URI, null, null, null, null)
                    while (addrCur!!.moveToNext()) {
                        val poBox = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX))
                        val street = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET))
                        val city = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY))
                        val state = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION))
                        val postalCode = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE))
                        val country = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY))
                        val type = addrCur!!.getString(
                                addrCur!!.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE))

                        // Do something with these....

                    }
                    addrCur!!.close()

                    // Get Instant Messenger.........
                    val imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                    val imWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                    val imCur = cr.query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null)
                    if (imCur!!.moveToFirst()) {
                        val imName = imCur!!.getString(
                                imCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA))
                        val imType: String
                        imType = imCur!!.getString(
                                imCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE))
                    }
                    imCur!!.close()

                    // Get Organizations.........

                    val orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
                    val orgWhereParams = arrayOf(id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    val orgCur = cr.query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null)
                    if (orgCur!!.moveToFirst()) {
                        val orgName = orgCur!!.getString(orgCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA))
                        val title = orgCur!!.getString(orgCur!!.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
                    }
                    orgCur!!.close()
                }
            }
        }
    }*/

}
