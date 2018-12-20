package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.CommentResponce

class CommentRequest(con: Context, rhandler: ProcessResponcceInterphase<CommentResponce>) :
        AbstractRequest<CommentResponce>(con, rhandler) {

    fun Comment(post_id: String,content: String) {
        val call = leadTrackingInterphase.Comment(token_, json_, post_id,content)
        call.enqueue(this)
    }
}