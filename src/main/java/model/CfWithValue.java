package model;

import com.atlassian.jira.issue.fields.CustomField;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CfWithValue {

    @SerializedName("customField")
    @Expose
    private CustomField customField;

    @SerializedName("value")
    @Expose
    private String value;

    public CfWithValue(CustomField customField, String value) {
        this.customField = customField;
        this.value = value;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }

    public String getValue() {

        if(value.length()>400){
            return value.substring(0,396) + "....";
        }
        else {
            return value;
        }
    }

    public void setValue(String value) {
        this.value = value;
    }
}
