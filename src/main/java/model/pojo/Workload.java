package model.pojo;

import java.util.List;

public class Workload {

    private String description;

    private Integer id;

    private String name;

    private Integer memberCount;

    private List<Day> days = null;

    private Boolean defaultScheme;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public Boolean getDefaultScheme() {
        return defaultScheme;
    }

    public void setDefaultScheme(Boolean defaultScheme) {
        this.defaultScheme = defaultScheme;
    }
}
