package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.LikeResponce

class LikeRequest(context: Context, rHandler: ProcessResponcceInterphase<LikeResponce>) :
        AbstractRequest<LikeResponce>(context, rHandler) {

    fun like(id: String, like: String) {
        val call = leadTrackingInterphase.like(token_, json_, id, like)
        call.enqueue(this)
    }

}