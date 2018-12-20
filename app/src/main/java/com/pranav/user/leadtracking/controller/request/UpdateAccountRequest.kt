package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.DeleteNotesResponce

class UpdateAccountRequest(con: Context, rhandler: ProcessResponcceInterphase<DeleteNotesResponce>) :
        AbstractRequest<DeleteNotesResponce>(con, rhandler) {

    fun UpdateAccount(accounts: String) {
        val call = leadTrackingInterphase.UpdateAccount(token_, json_, accounts)
        call.enqueue(this)
    }



}