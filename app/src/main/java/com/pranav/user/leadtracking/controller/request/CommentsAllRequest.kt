package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.CommentsAllResponce

class CommentsAllRequest(con: Context, rhandler: ProcessResponcceInterphase<CommentsAllResponce>) :
        AbstractRequest<CommentsAllResponce>(con, rhandler) {

    fun AllComment(id:String) {
        val call = leadTrackingInterphase.AllComment(token_, json_,id)
        call.enqueue(this)
    }
}