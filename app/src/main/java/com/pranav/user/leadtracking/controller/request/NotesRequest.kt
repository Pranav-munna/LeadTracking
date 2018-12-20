package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.NotesResponce

class NotesRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<NotesResponce>>) :
        AbstractRequest<Array<NotesResponce>>(con, rhandler) {

    fun Notes(id: String, page: Int) {
        val call = leadTrackingInterphase.Notes(token_, json_, id, page)
        call.enqueue(this)
    }
}