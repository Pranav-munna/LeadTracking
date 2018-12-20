package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.ScoreboardResponce

class ScoreboardRequest (con: Context, rhandler: ProcessResponcceInterphase<Array<ScoreboardResponce>>) :
        AbstractRequest<Array<ScoreboardResponce>>(con, rhandler) {

    fun ScoreboardList(pgno: Int) {
        val call = leadTrackingInterphase.ScoreboardList(token_, json_, pgno)
        call.enqueue(this)
    }
}