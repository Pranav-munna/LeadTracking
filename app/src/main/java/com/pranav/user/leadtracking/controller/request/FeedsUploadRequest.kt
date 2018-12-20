package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.FeedsUploadResponce
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FeedsUploadRequest(context: Context, rhandler: ProcessResponcceInterphase<FeedsUploadResponce>) :
        AbstractRequest<FeedsUploadResponce>(context, rhandler) {

    fun uploadFeed(body: String, file: File?) {
        val params = HashMap<String, RequestBody>()

        val rb_body: RequestBody
        rb_body = RequestBody.create(MediaType.parse("multipart/form-data"), body)
        params["content"] = rb_body

        if (file != null) {
            val image = RequestBody.create(MediaType.parse("multipart/form-data"), file!!.absoluteFile)
            val imagePart = MultipartBody.Part.createFormData("image", file!!.name, image)

            val call = leadTrackingInterphase.getstatus(token_, json_, rb_body, imagePart)
            call.enqueue(this)
        } else{
            val call = leadTrackingInterphase.getstatus(token_, json_, rb_body, null)
            call.enqueue(this)
        }
    }

}