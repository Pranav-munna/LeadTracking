package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "lead_id",
        "contact_id",
        "user_id",
        "title",
        "body",
        "created_at",
        "updated_at")
class NotesResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("lead_id")
    @get:JsonProperty("lead_id")
    @set:JsonProperty("lead_id")
    var leadId: Int? = null
    @JsonProperty("contact_id")
    @get:JsonProperty("contact_id")
    @set:JsonProperty("contact_id")
    var contactId: Int? = null
    @JsonProperty("user_id")
    @get:JsonProperty("user_id")
    @set:JsonProperty("user_id")
    var userId: Int? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("body")
    @get:JsonProperty("body")
    @set:JsonProperty("body")
    var body: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
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