package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce

class AddContactsDealRequest(con: Context, rhandler: ProcessResponcceInterphase<AddNetworkResponce>) :
        AbstractRequest<AddNetworkResponce>(con, rhandler) {

    fun Addcontacts( contacts: String, lead_id: String) {
        val call = leadTrackingInterphase.Addcontacts(token_, json_,
                "PUT", contacts, lead_id)
        call.enqueue(this)
    }
}