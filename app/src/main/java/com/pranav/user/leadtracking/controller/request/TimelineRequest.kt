package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.TimelineResponce

class TimelineRequest(context: Context, rhandler: ProcessResponcceInterphase<Array<TimelineResponce>>) :
        AbstractRequest<Array<TimelineResponce>>(context, rhandler) {

    fun timeline(id: String) {
        val call = leadTrackingInterphase.timeLine(token_, json_, id)
        call.enqueue(this)
    }

}