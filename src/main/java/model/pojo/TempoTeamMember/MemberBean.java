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
        "teamMemberId",
        "name",
        "type",
        "avatar",
        "activeInJira",
        "key",
        "displayname"
})
public class MemberBean {

    @JsonProperty("teamMemberId")
    private Integer teamMemberId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("avatar")
    private Avatar_ avatar;
    @JsonProperty("activeInJira")
    private Boolean activeInJira;
    @JsonProperty("key")
    private String key;
    @JsonProperty("displayname")
    private String displayname;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("teamMemberId")
    public Integer getTeamMemberId() {
        return teamMemberId;
    }

    @JsonProperty("teamMemberId")
    public void setTeamMemberId(Integer teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("avatar")
    public Avatar_ getAvatar() {
        return avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(Avatar_ avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("activeInJira")
    public Boolean getActiveInJira() {
        return activeInJira;
    }

    @JsonProperty("activeInJira")
    public void setActiveInJira(Boolean activeInJira) {
        this.activeInJira = activeInJira;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("displayname")
    public String getDisplayname() {
        return displayname;
    }

    @JsonProperty("displayname")
    public void setDisplayname(String displayname) {
        this.displayname = displayname;
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