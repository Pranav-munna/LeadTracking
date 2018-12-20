package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce

class DeleteMeetingRequest(con: Context, rhandler: ProcessResponcceInterphase<AddNetworkResponce>) :
        AbstractRequest<AddNetworkResponce>(con, rhandler) {

    fun DeleteMeeting(id: String) {
        val call = leadTrackingInterphase.DeleteMeeting(token_, json_, "DELETE", id)
        call.enqueue(this)
    }

}