package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce3.UserComment
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "created_by", "post_id", "content", "created_at", "updated_at", "user")
class Comment {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("created_by")
    @get:JsonProperty("created_by")
    @set:JsonProperty("created_by")
    var createdBy: Int? = null
    @JsonProperty("post_id")
    @get:JsonProperty("post_id")
    @set:JsonProperty("post_id")
    var postId: Int? = null
    @JsonProperty("content")
    @get:JsonProperty("content")
    @set:JsonProperty("content")
    var content: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("user")
    @get:JsonProperty("user")
    @set:JsonProperty("user")
    var user: UserComment? = null
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