
package model.pojo.TempoPlanner;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueTableData {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;
    @SerializedName("footer")
    @Expose
    private Footer footer;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }

}
