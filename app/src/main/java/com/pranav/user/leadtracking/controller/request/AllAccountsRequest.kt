package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AnswersResponce

class AllAccountsRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<AnswersResponce>>) :
        AbstractRequest<Array<AnswersResponce>>(con, rhandler) {


    fun AllAccounts(text: String, page: Int) {
        val call = leadTrackingInterphase.allAccounts(token_, json_, text, page)
        call.enqueue(this)
    }


}