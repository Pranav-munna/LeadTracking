package com.pranav.user.leadtracking.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.FeedsUploadRequest
import com.pranav.user.leadtracking.controller.responce.FeedsUploadResponce
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File

class FeedsUploadFragment : Fragment() {

    lateinit var imgVwCamera: ImageView
    lateinit var imgVwCameraClick: ImageView
    lateinit var btnPost: Button
    lateinit var edTxtcontent: EditText
    var imgDecodableString = ""
    var finalFile: File? = null
    lateinit var progressDialog: Dialog
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_feeds_upload, container, false)
        imgDecodableString = ""
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        activity!!.window.setLayout(width, (height * .8).toInt())
        activity!!.window.setGravity(Gravity.BOTTOM)

        imgVwCamera = mView.findViewById(R.id.imgVwCamera)
        imgVwCameraClick = mView.findViewById(R.id.imgVwCameraClick)
        btnPost = mView.findViewById(R.id.btnPost)
        edTxtcontent = mView.findViewById(R.id.edTxtcontent)

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)

        imgVwCameraClick.setOnClickListener {
            if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity!!, permissions, 101)
                    Toast.makeText(activity, "permission denied accessing camera", Toast.LENGTH_SHORT).show()
                }
                try {
                    CropImage.activity()
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
        btnPost.setOnClickListener {
            if (imgDecodableString.trim().isEmpty() && edTxtcontent.text.isEmpty()) {
                Toast.makeText(activity!!, "Nothing to post", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                btnPost.isClickable = false
                btnPost.isEnabled = false
                if (imgDecodableString.trim().isNotEmpty()) {
                    FeedsUploadRequest(activity!!, ResponceProcessorFeedsUpload())
                            .uploadFeed(edTxtcontent.text.trim().toString(), finalFile!!)

                } else
//                    Toast.makeText(activity, edTxtcontent.text.trim().toString(), Toast.LENGTH_SHORT).show()
                FeedsUploadRequest(activity!!, ResponceProcessorFeedsUpload())
                        .uploadFeed(edTxtcontent.text.trim().toString(), null)
            }
        }
        return mView
    }

    inner class ResponceProcessorFeedsUpload : ProcessResponcceInterphase<FeedsUploadResponce> {
        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()

        }

        override fun processResponce(responce: FeedsUploadResponce) {
            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            activity!!.finish()
            progressDialog.dismiss()
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

                imgVwCamera.setImageURI(resultUri)

//                val bitmap = data!!.extras.get("data") as Bitmap

//                val selectedImage = getImageUri(activity!!.applicationContext, bitmap)


//                Request(activity, ResponceProcessor()).sendImage("title", "name", "image", File(imgDecodableString))


            }
        } catch (e: Exception) {


        }


    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor = activity!!.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }
}
