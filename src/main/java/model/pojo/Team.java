//
//package model.pojo;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.ToStringBuilder;
//
//public class Team {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("summary")
//    @Expose
//    private String summary;
//    @SerializedName("lead")
//    @Expose
//    private String lead;
//    @SerializedName("program")
//    @Expose
//    private String program;
//    @SerializedName("isPublic")
//    @Expose
//    private Boolean isPublic;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSummary() {
//        return summary;
//    }
//
//    public void setSummary(String summary) {
//        this.summary = summary;
//    }
//
//    public String getLead() {
//        return lead;
//    }
//
//    public void setLead(String lead) {
//        this.lead = lead;
//    }
//
//    public String getProgram() {
//        return program;
//    }
//
//    public void setProgram(String program) {
//        this.program = program;
//    }
//
//    public Boolean getIsPublic() {
//        return isPublic;
//    }
//
//    public void setIsPublic(Boolean isPublic) {
//        this.isPublic = isPublic;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("id", id).append("name", name).append("summary", summary).append("lead", lead).append("program", program).append("isPublic", isPublic).toString();
//    }
//
//}
