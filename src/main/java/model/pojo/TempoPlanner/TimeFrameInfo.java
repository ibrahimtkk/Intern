
package model.pojo.TempoPlanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeFrameInfo {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("current")
    @Expose
    private Boolean current;
    @SerializedName("start")
    @Expose
    private Double start;
    @SerializedName("end")
    @Expose
    private Double end;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

}
