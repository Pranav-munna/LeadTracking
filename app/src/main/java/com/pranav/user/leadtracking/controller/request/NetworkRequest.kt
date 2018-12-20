package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.NetworkResponce

class NetworkRequest(context: Context, rHandler: ProcessResponcceInterphase<NetworkResponce>) :
        AbstractRequest<NetworkResponce>(context, rHandler) {

    fun networkList(page: Int) {
        val call = leadTrackingInterphase.networklist(token_, json_, page)
        call.enqueue(this)
    }

    fun networkList(tag: String, page: Int) {
        val call = leadTrackingInterphase.networklist(token_, json_,tag, page)
        call.enqueue(this)
    }

}