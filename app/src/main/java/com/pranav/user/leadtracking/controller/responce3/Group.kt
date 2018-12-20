package com.pranav.user.leadtracking.controller.responce3

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "owned_by",
        "account_name",
        "account_id",
        "image",
        "vertical",
        "industry_vertical",
        "created_at",
        "updated_at")
class Group {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("owned_by")
    @get:JsonProperty("owned_by")
    var ownedBy: Int? = null

    @JsonProperty("account_name")
    @get:JsonProperty("account_name")
    var accountName: String? = null

    @JsonProperty("account_id")
    @get:JsonProperty("account_id")
    var accountId: String? = null

    @JsonProperty("image")
    @get:JsonProperty("image")
    var image: Any? = null

    @JsonProperty("vertical")
    @get:JsonProperty("vertical")
    var vertical: Any? = null

    @JsonProperty("industry_vertical")
    @get:JsonProperty("industry_vertical")
    var industryVertical: Any? = null

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