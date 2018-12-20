package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AllQuestionsResponce

class AllQuestionsRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<AllQuestionsResponce>>) :
        AbstractRequest<Array<AllQuestionsResponce>>(con, rhandler) {

    fun AllQuestions(id:String) {
        val call = leadTrackingInterphase.AllQuestions(token_, json_,id)
        call.enqueue(this)
    }
}