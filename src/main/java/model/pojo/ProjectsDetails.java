//
//package model.pojo;
//
//import java.util.HashMap;
//import java.util.Map;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//        "key",
//        "GM",
//        "DP"
//})
//public class ProjectsDetails {
//
//    @JsonProperty("key")
//    private String key;
//    @JsonProperty("GM")
//    private String gM;
//    @JsonProperty("DP")
//    private String dP;
//    @JsonIgnore
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//    @JsonProperty("key")
//    public String getKey() {
//        return key;
//    }
//
//    @JsonProperty("key")
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    @JsonProperty("GM")
//    public String getGM() {
//        return gM;
//    }
//
//    @JsonProperty("GM")
//    public void setGM(String gM) {
//        this.gM = gM;
//    }
//
//    @JsonProperty("DP")
//    public String getDP() {
//        return dP;
//    }
//
//    @JsonProperty("DP")
//    public void setDP(String dP) {
//        this.dP = dP;
//    }
//
//    @JsonAnyGetter
//    public Map<String, Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, Object value) {
//        this.additionalProperties.put(name, value);
//    }
//
//}