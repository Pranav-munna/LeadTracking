package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.DesignationResponce

class DesignationRequest(context: Context, rhandler: ProcessResponcceInterphase<Array<DesignationResponce>>) :
        AbstractRequest<Array<DesignationResponce>>(context, rhandler) {

    fun desgination() {
        val call = leadTrackingInterphase.desgination(token_, json_)
        call.enqueue(this)
    }

}