package com.pranav.user.leadtracking.controller.responce2

import java.util.HashMap
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.pranav.user.leadtracking.controller.responce3.GroupDatumDealResponce
import com.pranav.user.leadtracking.controller.responce3.Level
import com.pranav.user.leadtracking.controller.responce3.Member
import com.pranav.user.leadtracking.controller.responce3.Score

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "name", "description", "billing_type",
        "deliverable_type", "score", "group_id", "contact_id",
        "level_id", "due_date", "estimated_start_date", "estimated_end_date",
        "estimated_close_date", "estimated_revenue", "cost_amount", "opportunity_type",
        "opportunity_id", "proposal_sow_required", "proposal_sow_date",
        /*"pip_approval_flag",*/ "ust_pipsheet", "actual_start_date",
        "actual_end_date", "last_contacted_at", "created_at", "updated_at",
        "contact", "group", "level", "scores", "members")
class DatumDealResponce {

    @JsonProperty("id")
    @get:JsonProperty("id")
    @set:JsonProperty("id")
    var id: Int? = null
    @JsonProperty("name")
    @get:JsonProperty("name")
    @set:JsonProperty("name")
    var name: String? = null
    @JsonProperty("description")
    @get:JsonProperty("description")
    @set:JsonProperty("description")
    var description: Any? = null
    @JsonProperty("billing_type")
    @get:JsonProperty("billing_type")
    @set:JsonProperty("billing_type")
    var billingType: Int? = null
    @JsonProperty("deliverable_type")
    @get:JsonProperty("deliverable_type")
    @set:JsonProperty("deliverable_type")
    var deliverableType: Int? = null
    @JsonProperty("score")
    @get:JsonProperty("score")
    @set:JsonProperty("score")
    var score: Int? = null
    @JsonProperty("group_id")
    @get:JsonProperty("group_id")
    @set:JsonProperty("group_id")
    var groupId: Int? = null
    @JsonProperty("contact_id")
    @get:JsonProperty("contact_id")
    @set:JsonProperty("contact_id")
    var contactId: Int? = null
    @JsonProperty("level_id")
    @get:JsonProperty("level_id")
    @set:JsonProperty("level_id")
    var levelId: Int? = null
    @JsonProperty("due_date")
    @get:JsonProperty("due_date")
    @set:JsonProperty("due_date")
    var dueDate: String? = null
    @JsonProperty("estimated_start_date")
    @get:JsonProperty("estimated_start_date")
    @set:JsonProperty("estimated_start_date")
    var estimatedStartDate: String? = null
    @JsonProperty("estimated_end_date")
    @get:JsonProperty("estimated_end_date")
    @set:JsonProperty("estimated_end_date")
    var estimatedEndDate: String? = null
    @JsonProperty("estimated_close_date")
    @get:JsonProperty("estimated_close_date")
    @set:JsonProperty("estimated_close_date")
    var estimatedCloseDate: String? = null
    @JsonProperty("estimated_revenue")
    @get:JsonProperty("estimated_revenue")
    @set:JsonProperty("estimated_revenue")
    var estimatedRevenue: String? = null
    @JsonProperty("cost_amount")
    @get:JsonProperty("cost_amount")
    @set:JsonProperty("cost_amount")
    var costAmount: String? = null
    @JsonProperty("opportunity_type")
    @get:JsonProperty("opportunity_type")
    @set:JsonProperty("opportunity_type")
    var opportunityType: String? = null
    @JsonProperty("opportunity_id")
    @get:JsonProperty("opportunity_id")
    @set:JsonProperty("opportunity_id")
    var opportunityId: String? = null
    @JsonProperty("proposal_sow_required")
    @get:JsonProperty("proposal_sow_required")
    @set:JsonProperty("proposal_sow_required")
    var proposalSowRequired: String? = null
    @JsonProperty("proposal_sow_date")
    @get:JsonProperty("proposal_sow_date")
    @set:JsonProperty("proposal_sow_date")
    var proposalSowDate: String? = null
    /*@JsonProperty("pip_approval_flag")
    @get:JsonProperty("pip_approval_flag")
    @set:JsonProperty("pip_approval_flag")
    var pipApprovalFlag: Boolean? = null*/
    @JsonProperty("ust_pipsheet")
    @get:JsonProperty("ust_pipsheet")
    @set:JsonProperty("ust_pipsheet")
    var ustPipsheet: String? = null
    @JsonProperty("actual_start_date")
    @get:JsonProperty("actual_start_date")
    @set:JsonProperty("actual_start_date")
    var actualStartDate: String? = null
    @JsonProperty("actual_end_date")
    @get:JsonProperty("actual_end_date")
    @set:JsonProperty("actual_end_date")
    var actualEndDate: String? = null
    @JsonProperty("last_contacted_at")
    @get:JsonProperty("last_contacted_at")
    @set:JsonProperty("last_contacted_at")
    var lastContactedAt: String? = null
    @JsonProperty("created_at")
    @get:JsonProperty("created_at")
    @set:JsonProperty("created_at")
    var createdAt: String? = null
    @JsonProperty("updated_at")
    @get:JsonProperty("updated_at")
    @set:JsonProperty("updated_at")
    var updatedAt: String? = null


    @JsonProperty("contact")
    @get:JsonProperty("contact")
    @set:JsonProperty("contact")
    var contact: Contact? = null
    @JsonProperty("group")
    @get:JsonProperty("group")
    @set:JsonProperty("group")
    var group: GroupDatumDealResponce? = null
    @JsonProperty("level")
    @get:JsonProperty("level")
    @set:JsonProperty("level")
    var level: Level? = null
    @JsonProperty("scores")
    @get:JsonProperty("scores")
    @set:JsonProperty("scores")
    var scores: List<Score>? = null
    @JsonProperty("members")
    @get:JsonProperty("members")
    @set:JsonProperty("members")
    var members: List<Member>? = null

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