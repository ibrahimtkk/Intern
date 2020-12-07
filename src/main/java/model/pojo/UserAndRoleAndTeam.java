package model.pojo;

// todo: gerek kalmayabilir
public class UserAndRoleAndTeam {
    private String username;
    private String displayName;
    private String role;
    private String teamName;
    private Integer teamId;

    public UserAndRoleAndTeam(String username, String role, String teamName, Integer teamId) {
        this.username = username;
        this.role = role;
        this.teamName = teamName;
        this.teamId = teamId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
