
package model.pojo.TempoPlanner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("header")
    @Expose
    private Member header;
    @SerializedName("columns")
    @Expose
    private List<AvailabilityInfos> columns = null;

    public Member getHeader() {
        return header;
    }

    public void setHeader(Member header) {
        this.header = header;
    }

    public List<AvailabilityInfos> getColumns() {
        return columns;
    }

    public void setColumns(List<AvailabilityInfos> columns) {
        this.columns = columns;
    }

}
