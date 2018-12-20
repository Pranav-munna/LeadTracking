package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.ContactSearchResponce

class ContactSearchRequest(context: Context, rhandler: ProcessResponcceInterphase<ContactSearchResponce>) :
        AbstractRequest<ContactSearchResponce>(context, rhandler) {

    fun contacts(page: Int) {
        val call = leadTrackingInterphase.contacts(token_, json_, page)
        call.enqueue(this)
    }

}