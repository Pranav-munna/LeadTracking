package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.QuestionAllLevelsResponce

class QuestionAllLevelsRequest(context: Context, rhandler: ProcessResponcceInterphase<Array<QuestionAllLevelsResponce>>) :
        AbstractRequest<Array<QuestionAllLevelsResponce>>(context, rhandler) {
    fun AllLevel() {
        val call = leadTrackingInterphase.AllLevel(token_, json_)
        call.enqueue(this)
    }

}