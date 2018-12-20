package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.ScheduledMeetingResponce

class ScheduledMeetingRequest(context: Context, rHandler: ProcessResponcceInterphase<Array<ScheduledMeetingResponce>>) :
        AbstractRequest<Array<ScheduledMeetingResponce>>(context, rHandler) {

    fun myMeetings(id: String) {
        val call = leadTrackingInterphase.myMeeting(token_, json_, id)
        call.enqueue(this)
    }

    fun myMeetingNetwork(id: String) {
        val call = leadTrackingInterphase.myMeetingNetwork(token_, json_, id)
        call.enqueue(this)
    }

}