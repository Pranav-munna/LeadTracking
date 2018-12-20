package com.pranav.user.leadtracking.controller.responce3


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
        "created_at",
        "updated_at")
class Contact {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("name")
    @get:JsonProperty("name")
    var name: String? = null

    @JsonProperty("designation_id")
    @get:JsonProperty("designation_id")
    var designationId: Int? = null

    @JsonProperty("country_code")
    @get:JsonProperty("country_code")
    var countryCode: String? = null

    @JsonProperty("number")
    @get:JsonProperty("number")
    var number: String? = null

    @JsonProperty("email")
    @get:JsonProperty("email")
    var email: String? = null

    @JsonProperty("image")
    @get:JsonProperty("image")
    var image: Any? = null

    @JsonProperty("last_contacted_at")
    @get:JsonProperty("last_contacted_at")
    var lastContactedAt: Any? = null

    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    var createdAt: String? = null

    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
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