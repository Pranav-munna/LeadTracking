package com.pranav.user.leadtracking.controller.responce3

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("question_id"/*,
        "level_id",
        "question_order",
        "created_at",
        "updated_at"*/)
class Pivot {

    @JsonProperty("question_id")
    @get:JsonProperty("question_id")
    @set:JsonProperty("question_id")
    var questionId: Int? = null
    /*@JsonProperty("level_id")
    @get:JsonProperty("level_id")
    @set:JsonProperty("level_id")
    var levelId: Int? = null
    @JsonProperty("question_order")
    @get:JsonProperty("question_order")
    @set:JsonProperty("question_order")
    var questionOrder: Int? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null*/
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