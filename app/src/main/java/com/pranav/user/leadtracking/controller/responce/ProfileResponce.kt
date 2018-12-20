package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.Designation2
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "first_name", "last_name", "about", "email", "designation_id", "profile_image", "role_id", "is_enabled", "location", "contact_number", "office_number", "oid", "systemid", "access_in_dynamics", "created_at", "updated_at", "designation")
class ProfileResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("first_name")
    @get:JsonProperty("first_name")
    @set:JsonProperty("first_name")
    var firstName: String? = null
    @JsonProperty("last_name")
    @get:JsonProperty("last_name")
    @set:JsonProperty("last_name")
    var lastName: String? = null
    @JsonProperty("about")
    @get:JsonProperty("about")
    @set:JsonProperty("about")
    var about: String? = null
    @JsonProperty("email")
    @get:JsonProperty("email")
    @set:JsonProperty("email")
    var email: String? = null
    @JsonProperty("designation_id")
    @get:JsonProperty("designation_id")
    @set:JsonProperty("designation_id")
    var designationId: Int? = null
    @JsonProperty("profile_image")
    @get:JsonProperty("profile_image")
    @set:JsonProperty("profile_image")
    var profileImage: String? = null
    @JsonProperty("role_id")
    @get:JsonProperty("role_id")
    @set:JsonProperty("role_id")
    var roleId: Int? = null
    @JsonProperty("is_enabled")
    @get:JsonProperty("is_enabled")
    @set:JsonProperty("is_enabled")
    var isEnabled: Int? = null
    @JsonProperty("location")
    @get:JsonProperty("location")
    @set:JsonProperty("location")
    var location: String? = null
    @JsonProperty("contact_number")
    @get:JsonProperty("contact_number")
    @set:JsonProperty("contact_number")
    var contactNumber: String? = null
    @JsonProperty("office_number")
    @get:JsonProperty("office_number")
    @set:JsonProperty("office_number")
    var officeNumber: String? = null
    @JsonProperty("oid")
    @get:JsonProperty("oid")
    @set:JsonProperty("oid")
    var oid: String? = null
    @JsonProperty("systemid")
    @get:JsonProperty("systemid")
    @set:JsonProperty("systemid")
    var systemid: String? = null
    @JsonProperty("access_in_dynamics")
    @get:JsonProperty("access_in_dynamics")
    @set:JsonProperty("access_in_dynamics")
    var accessInDynamics: Int? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("designation")
    @get:JsonProperty("designation")
    @set:JsonProperty("designation")
    var designation: Designation2? = null
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