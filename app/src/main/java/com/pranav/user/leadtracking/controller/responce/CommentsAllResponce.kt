package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.Comment
import com.pranav.user.leadtracking.controller.responce2.Like
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "created_by", "content", "type", "created_at", "updated_at", "comments", "likes")
class CommentsAllResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("created_by")
    @get:JsonProperty("created_by")
    @set:JsonProperty("created_by")
    var createdBy: Int? = null
    @JsonProperty("content")
    @get:JsonProperty("content")
    @set:JsonProperty("content")
    var content: String? = null
    @JsonProperty("type")
    @get:JsonProperty("type")
    @set:JsonProperty("type")
    var type: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null
    @JsonProperty("comments")
    @get:JsonProperty("comments")
    @set:JsonProperty("comments")
    var comments: List<Comment>? = null
    @JsonProperty("likes")
    @get:JsonProperty("likes")
    @set:JsonProperty("likes")
    var likes: List<Like>? = null
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