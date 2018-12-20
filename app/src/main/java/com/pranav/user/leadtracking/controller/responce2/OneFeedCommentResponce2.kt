package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce3.OneFeedCommentUserResponce3
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "created_by",
        "post_id",
        "content",
        "created_at",
        "updated_at",
        "user")
class OneFeedCommentResponce2 {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("created_by")
    @get:JsonProperty("created_by")
    var createdBy: Int? = null

    @JsonProperty("post_id")
    @get:JsonProperty("post_id")
    var postId: Int? = null

    @JsonProperty("content")
    @get:JsonProperty("content")
    var content: String? = null

    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    var createdAt: String? = null

    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    var updatedAt: String? = null

    @JsonProperty("user")
    @get:JsonProperty("user")
    var user: OneFeedCommentUserResponce3? = null
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