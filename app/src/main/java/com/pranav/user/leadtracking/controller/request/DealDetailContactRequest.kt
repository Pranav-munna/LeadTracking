package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllFeedsResponce
import com.pranav.user.leadtracking.controller.responce.DealDetailContactResponce
import com.pranav.user.leadtracking.controller.responce.DealDetailResponce

class DealDetailContactRequest(con: Context, rhandler: ProcessResponcceInterphase<DealDetailContactResponce>) :
        AbstractRequest<DealDetailContactResponce>(con, rhandler) {

    fun contact(pageNo: Int) {
        val call = leadTrackingInterphase.contact(token_, json_, pageNo)
        call.enqueue(this)
    }

    fun contactSearch(search: String, pageNo: Int) {
        val call = leadTrackingInterphase.contactSearch(token_, json_, search, pageNo)
        call.enqueue(this)
    }
}