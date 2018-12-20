package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.Contact
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("total_results", "contacts")
class ContactSearchResponce {

    @JsonProperty("total_results")
    @get:JsonProperty("total_results")
    @set:JsonProperty("total_results")
    var totalResults: Int? = null
    @JsonProperty("contacts")
    @get:JsonProperty("contacts")
    @set:JsonProperty("contacts")
    var contacts: List<Contact>? = null
    @JsonIgnore
    private val additionalProperties = HashMap<String, Any?>()

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any?> {
        return this.additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any?) {
        this.additionalProperties[name] = value
    }
}