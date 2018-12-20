package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllFeedsResponce
import com.pranav.user.leadtracking.controller.responce.DealDetailResponce

class DealDetailRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<DealDetailResponce>>) :
        AbstractRequest<Array<DealDetailResponce>>(con, rhandler) {

    fun getCategories(id: String) {
        val call = leadTrackingInterphase.DealDetails(token_, json_, id)
        call.enqueue(this)
    }
}