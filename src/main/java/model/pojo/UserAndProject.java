package model.pojo;

public class UserAndProject {
    private String username;
    private String projectKey;

    public UserAndProject(String username, String projectKey) {
        this.username = username;
        this.projectKey = projectKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }
}
