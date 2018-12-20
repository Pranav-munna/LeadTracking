package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.controller.responce.AnswersResponce

class DeleteContactRequest(con: Context, rhandler: ProcessResponcceInterphase<AnswersResponce>) :
        AbstractRequest<AnswersResponce>(con, rhandler) {

    fun DeleteContact(lead_id: String) {
        val call = leadTrackingInterphase.DeleteContact(token_, json_,"DELETE", lead_id)
        call.enqueue(this)
    }

}