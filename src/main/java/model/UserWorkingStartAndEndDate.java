package model;

import java.util.Date;

public class UserWorkingStartAndEndDate {
    String username;
    String issueKey;
    Date startDate;
    Date endDate;
    Integer workingHours;

    public UserWorkingStartAndEndDate(String username, String issueKey, Date startDate, Date endDate) {
        this.username = username;
        this.issueKey = issueKey;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }
}
