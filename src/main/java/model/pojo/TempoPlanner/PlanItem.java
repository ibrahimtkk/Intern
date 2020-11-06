package model.pojo.TempoPlanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "key",
        "id",
        "type",
        "name",
        "summary",
        "description",
        "iconName",
        "iconUrl",
        "projectKey",
        "projectId",
        "components",
        "versions",
        "projectColor",
        "avatarUrls",
        "planItemUrl",
        "isResolved",
        "issueStatus"
})
public class PlanItem {

    @JsonProperty("key")
    private String key;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("description")
    private String description;
    @JsonProperty("iconName")
    private String iconName;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("projectKey")
    private String projectKey;
    @JsonProperty("projectId")
    private Integer projectId;
    @JsonProperty("components")
    private List<Object> components = null;
    @JsonProperty("versions")
    private List<Object> versions = null;
    @JsonProperty("projectColor")
    private String projectColor;
    @JsonProperty("avatarUrls")
    private AvatarUrls avatarUrls;
    @JsonProperty("planItemUrl")
    private String planItemUrl;
    @JsonProperty("isResolved")
    private Boolean isResolved;
    @JsonProperty("issueStatus")
    private IssueStatus issueStatus;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("iconName")
    public String getIconName() {
        return iconName;
    }

    @JsonProperty("iconName")
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @JsonProperty("projectKey")
    public String getProjectKey() {
        return projectKey;
    }

    @JsonProperty("projectKey")
    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    @JsonProperty("projectId")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("components")
    public List<Object> getComponents() {
        return components;
    }

    @JsonProperty("components")
    public void setComponents(List<Object> components) {
        this.components = components;
    }

    @JsonProperty("versions")
    public List<Object> getVersions() {
        return versions;
    }

    @JsonProperty("versions")
    public void setVersions(List<Object> versions) {
        this.versions = versions;
    }

    @JsonProperty("projectColor")
    public String getProjectColor() {
        return projectColor;
    }

    @JsonProperty("projectColor")
    public void setProjectColor(String projectColor) {
        this.projectColor = projectColor;
    }

    @JsonProperty("avatarUrls")
    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    @JsonProperty("avatarUrls")
    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    @JsonProperty("planItemUrl")
    public String getPlanItemUrl() {
        return planItemUrl;
    }

    @JsonProperty("planItemUrl")
    public void setPlanItemUrl(String planItemUrl) {
        this.planItemUrl = planItemUrl;
    }

    @JsonProperty("isResolved")
    public Boolean getIsResolved() {
        return isResolved;
    }

    @JsonProperty("isResolved")
    public void setIsResolved(Boolean isResolved) {
        this.isResolved = isResolved;
    }

    @JsonProperty("issueStatus")
    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    @JsonProperty("issueStatus")
    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
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