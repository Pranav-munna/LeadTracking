package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "first_name",
        "last_name",
        "email",
        "profile_image")
class UserResponce2 {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("first_name")
    @get:JsonProperty("first_name")
    var firstName: String? = null

    @JsonProperty("last_name")
    @get:JsonProperty("last_name")
    var lastName: String? = null

    @JsonProperty("email")
    @get:JsonProperty("email")
    var email: String? = null

    @JsonProperty("profile_image")
    @get:JsonProperty("profile_image")
    var profileImage: String? = null
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
