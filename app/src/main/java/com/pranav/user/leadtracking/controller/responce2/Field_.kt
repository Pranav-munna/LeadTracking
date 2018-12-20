package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder("name"/*, "description", "type"*/)
class Field_ {

    /*@JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null*/
    /*@JsonProperty("description")
   @get:JsonProperty("description")
   @set:JsonProperty("description")
   var description: String? = null
   @JsonProperty("type")
   @get:JsonProperty("type")
   @set:JsonProperty("type")
   var type: String? = null*/
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