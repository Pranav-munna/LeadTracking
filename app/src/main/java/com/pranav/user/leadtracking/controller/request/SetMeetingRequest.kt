package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.SetMeetingResponce

class SetMeetingRequest(con: Context, rhandler: ProcessResponcceInterphase<SetMeetingResponce>) :
        AbstractRequest<SetMeetingResponce>(con, rhandler) {

    fun setmeeting(agenda: String, contacts: String, location: String,
                   scheduled: String, subject: String, type: String, calanderid: String,
                   lead_id: String) {
        val call = leadTrackingInterphase.setmeeting(token_, json_, agenda,
                contacts, location, scheduled, subject, type, calanderid, lead_id)
        call.enqueue(this)
    }

    fun setmeetingNetwork(agenda: String, contacts: String, location: String,
                          scheduled: String, subject: String, type: String, calanderid: String) {
        val call = leadTrackingInterphase.setmeetingNetwork(token_, json_, agenda,
                contacts, location, scheduled, subject, type, calanderid)
        call.enqueue(this)
    }
}