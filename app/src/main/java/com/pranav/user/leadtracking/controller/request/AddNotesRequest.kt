package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNotesResponce
import com.pranav.user.leadtracking.controller.responce.LoginResponce

class AddNotesRequest(con: Context, rhandler: ProcessResponcceInterphase<AddNotesResponce>) :
        AbstractRequest<AddNotesResponce>(con, rhandler) {

    fun AddNotes(contact_id: String, title: String, body: String) {
        val call = leadTrackingInterphase.AddNotes(token_, json_, contact_id, title, body)
        call.enqueue(this)
    }

}