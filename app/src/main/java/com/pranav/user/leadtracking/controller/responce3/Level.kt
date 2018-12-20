package com.pranav.user.leadtracking.controller.responce3

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "title", "score", "rank", "value", "metadata_id", "ust_stage", "ust_stagenumber", "stepname", "closeprobability", "processstageid", "is_exceptional", "success_message", "error_message")
class Level {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("score")
    @get:JsonProperty("score")
    @set:JsonProperty("score")
    var score: Int? = null
    @JsonProperty("rank")
    @get:JsonProperty("rank")
    @set:JsonProperty("rank")
    var rank: Int? = null
    @JsonProperty("value")
    @get:JsonProperty("value")
    @set:JsonProperty("value")
    var value: String? = null
    @JsonProperty("metadata_id")
    @get:JsonProperty("metadata_id")
    @set:JsonProperty("metadata_id")
    var metadataId: String? = null
    @JsonProperty("ust_stage")
    @get:JsonProperty("ust_stage")
    @set:JsonProperty("ust_stage")
    var ustStage: String? = null
    @JsonProperty("ust_stagenumber")
    @get:JsonProperty("ust_stagenumber")
    @set:JsonProperty("ust_stagenumber")
    var ustStagenumber: String? = null
    @JsonProperty("stepname")
    @get:JsonProperty("stepname")
    @set:JsonProperty("stepname")
    var stepname: String? = null
    @JsonProperty("closeprobability")
    @get:JsonProperty("closeprobability")
    @set:JsonProperty("closeprobability")
    var closeprobability: String? = null
    @JsonProperty("processstageid")
    @get:JsonProperty("processstageid")
    @set:JsonProperty("processstageid")
    var processstageid: String? = null
    @JsonProperty("is_exceptional")
    @get:JsonProperty("is_exceptional")
    @set:JsonProperty("is_exceptional")
    var isExceptional: Int? = null
    @JsonProperty("success_message")
    @get:JsonProperty("success_message")
    @set:JsonProperty("success_message")
    var successMessage: String? = null
    @JsonProperty("error_message")
    @get:JsonProperty("error_message")
    @set:JsonProperty("error_message")
    var errorMessage: Any? = null
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