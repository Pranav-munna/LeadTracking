package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.ImageResponce2
import com.pranav.user.leadtracking.controller.responce2.UserResponce2
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "created_by",
        "content",
        "type",
        "created_at",
        "updated_at",
        "comments_count",
        "likes_count",
        "self_liked",
        "user",
        "image")
class AllFeedsResponce {

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

    @JsonProperty("comments_count")
    @get:JsonProperty("comments_count")
    var commentsCount: Int? = null

    @JsonProperty("likes_count")
    @get:JsonProperty("likes_count")
    var likesCount: Int? = null

    @JsonProperty("self_liked")
    @get:JsonProperty("self_liked")
    @set:JsonProperty("self_liked")
    var selfLiked: Int? = null

    @JsonProperty("user")
    @get:JsonProperty("user")
    var user: UserResponce2? = null

    @JsonProperty("image")
    @get:JsonProperty("image")
    var image: ImageResponce2? = null
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
