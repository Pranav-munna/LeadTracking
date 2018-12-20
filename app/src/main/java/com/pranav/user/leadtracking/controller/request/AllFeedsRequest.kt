package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllFeedsResponce

class AllFeedsRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<AllFeedsResponce>>) :
        AbstractRequest<Array<AllFeedsResponce>>(con, rhandler) {

    fun getCategories(page: Int) {
        val call = leadTrackingInterphase.getAllfeeds(token_, json_, page)
        call.enqueue(this)
    }
}