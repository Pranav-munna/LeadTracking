package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllBillingsResponce

class AllDeliverablesRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<AllBillingsResponce>>) :
        AbstractRequest<Array<AllBillingsResponce>>(con, rhandler) {

    fun AllDeliverables() {
        val call = leadTrackingInterphase.AllDeliverables(token_, json_)
        call.enqueue(this)
    }
}