package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce3.SubQuestion
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        /*"question_id",
        "title",
        "weightage",
        "has_metadata",
        "has_metaquestion",*/
        "sub_question",
        "metadata_type"/*,
        "value",
        "metadata_id"*/)
class Option {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    /*@JsonProperty("question_id")
    @get:JsonProperty("question_id")
    @set:JsonProperty("question_id")
    var questionId: Int? = null
    @JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("weightage")
    @get:JsonProperty("weightage")
    @set:JsonProperty("weightage")
    var weightage: Int? = null
    @JsonProperty("has_metadata")
    @get:JsonProperty("has_metadata")
    @set:JsonProperty("has_metadata")
    var hasMetadata: Int? = null
    @JsonProperty("has_metaquestion")
    @get:JsonProperty("has_metaquestion")
    @set:JsonProperty("has_metaquestion")
    var hasMetaquestion: Int? = null*/
    @JsonProperty("sub_question")
    @get:JsonProperty("sub_question")
    @set:JsonProperty("sub_question")
    var subQuestion: List<SubQuestion>? = null
    @JsonProperty("metadata_type")
    @get:JsonProperty("metadata_type")
    @set:JsonProperty("metadata_type")
    var metadataType: Any? = null
    /*@JsonProperty("value")
    @get:JsonProperty("value")
    @set:JsonProperty("value")
    var value: String? = null
    @JsonProperty("metadata_id")
    @get:JsonProperty("metadata_id")
    @set:JsonProperty("metadata_id")
    var metadataId: String? = null*/
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