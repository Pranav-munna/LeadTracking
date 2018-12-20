package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.OneFeedCommentResponce2
import com.pranav.user.leadtracking.controller.responce2.OneFeedLikeResponce2
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "created_by",
        "content",
        "type",
        "created_at",
        "updated_at",
        "comments",
        "likes")
class OneFeedResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("created_by")
    @get:JsonProperty("created_by")
    var createdBy: Int? = null

    @JsonProperty("content")
    @get:JsonProperty("content")
    var content: String? = null

    @JsonProperty("type")
    @get:JsonProperty("type")
    var type: String? = null

    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    var createdAt: String? = null

    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    var updatedAt: String? = null

    @JsonProperty("comments")
    @get:JsonProperty("comments")
    var comments: List<OneFeedCommentResponce2>? = null

    @JsonProperty("likes")
    @get:JsonProperty("likes")
    var likes: List<OneFeedLikeResponce2>? = null
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