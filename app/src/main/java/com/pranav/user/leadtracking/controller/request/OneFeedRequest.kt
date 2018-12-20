package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.OneFeedResponce

class OneFeedRequest(con: Context, rhandler: ProcessResponcceInterphase<OneFeedResponce>) :
        AbstractRequest<OneFeedResponce>(con, rhandler) {

    fun feedLikeComments(id: String) {
        val call = leadTrackingInterphase.feedLikeComments(token_, json_, id)
        call.enqueue(this)
    }
}