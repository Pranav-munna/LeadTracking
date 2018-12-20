package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddNetworkResponce

class UpdateLevelRequest(con: Context, rhandler: ProcessResponcceInterphase<AddNetworkResponce>) :
        AbstractRequest<AddNetworkResponce>(con, rhandler) {

    fun UpdateLevel(lead_id: String, level_id: String) {
        val call = leadTrackingInterphase.UpdateLevel(token_, json_,
                lead_id, level_id)
        call.enqueue(this)
    }
}