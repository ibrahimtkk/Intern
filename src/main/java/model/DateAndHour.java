package model;

import java.util.Date;

public class DateAndHour {
    String username;
    Date date;
    Integer hour;

    public DateAndHour(String username, Date date, Integer hour) {
        this.username = username;
        this.date = date;
        this.hour = hour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}