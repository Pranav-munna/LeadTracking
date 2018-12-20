package com.pranav.user.leadtracking.controller.request

import android.content.Context
import com.pranav.user.leadtracking.controller.AbstractRequest
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.responce.AddDealResponce

class AddDealRequest(con: Context, rhandler: ProcessResponcceInterphase<AddDealResponce>) :
        AbstractRequest<AddDealResponce>(con, rhandler) {

    fun AddDeal(billing_type: String, contact_id: String, deliverable_type: String, estimated_end_date: String, estimated_revenue: String, estimated_start_date: String, group_id: String, name: String) {
        val call = leadTrackingInterphase.AddDeal(token_, json_, billing_type, contact_id, deliverable_type, estimated_end_date, estimated_revenue, estimated_start_date, group_id, name)
        call.enqueue(this)
    }

    fun AddDealUpdate(billing_type: String, contact_id: String, deliverable_type: String, estimated_end_date: String, estimated_revenue: String, estimated_start_date: String, group_id: String, lead: String, name: String) {
        val call = leadTrackingInterphase.AddDealUpdate(token_, json_, billing_type, contact_id, deliverable_type, estimated_end_date, estimated_revenue, estimated_start_date, group_id, lead, name)
        call.enqueue(this)
    }


}