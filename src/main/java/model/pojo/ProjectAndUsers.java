package model.pojo;

import java.util.List;

public class ProjectAndUsers {
    private String projectKey;
    private List<String> users;

    public ProjectAndUsers(String projectKey, List<String> users) {
        this.projectKey = projectKey;
        this.users = users;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
