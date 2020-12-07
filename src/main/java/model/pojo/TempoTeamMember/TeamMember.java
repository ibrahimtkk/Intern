package model.pojo.TempoTeamMember;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "member",
        "memberBean",
        "membership",
        "membershipBean",
        "showDeactivate"
})
public class TeamMember {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("member")
    private Member member;
    @JsonProperty("memberBean")
    private MemberBean memberBean;
    @JsonProperty("membership")
    private Membership membership;
    @JsonProperty("membershipBean")
    private MembershipBean membershipBean;
    @JsonProperty("showDeactivate")
    private Boolean showDeactivate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("member")
    public Member getMember() {
        return member;
    }

    @JsonProperty("member")
    public void setMember(Member member) {
        this.member = member;
    }

    @JsonProperty("memberBean")
    public MemberBean getMemberBean() {
        return memberBean;
    }

    @JsonProperty("memberBean")
    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    @JsonProperty("membership")
    public Membership getMembership() {
        return membership;
    }

    @JsonProperty("membership")
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    @JsonProperty("membershipBean")
    public MembershipBean getMembershipBean() {
        return membershipBean;
    }

    @JsonProperty("membershipBean")
    public void setMembershipBean(MembershipBean membershipBean) {
        this.membershipBean = membershipBean;
    }

    @JsonProperty("showDeactivate")
    public Boolean getShowDeactivate() {
        return showDeactivate;
    }

    @JsonProperty("showDeactivate")
    public void setShowDeactivate(Boolean showDeactivate) {
        this.showDeactivate = showDeactivate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}