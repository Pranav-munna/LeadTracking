package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.DealsResponce

class DealsRequest(context: Context, rHandler: ProcessResponcceInterphase<DealsResponce>) :
        AbstractRequest<DealsResponce>(context, rHandler) {

    fun dealList(page: Int) {
        val call = leadTrackingInterphase.dealList(token_, json_, page)
        call.enqueue(this)
    }

    fun dealListSearch(data: String, page: Int) {
        val call = leadTrackingInterphase.dealListSearch(token_, json_, data, page)
        call.enqueue(this)
    }
}