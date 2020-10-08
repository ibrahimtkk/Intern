package com.veniture.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.event.type.EventDispatchOption;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;
import com.atlassian.jira.user.ApplicationUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class functions {

    public static void updateCustomFieldValue(MutableIssue issue, Long cfId, Object value, ApplicationUser user) {
        CustomField cf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(cfId);
        issue.setCustomFieldValue(cf,value);
        ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false);
    }

    public static void updateCfValueForSelectList(MutableIssue issue, Long cfId, Long optionId, ApplicationUser user) {
        CustomField cf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(cfId);
        FieldConfig fieldConfig=cf.getRelevantConfig(issue);
        Option option=ComponentAccessor.getOptionsManager().getOptions(fieldConfig).getOptionById(optionId);
        issue.setCustomFieldValue(cf,option);
        ComponentAccessor.getIssueManager().updateIssue(user, issue, EventDispatchOption.ISSUE_UPDATED, false);
    }

    public static String getCustomFieldValueFromIssue(MutableIssue issue, Long cfId, CustomFieldManager cfm){
        CustomField cf = cfm.getCustomFieldObject(cfId);
        Object value = cf.getValue(issue);
       // Object value = issue.getCustomFieldValue(c);
        if (value instanceof Double) {
            return  String.valueOf(((Double) value).intValue());
        }
        else {
            return value.toString();
        }
    }

    public static List<CustomField> getCustomFieldsInProject(String projectKey){
        Long projectId = ComponentAccessor.getProjectManager().getProjectByCurrentKey(projectKey).getId();
        return ComponentAccessor.getCustomFieldManager().getCustomFieldObjects(projectId, ConstantsManager.ALL_ISSUE_TYPES);
    }

    public static void setCustomFieldValue(String issueKey, String customFieldId,String newvalue){
        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();
        MutableIssue issue=ComponentAccessor.getIssueManager().getIssueByKeyIgnoreCase(issueKey);
        CustomField cf = customFieldManager.getCustomFieldObject(customFieldId);
        DefaultIssueChangeHolder changeHolder = new DefaultIssueChangeHolder();
        cf.updateValue(null, issue, new ModifiedValue(issue.getCustomFieldValue(cf), newvalue),changeHolder);
    }



    public static Double calculateDayCountFromHour(Double hourTime){
        Double aDouble = new Double(hourTime) / 9 ;
        return Math.round(aDouble*1e1)/1e1;
    }

//    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
//        Calendar startCal = Calendar.getInstance();
//        startCal.setTime(startDate);
//
//        Calendar endCal = Calendar.getInstance();
//        endCal.setTime(endDate);
//
//        int workDays = 0;
//
//        //Return 0 if start and end are the same
//        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
//            return 0;
//        }
//
//        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
//            startCal.setTime(endDate);
//            endCal.setTime(startDate);
//        }
//
//        do {
//            //excluding start date
//            startCal.add(Calendar.DAY_OF_MONTH, 1);
//            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
//                ++workDays;
//            }
//        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
//
//        return workDays;
//    }
}
