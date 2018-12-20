package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.NoteUpdateResponce

class NoteUpdateRequest(con: Context, rhandler: ProcessResponcceInterphase<NoteUpdateResponce>) :
        AbstractRequest<NoteUpdateResponce>(con, rhandler) {

    fun updateNotes(body:String,note:String,title:String) {
        val call = leadTrackingInterphase.updateNotes(token_, json_,body,note,title)
        call.enqueue(this)
    }
}