package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.ProfileResponce

class ProfileRequest(context: Context, rhandler: ProcessResponcceInterphase<ProfileResponce>) :
        AbstractRequest<ProfileResponce>(context, rhandler) {
    fun profile() {
        val call = leadTrackingInterphase.profile(token_, json_)
        call.enqueue(this)
    }

}