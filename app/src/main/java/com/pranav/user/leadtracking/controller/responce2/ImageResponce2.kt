package com.pranav.user.leadtracking.controller.responce2

import com.fasterxml.jackson.annotation.*
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id",
        "post_id",
        "image",
        "width",
        "height")
class ImageResponce2 {

    @JsonProperty("id")
    @get:JsonProperty("id")
    var id: Int? = null

    @JsonProperty("post_id")
    @get:JsonProperty("post_id")
    var post_id: Int? = null

    @JsonProperty("image")
    @get:JsonProperty("image")
    var image: String? = null

    @JsonProperty("width")
    @get:JsonProperty("width")
    var width: Int? = null

    @JsonProperty("height")
    @get:JsonProperty("height")
    var height: Int? = null
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
