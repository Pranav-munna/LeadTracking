package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.GroupResponce

class GroupRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<GroupResponce>>) :
        AbstractRequest<Array<GroupResponce>>(con, rhandler) {

    fun groups() {
        val call = leadTrackingInterphase.groups(token_, json_)
        call.enqueue(this)
    }
}