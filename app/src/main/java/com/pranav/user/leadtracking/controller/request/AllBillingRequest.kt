package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllBillingsResponce

class AllBillingRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<AllBillingsResponce>>) :
        AbstractRequest<Array<AllBillingsResponce>>(con, rhandler) {

    fun AllBilling() {
        val call = leadTrackingInterphase.AllBilling(token_, json_)
        call.enqueue(this)
    }
}