package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.EditNetworkResponce
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileUpdateRequest(con: Context, rhandler: ProcessResponcceInterphase<EditNetworkResponce>) :
        AbstractRequest<EditNetworkResponce>(con, rhandler) {

    fun update(about: String, contact_number: String, designation_id: String,
               first_name: String, last_name: String, location: String, office_number: String) {

        val call = leadTrackingInterphase.updateProfile(token_, json_, about,
                contact_number, designation_id, first_name, last_name, location, office_number)
        call.enqueue(this)

    }

    fun updateImage(file: File) {
        val image = RequestBody.create(MediaType.parse("multipart/form-data"), file.getAbsoluteFile())
        val imagePart = MultipartBody.Part.createFormData("image", file.getName(), image)

        val call = leadTrackingInterphase.updateProfile(token_, json_, imagePart)
        call.enqueue(this)
    }


}