package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddDealResponce

class CallRequest(con: Context, rhandler: ProcessResponcceInterphase<AddDealResponce>) :
        AbstractRequest<AddDealResponce>(con, rhandler) {

    fun NetworkCall(contact_id: String) {
        val call = leadTrackingInterphase.NetworkCall(token_, json_,  contact_id)
        call.enqueue(this)
    }

    fun DealCall(lead_id: String) {
        val call = leadTrackingInterphase.DealCall(token_, json_,lead_id)
        call.enqueue(this)
    }

}