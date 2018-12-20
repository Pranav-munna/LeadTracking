package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.User
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("signed", "pipeline", "meetings", "user")
class ScoreboardResponce {

    @JsonProperty("signed")
    @get:JsonProperty("signed")
    @set:JsonProperty("signed")
    var signed: Int? = null
    @JsonProperty("pipeline")
    @get:JsonProperty("pipeline")
    @set:JsonProperty("pipeline")
    var pipeline: Double? = null
    @JsonProperty("meetings")
    @get:JsonProperty("meetings")
    @set:JsonProperty("meetings")
    var meetings: Int? = null
    @JsonProperty("user")
    @get:JsonProperty("user")
    @set:JsonProperty("user")
    var user: User? = null
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