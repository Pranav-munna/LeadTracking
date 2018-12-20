package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.QuestionsOneLevelResponce

class QuestionsOneLevelRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<QuestionsOneLevelResponce>>) :
        AbstractRequest<Array<QuestionsOneLevelResponce>>(con, rhandler) {

    fun Question(id: String, questionNo: String) {
        val call = leadTrackingInterphase.Question(token_, json_, id, questionNo)
        call.enqueue(this)
    }

    fun MeetingQuestion() {
        val call = leadTrackingInterphase.MeetingQuestion(token_, json_)
        call.enqueue(this)
    }
}