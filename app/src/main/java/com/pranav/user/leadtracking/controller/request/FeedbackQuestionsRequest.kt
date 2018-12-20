package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce

class FeedbackQuestionsRequest(con: Context, rhandler: ProcessResponcceInterphase<AddNetworkResponce>) :
        AbstractRequest<AddNetworkResponce>(con, rhandler) {

    fun MeetingQuestion(answers: String, meeting_id: String) {
        val call = leadTrackingInterphase.MeetingQuestion(token_, json_, answers, meeting_id)
        call.enqueue(this)
    }

}