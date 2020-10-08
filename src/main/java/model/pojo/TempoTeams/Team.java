
package model.pojo.TempoTeams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("lead")
    @Expose
    private String lead;
    @SerializedName("program")
    @Expose
    private String program;
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    @SerializedName("RemainingInAYear")
    @Expose
    private Double RemainingInAYear;

    @SerializedName("TotalAvailabilityInAYear")
    @Expose
    private Double TotalAvailabilityInAYear;

    public void setRemainingInAYear(Double RemainingInAYear) {
        this.RemainingInAYear = RemainingInAYear;
    }

    public Double getRemainingInAYear() { return RemainingInAYear; }

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

    public String getProgram() {
        return program;
    }

    public Double getTotalAvailabilityInAYear() {
        return TotalAvailabilityInAYear;
    }

    public void setTotalAvailabilityInAYear(Double totalAvailabilityInAYear) {
        TotalAvailabilityInAYear = totalAvailabilityInAYear;
    }


}
