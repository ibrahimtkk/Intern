
package model.pojo.TempoPlanner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Header {

    @SerializedName("columns")
    @Expose
    private List<TimeFrameInfo> timeFrameInfos = null;

    public List<TimeFrameInfo> getTimeFrameInfos() {
        return timeFrameInfos;
    }

    public void setTimeFrameInfos(List<TimeFrameInfo> timeFrameInfos) {
        this.timeFrameInfos = timeFrameInfos;
    }

}
