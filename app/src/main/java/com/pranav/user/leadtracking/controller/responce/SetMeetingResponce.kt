package com.pranav.user.leadtracking.controller.responce


import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("message")
class SetMeetingResponce {

    @JsonProperty("message")
    @get:JsonProperty("message")
    @set:JsonProperty("message")
    var message: String? = null

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