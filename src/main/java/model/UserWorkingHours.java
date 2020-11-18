package model;


import java.util.Date;
import java.util.List;

public class UserWorkingHours {
    String username;
    String issueKey;
    List<DateAndHour> dateAndHourList;

    public UserWorkingHours(){

    }

    public UserWorkingHours(String username, String issueKey, List<DateAndHour> dateAndHourList) {
        this.username = username;
        this.issueKey = issueKey;
        this.dateAndHourList = dateAndHourList;
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

    public List<DateAndHour> getDateAndHourList() {
        return dateAndHourList;
    }

    public void setDateAndHourList(List<DateAndHour> dateAndHourList) {
        this.dateAndHourList = dateAndHourList;
    }
}