package model.pojo.TempoTeamMember;

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
        "role",
        "dateFrom",
        "dateTo",
        "dateFromANSI",
        "dateToANSI",
        "availability",
        "teamMemberId",
        "teamId",
        "status"
})
public class MembershipBean {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("role")
    private Role_ role;
    @JsonProperty("dateFrom")
    private String dateFrom;
    @JsonProperty("dateTo")
    private String dateTo;
    @JsonProperty("dateFromANSI")
    private String dateFromANSI;
    @JsonProperty("dateToANSI")
    private String dateToANSI;
    @JsonProperty("availability")
    private String availability;
    @JsonProperty("teamMemberId")
    private Integer teamMemberId;
    @JsonProperty("teamId")
    private Integer teamId;
    @JsonProperty("status")
    private String status;
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

    @JsonProperty("role")
    public Role_ getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(Role_ role) {
        this.role = role;
    }

    @JsonProperty("dateFrom")
    public String getDateFrom() {
        return dateFrom;
    }

    @JsonProperty("dateFrom")
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @JsonProperty("dateTo")
    public String getDateTo() {
        return dateTo;
    }

    @JsonProperty("dateTo")
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @JsonProperty("dateFromANSI")
    public String getDateFromANSI() {
        return dateFromANSI;
    }

    @JsonProperty("dateFromANSI")
    public void setDateFromANSI(String dateFromANSI) {
        this.dateFromANSI = dateFromANSI;
    }

    @JsonProperty("dateToANSI")
    public String getDateToANSI() {
        return dateToANSI;
    }

    @JsonProperty("dateToANSI")
    public void setDateToANSI(String dateToANSI) {
        this.dateToANSI = dateToANSI;
    }

    @JsonProperty("availability")
    public String getAvailability() {
        return availability;
    }

    @JsonProperty("availability")
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @JsonProperty("teamMemberId")
    public Integer getTeamMemberId() {
        return teamMemberId;
    }

    @JsonProperty("teamMemberId")
    public void setTeamMemberId(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    @JsonProperty("teamId")
    public Integer getTeamId() {
        return teamId;
    }

    @JsonProperty("teamId")
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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