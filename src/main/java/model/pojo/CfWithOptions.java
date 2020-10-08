package model.pojo;

import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CfWithOptions {

    public CfWithOptions(CustomField customField, Options options) {
        this.customField = customField;
        this.options = options;
    }

    @SerializedName("customField")
    @Expose
    private CustomField customField;

    @SerializedName("value")
    @Expose
    private Options options;

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}