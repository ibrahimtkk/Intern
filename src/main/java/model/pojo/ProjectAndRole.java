package model.pojo;

import java.util.List;

public class ProjectAndRole {
    private String projectKey;
    private List<String> roles;

    public ProjectAndRole(String projectKey, List<String> roles) {
        this.projectKey = projectKey;
        this.roles = roles;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
