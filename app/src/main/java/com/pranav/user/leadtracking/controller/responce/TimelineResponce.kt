package com.pranav.user.leadtracking.controller.responce

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
@JsonInclude(JsonInclude.Include.NON_NULL)
/*@JsonPropertyOrder("id",
        "lead_id",
        "event_date",
        "event_name",
        "event_type",
        "event_meta",
        "created_at",
        "updated_at",
        "meeting")*/
class TimelineResponce {

    /*@JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null*/
    /*@JsonProperty("lead_id")
    @get:JsonProperty("lead_id")
    @set:JsonProperty("lead_id")
    var leadId: Int? = null
    @JsonProperty("event_date")
    @get:JsonProperty("event_date")
    @set:JsonProperty("event_date")
    var eventDate: String? = null
    @JsonProperty("event_name")
    @get:JsonProperty("event_name")
    @set:JsonProperty("event_name")
    var eventName: String? = null
    @JsonProperty("event_type")
    @get:JsonProperty("event_type")
    @set:JsonProperty("event_type")
    var eventType: String? = null
    @JsonProperty("event_meta")
    @get:JsonProperty("event_meta")
    @set:JsonProperty("event_meta")
    var eventMeta: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("meeting")
    @get:JsonProperty("meeting")
    @set:JsonProperty("meeting")
    var meeting: Any? = null*/
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