package com.pranav.user.leadtracking.controller.responce

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.pranav.user.leadtracking.controller.responce3.Contact

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "subject",
        "agenda",
        "scheduled_on",
        "calendar_id",
        "contacts")
class ScheduledMeetingResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("subject")
    @get:JsonProperty("subject")
    @set:JsonProperty("subject")
    var subject: String? = null
    @JsonProperty("agenda")
    @get:JsonProperty("agenda")
    @set:JsonProperty("agenda")
    var agenda: String? = null
    @JsonProperty("scheduled_on")
    @get:JsonProperty("scheduled_on")
    @set:JsonProperty("scheduled_on")
    var scheduledOn: String? = null
    @JsonProperty("calendar_id")
    @get:JsonProperty("calendar_id")
    @set:JsonProperty("calendar_id")
    var calendarId: String? = null
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