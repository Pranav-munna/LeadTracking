package com.pranav.user.leadtracking.controller.responce

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.pranav.user.leadtracking.controller.responce2.Answer
import com.pranav.user.leadtracking.controller.responce2.Field_
import com.pranav.user.leadtracking.controller.responce2.Level
import com.pranav.user.leadtracking.controller.responce2.Option

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        /*"title", "opportunity_field", "image", "is_enabled",*/
        "answers", "options", "field", "levels")
class AllQuestionsResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null

    /*@JsonProperty("title")
    @get:JsonProperty("title")
    @set:JsonProperty("title")
    var title: String? = null
    @JsonProperty("opportunity_field")
    @get:JsonProperty("opportunity_field")
    @set:JsonProperty("opportunity_field")
    var opportunityField: Any? = null
    @JsonProperty("image")
    @get:JsonProperty("image")
    @set:JsonProperty("image")
    var image: Any? = null
    @JsonProperty("is_enabled")
    @get:JsonProperty("is_enabled")
    @set:JsonProperty("is_enabled")
    var isEnabled: Int? = null*/

    @JsonProperty("answers")
    @get:JsonProperty("answers")
    @set:JsonProperty("answers")
    var answers: List<Answer>? = null
    @JsonProperty("options")
    @get:JsonProperty("options")
    @set:JsonProperty("options")
    var options: List<Option>? = null
    @JsonProperty("field")
    @get:JsonProperty("field")
    @set:JsonProperty("field")
    var field: Field_? = null
    @JsonProperty("levels")
    @get:JsonProperty("levels")
    @set:JsonProperty("levels")
    var levels: List<Level>? = null
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