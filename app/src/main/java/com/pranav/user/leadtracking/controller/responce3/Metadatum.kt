package com.pranav.user.leadtracking.controller.responce3

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id"/*,
        "question_id", "answer_id", "lead_id", "meeting_id", "user_id",
        "option_id", "value", "created_at", "updated_at"*/)
class Metadatum {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null

   /* @JsonProperty("question_id")
    @get:JsonProperty("question_id")
    @set:JsonProperty("question_id")
    var questionId: Int? = null
    @JsonProperty("answer_id")
    @get:JsonProperty("answer_id")
    @set:JsonProperty("answer_id")
    var answerId: Int? = null
    @JsonProperty("lead_id")
    @get:JsonProperty("lead_id")
    @set:JsonProperty("lead_id")
    var leadId: Int? = null
    @JsonProperty("meeting_id")
    @get:JsonProperty("meeting_id")
    @set:JsonProperty("meeting_id")
    var meetingId: Int? = null
    @JsonProperty("user_id")
    @get:JsonProperty("user_id")
    @set:JsonProperty("user_id")
    var userId: Int? = null
    @JsonProperty("option_id")
    @get:JsonProperty("option_id")
    @set:JsonProperty("option_id")
    var optionId: Int? = null
    @JsonProperty("value")
    @get:JsonProperty("value")
    @set:JsonProperty("value")
    var value: String? = null
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