package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.controller.responce.AnswersResponce

class AnswersRequest(con: Context, rhandler: ProcessResponcceInterphase<AnswersResponce>) :
        AbstractRequest<AnswersResponce>(con, rhandler) {

    fun Answers(answers: String, lead_id: String) {
        val call = leadTrackingInterphase.Answers(token_, json_, answers, lead_id)
        call.enqueue(this)
    }

    fun AnswersUpdate(answers: String, lead_id: String) {
        val call = leadTrackingInterphase.Answers(token_, json_,"PUT", answers, lead_id)
        call.enqueue(this)
    }


}