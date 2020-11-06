package model.pojo.TempoPlanner;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "assignee",
        "planItem",
        "scope",
        "commitment",
        "secondsPerDay",
        "includeNonWorkingDays",
        "start",
        "startTime",
        "end",
        "description",
        "seconds",
        "created",
        "createdBy",
        "createdByKey",
        "updated",
        "updatedBy",
        "recurrence",
        "planApproval"
})
public class Allocation {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("assignee")
    private Assignee assignee;
    @JsonProperty("planItem")
    private PlanItem planItem;
    @JsonProperty("scope")
    private Scope scope;
    @JsonProperty("commitment")
    private Double commitment;
    @JsonProperty("secondsPerDay")
    private Integer secondsPerDay;
    @JsonProperty("includeNonWorkingDays")
    private Boolean includeNonWorkingDays;
    @JsonProperty("start")
    private String start;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("end")
    private String end;
    @JsonProperty("description")
    private String description;
    @JsonProperty("seconds")
    private Integer seconds;
    @JsonProperty("created")
    private String created;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdByKey")
    private String createdByKey;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("updatedBy")
    private String updatedBy;
    @JsonProperty("recurrence")
    private Recurrence recurrence;
    @JsonProperty("planApproval")
    private PlanApproval planApproval;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("assignee")
    public Assignee getAssignee() {
        return assignee;
    }

    @JsonProperty("assignee")
    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    @JsonProperty("planItem")
    public PlanItem getPlanItem() {
        return planItem;
    }

    @JsonProperty("planItem")
    public void setPlanItem(PlanItem planItem) {
        this.planItem = planItem;
    }

    @JsonProperty("scope")
    public Scope getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @JsonProperty("commitment")
    public Double getCommitment() {
        return commitment;
    }

    @JsonProperty("commitment")
    public void setCommitment(Double commitment) {
        this.commitment = commitment;
    }

    @JsonProperty("secondsPerDay")
    public Integer getSecondsPerDay() {
        return secondsPerDay;
    }

    @JsonProperty("secondsPerDay")
    public void setSecondsPerDay(Integer secondsPerDay) {
        this.secondsPerDay = secondsPerDay;
    }

    @JsonProperty("includeNonWorkingDays")
    public Boolean getIncludeNonWorkingDays() {
        return includeNonWorkingDays;
    }

    @JsonProperty("includeNonWorkingDays")
    public void setIncludeNonWorkingDays(Boolean includeNonWorkingDays) {
        this.includeNonWorkingDays = includeNonWorkingDays;
    }

    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    @JsonProperty("startTime")
    public String getStartTime() {
        return startTime;
    }

    @JsonProperty("startTime")
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @JsonProperty("end")
    public String getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(String end) {
        this.end = end;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("seconds")
    public Integer getSeconds() {
        return seconds;
    }

    @JsonProperty("seconds")
    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("createdByKey")
    public String getCreatedByKey() {
        return createdByKey;
    }

    @JsonProperty("createdByKey")
    public void setCreatedByKey(String createdByKey) {
        this.createdByKey = createdByKey;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("updatedBy")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updatedBy")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonProperty("recurrence")
    public Recurrence getRecurrence() {
        return recurrence;
    }

    @JsonProperty("recurrence")
    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    @JsonProperty("planApproval")
    public PlanApproval getPlanApproval() {
        return planApproval;
    }

    @JsonProperty("planApproval")
    public void setPlanApproval(PlanApproval planApproval) {
        this.planApproval = planApproval;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}