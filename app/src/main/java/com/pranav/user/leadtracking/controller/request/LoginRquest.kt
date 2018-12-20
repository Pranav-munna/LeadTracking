package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.LoginResponce

class LoginRquest(con: Context, rhandler: ProcessResponcceInterphase<LoginResponce>) :
        AbstractRequest<LoginResponce>(con, rhandler) {

    fun login(accessTkn: String, refreshTkn: String, expireOn: String) {
        val call = leadTrackingInterphase.login(json_, accessTkn, refreshTkn, expireOn)
        call.enqueue(this)
    }

}