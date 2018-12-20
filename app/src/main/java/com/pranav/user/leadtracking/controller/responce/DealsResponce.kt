package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.pranav.user.leadtracking.controller.responce2.DatumDealResponce

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("total_leads", "data")
class DealsResponce {

    @JsonProperty("total_leads")
    @get:JsonProperty("total_leads")
    @set:JsonProperty("total_leads")
    var totalLeads: Int? = null
    @JsonProperty("data")
    @get:JsonProperty("data")
    @set:JsonProperty("data")
    var data: List<DatumDealResponce>? = null

}
