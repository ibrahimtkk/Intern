
package model.pojo.TempoPlanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("memberId")
    @Expose
    private Double memberId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMemberId() {
        return memberId;
    }

    public void setMemberId(Double memberId) {
        this.memberId = memberId;
    }

}
