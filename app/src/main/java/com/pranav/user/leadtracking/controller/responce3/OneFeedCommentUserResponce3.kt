package com.pranav.user.leadtracking.controller.responce3

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "first_name",
        "last_name",
        "about",
        "email",
        "designation_id",
        "profile_image",
        "role_id",
        "is_enabled",
        "location",
        "contact_number",
        "office_number",
        "oid",
        "systemid",
        "access_in_dynamics",
        "created_at",
        "updated_at")
class OneFeedCommentUserResponce3 {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("first_name")
    @get:JsonProperty("first_name")
    var firstName: String? = null

    @JsonProperty("last_name")
    @get:JsonProperty("last_name")
    var lastName: String? = null

    @JsonProperty("about")
    @get:JsonProperty("about")
    var about: Any? = null

    @JsonProperty("email")
    @get:JsonProperty("email")
    var email: String? = null

    @JsonProperty("designation_id")
    @get:JsonProperty("designation_id")
    var designationId: Int? = null

    @JsonProperty("profile_image")
    @get:JsonProperty("profile_image")
    var profileImage: String? = null

    @JsonProperty("role_id")
    @get:JsonProperty("role_id")
    var roleId: Int? = null

    @JsonProperty("is_enabled")
    @get:JsonProperty("is_enabled")
    var isEnabled: Int? = null

    @JsonProperty("location")
    @get:JsonProperty("location")
    var location: Any? = null

    @JsonProperty("contact_number")
    @get:JsonProperty("contact_number")
    var contactNumber: Any? = null

    @JsonProperty("office_number")
    @get:JsonProperty("office_number")
    var officeNumber: Any? = null

    @JsonProperty("oid")
    @get:JsonProperty("oid")
    var oid: String? = null

    @JsonProperty("systemid")
    @get:JsonProperty("systemid")
    var systemid: String? = null

    @JsonProperty("access_in_dynamics")
    @get:JsonProperty("access_in_dynamics")
    var accessInDynamics: Int? = null

    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
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