package com.pranav.user.leadtracking.controller.responce

import com.fasterxml.jackson.annotation.*
import com.pranav.user.leadtracking.controller.responce2.Field_
import com.pranav.user.leadtracking.controller.responce2.Level
import com.pranav.user.leadtracking.controller.responce2.Option
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "options",
        "field",
        "levels")

/*
"title",
"opportunity_field",
"image",
"is_enabled",
*/

class QuestionsOneLevelResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null

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