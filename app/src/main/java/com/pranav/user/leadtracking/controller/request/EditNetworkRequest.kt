package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.EditNetworkResponce
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditNetworkRequest(con: Context, rhandler: ProcessResponcceInterphase<EditNetworkResponce>) :
        AbstractRequest<EditNetworkResponce>(con, rhandler) {

    fun EditNetwork(email: String, account_id: String, name: String,
                    country_code: String, number: String, designation_id: String,
                    id: String) {
        val call = leadTrackingInterphase.EditNetwork(token_,
                json_, email, account_id, name, country_code,
                "PUT", number, designation_id, id)
        call.enqueue(this)

    }

    fun image(id: String, file: File) {
        val image = RequestBody.create(MediaType.parse("multipart/form-data"), file.getAbsoluteFile())
        val imagePart = MultipartBody.Part.createFormData("image", file.getName(), image)

        val call = leadTrackingInterphase.EditNetwork(token_,
                json_, imagePart, id)
        call.enqueue(this)
    }

    fun EditNetworkImage(email: String, account_id: String, name: String,
                         country_code: String, number: String, designation_id: String,
                         id: String, file: File) {


        val image = RequestBody.create(MediaType.parse("multipart/form-data"), file.absoluteFile)
        val imagePart = MultipartBody.Part.createFormData("image", file.name, image)

        val rb_email: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), email)
        val rb_account_id: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), account_id)
        val rb_name: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        val rb_country_code: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), country_code)
        val rb_PUT: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "PUT")
        val rb_number: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), number)
        val rb_designation_id: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), designation_id)

        val params = HashMap<String, RequestBody>()
        params["email"] = rb_email
        params["account_id"] = rb_account_id
        params["name"] = rb_name
        params["country_code"] = rb_country_code
        params["_method"] = rb_PUT
        params["number"] = rb_number
        params["designation_id"] = rb_designation_id

        val call = leadTrackingInterphase.EditNetworkImage(token_,
                json_,
                rb_email,
                rb_account_id,
                rb_name,
                rb_country_code,
                rb_PUT,
                rb_number,
                rb_designation_id,
                id, imagePart)
        call.enqueue(this)
    }

}