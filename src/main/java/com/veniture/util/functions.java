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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.pojo.TempoPlanner.Allocation;
import model.pojo.TempoPlanner.Holiday;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.veniture.constants.Constants.*;
import static com.veniture.constants.Constants.adminPassword;

public class functions {

    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

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

    public static List<Allocation> getAllocationsByDate(String startDate, String endDate) throws IOException {
        Type tempoAllocationDataType = new TypeToken<List<Allocation>>() {}.getType();
        List<Allocation> tempoAllocationData = GSON.fromJson(getResponseByStartAndEndDate(startDate, endDate), tempoAllocationDataType);
        return tempoAllocationData;
    }

    public static String getResponseByStartAndEndDate(String startDate, String endDate) throws IOException {
        String QUERY = QUERY_ALLOCATION_BY_DATE.replace("SSS", startDate).replace("EEE", endDate);
        return getResponseString(QUERY);
    }

    public static String getResponseString(String Query) throws IOException {
        String hostname;
        String scheme;
        assert JIRA_BASE_URL != null;
        if (JIRA_BASE_URL.contains("veniture")){
            hostname= venitureHostname;
            scheme = schemeHTTPS;
        }
        else if (JIRA_BASE_URL.contains("localhost")){
            hostname = JIRA_BASE_URL;
            scheme = "";
        }
        else {
            hostname= floHostname;
            scheme = schemeHTTP;
        }

        String fullUrl = scheme + hostname + Query;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(fullUrl);
        httpGet.addHeader(BasicScheme.authenticate(
                new UsernamePasswordCredentials(adminUsername, adminPassword),
                "UTF-8", false));

        org.apache.http.HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity responseEntity = httpResponse.getEntity();
        InputStream inputStream = responseEntity.getContent();
        String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        return response;
    }

    public static List<String> getHolidayList() throws IOException, URISyntaxException {
//        List<String> holidaysList = new ArrayList<>();
//        URIBuilder builder = new URIBuilder(Holiday_API_Url);
//        builder.setParameter("country", "TR")
//                .setParameter("year", YEAR)
//                .setParameter("key", Holiday_API);
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(builder.build());
//
//        org.apache.http.HttpResponse httpResponse = httpClient.execute(httpGet);
//        HttpEntity responseEntity = httpResponse.getEntity();
//        InputStream inputStream = responseEntity.getContent();
//        String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
//
//        Type holidayDataType = new TypeToken<List<Holiday>>() {}.getType();
//        List<Holiday> holidayData = GSON.fromJson(response, holidayDataType);
//        holidayData.forEach(holiday -> {
//            holidaysList.add(holiday.getDate());
//        });
        List<String> holidaysList = holidays;
        return holidaysList;
    }

    public static long getNonHolidayCount(Date startDate, Date endDate) throws IOException, URISyntaxException {
        List<String> holidaysList = getHolidayList();
        List<String> nonHolidaysList = new ArrayList<>();
        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        long farkGun = ((endDateLong - startDateLong)/1000)/86400;
        Calendar calendar = Calendar.getInstance();
        for (int i=0; i<farkGun; i++) {
            calendar.setTime(startDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);
            int dow = calendar.get(Calendar.DAY_OF_WEEK);
            if (((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY)) && (!holidaysList.contains(calendar.getTime().toLocaleString())   )){
                nonHolidaysList.add(calendar.getTime().toLocaleString());
            }
        }
        return nonHolidaysList.size();
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
