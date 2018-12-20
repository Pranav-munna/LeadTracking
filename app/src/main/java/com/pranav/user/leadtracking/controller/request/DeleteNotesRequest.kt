package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNotesResponce
import com.pranav.user.leadtracking.controller.responce.DeleteNotesResponce

class DeleteNotesRequest(con: Context, rhandler: ProcessResponcceInterphase<DeleteNotesResponce>) :
        AbstractRequest<DeleteNotesResponce>(con, rhandler) {

    fun DeleteNotes(note_id: String) {
        val call = leadTrackingInterphase.DeleteNotes(token_, json_, note_id)
        call.enqueue(this)
    }



}