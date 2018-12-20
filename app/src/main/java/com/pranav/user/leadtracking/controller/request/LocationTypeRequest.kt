package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.LocationResponce

class LocationTypeRequest(con: Context, rhandler: ProcessResponcceInterphase<Array<LocationResponce>>) :
        AbstractRequest<Array<LocationResponce>>(con, rhandler) {

    fun locations() {
        val call = leadTrackingInterphase.locations(token_, json_)
        call.enqueue(this)
    }
    fun type() {
        val call = leadTrackingInterphase.type(token_, json_)
        call.enqueue(this)
    }
}