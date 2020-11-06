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
        "requester",
        "reviewer",
        "actor",
        "statusCode",
        "latestAction",
        "updated",
        "created"
})
public class PlanApproval {

    @JsonProperty("requester")
    private Requester requester;
    @JsonProperty("reviewer")
    private Reviewer reviewer;
    @JsonProperty("actor")
    private Actor actor;
    @JsonProperty("statusCode")
    private Integer statusCode;
    @JsonProperty("latestAction")
    private LatestAction latestAction;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("created")
    private String created;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("requester")
    public Requester getRequester() {
        return requester;
    }

    @JsonProperty("requester")
    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    @JsonProperty("reviewer")
    public Reviewer getReviewer() {
        return reviewer;
    }

    @JsonProperty("reviewer")
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    @JsonProperty("actor")
    public Actor getActor() {
        return actor;
    }

    @JsonProperty("actor")
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("latestAction")
    public LatestAction getLatestAction() {
        return latestAction;
    }

    @JsonProperty("latestAction")
    public void setLatestAction(LatestAction latestAction) {
        this.latestAction = latestAction;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
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