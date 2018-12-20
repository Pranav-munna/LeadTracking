package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "name",
        "designation_id",
        "country_code",
        "number",
        "email",
        "image",
        "last_contacted_at",
        "created_by",
        "created_at",
        "updated_at")
class DealDetailResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("designation_id")
    @get:JsonProperty("designation_id")
    @set:JsonProperty("designation_id")
    var designationId: Int? = null
    @JsonProperty("country_code")
    @get:JsonProperty("country_code")
    @set:JsonProperty("country_code")
    var countryCode: String? = null
    @JsonProperty("number")
    @get:JsonProperty("number")
    @set:JsonProperty("number")
    var number: String? = null
    @JsonProperty("email")
    @get:JsonProperty("email")
    @set:JsonProperty("email")
    var email: String? = null
    @JsonProperty("image")
    @get:JsonProperty("image")
    @set:JsonProperty("image")
    var image: Any? = null
    @JsonProperty("last_contacted_at")
    @get:JsonProperty("last_contacted_at")
    @set:JsonProperty("last_contacted_at")
    var lastContactedAt: Any? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("created_by")
    @get:JsonProperty("created_by")
    @set:JsonProperty("created_by")
    var created_by: Int? = null
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