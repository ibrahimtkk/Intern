
package model.pojo.TempoPlanner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Footer {

    @SerializedName("columns")
    @Expose
    private List<FooterTotalAvailabilityInfos> columns = null;

    public List<FooterTotalAvailabilityInfos> getColumns() {
        return columns;
    }

    public void setColumns(List<FooterTotalAvailabilityInfos> columns) {
        this.columns = columns;
    }

}
