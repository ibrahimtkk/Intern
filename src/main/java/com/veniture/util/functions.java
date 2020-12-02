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
import com.atlassian.jira.user.UserPropertyManager;
import com.atlassian.jira.user.util.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.module.propertyset.PropertySet;
import com.thoughtworks.xstream.XStream;
import model.DateAndHour;
import model.UserWorkingHours;
import model.UserWorkingStartAndEndDate;
import model.pojo.TempoPlanner.Allocation;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.veniture.constants.Constants.*;
import static com.veniture.constants.Constants.adminPassword;

public class functions {

    private static boolean sameDate = false;
    private static boolean isOverload = false;
    private static boolean isSuggestable = true;
    private static String sugUser = "";
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();
    private static final XStream XSTREAM = new XStream();
    private static DateAndHour innerDateAndHour = null;
    private static List<UserWorkingStartAndEndDate> suggestedUsers = null;

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
    public static List<Allocation> getAllocationsByDateAndAssigneeKey(String startDate, String endDate, String assigneeKey) throws IOException {
        Type tempoAllocationDataType = new TypeToken<List<Allocation>>() {}.getType();
        List<Allocation> tempoAllocationData = GSON.fromJson(getResponseByStartAndEndDateAndAssigneeKey(startDate, endDate, assigneeKey), tempoAllocationDataType);
        return tempoAllocationData;
    }

    public static String getResponseByStartAndEndDate(String startDate, String endDate) throws IOException {
        String QUERY = QUERY_ALLOCATION_BY_DATE.replace("SSS", startDate).replace("EEE", endDate);
        return getResponseString(QUERY);
    }
    public static String getResponseByStartAndEndDateAndAssigneeKey(String startDate, String endDate,String assigneeKey) throws IOException {
        String QUERY = QUERY_ALLOCATION_BY_DATE_ASSIGNEE_KEY.replace("SSS", startDate).replace("EEE", endDate).replace("AAA", assigneeKey);
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

    public static List<String> getHaveSameProjectUsers(List<String> issueKeys){
        UserPropertyManager userPropertyManager = ComponentAccessor.getUserPropertyManager();
        UserUtil userUtil = ComponentAccessor.getUserUtil();
        List<String> suggestionUserName = new ArrayList<>();
        List<String> result = new ArrayList<>();

        userUtil.getUsers().forEach(u -> {
            ApplicationUser us = u;
            result.add(us.getName()) ;
        });


        result.forEach(user -> {
            ApplicationUser applicationUser = ComponentAccessor.getUserManager().getUserByName(user);
            PropertySet propertySet = userPropertyManager.getPropertySet(applicationUser);
            String projects = propertySet.getString("jira.meta.projeler");

            if (projects != null){
                List<String> projectsList = Arrays.asList(projects.split(","));
                issueKeys.forEach(issueKey -> {
                    if (projectsList.contains(issueKey))
                        suggestionUserName.add(applicationUser.getName());
                });
            }
        });
        return suggestionUserName;
    }

    // TODO: allocationStartDate ve allocationEndDate'e gore filtreleme yapilacak
    public static List<UserWorkingHours> getWorkingHoursOfUsers(List<Allocation> tempoAllocations, List<String> suggestionUserNames) throws IOException, URISyntaxException{
        List<UserWorkingHours> usersKeysAndWorkingHours = new ArrayList<>();
        List<UserWorkingHours> finalUsersKeysAndWorkingHours = usersKeysAndWorkingHours;
        tempoAllocations.forEach(tempoAllocation -> {
            suggestionUserNames.forEach(suggestionUsername -> {
                if (tempoAllocation.getAssignee().getKey().equals(suggestionUsername)){
                    String actualIssueKey = tempoAllocation.getPlanItem().getKey();
                        try {
                            Date startDateOfTempo = new SimpleDateFormat("yyyy-MM-dd").parse(tempoAllocation.getStart());
                            Date endDateOfTempo = new SimpleDateFormat("yyyy-MM-dd").parse(tempoAllocation.getEnd());
                            int workHour = tempoAllocation.getSecondsPerDay()/3600;
                            List<Date> dateList = getDaysBetweenDates(startDateOfTempo, endDateOfTempo);
                            List<DateAndHour> dateAndHourList = new ArrayList<>();
                            dateList.forEach(date -> {
                                dateAndHourList.add(new DateAndHour(tempoAllocation.getAssignee().getKey(), date, workHour));
                            });
                            UserWorkingHours userWorkingHours = new UserWorkingHours(suggestionUsername, actualIssueKey, dateAndHourList);
                            finalUsersKeysAndWorkingHours.add(userWorkingHours);
                        }
                        catch (IOException | URISyntaxException | ParseException e3) {
                            e3.printStackTrace();
                        }
                }
            });
        });
        usersKeysAndWorkingHours = sortListDateAndHoursByDate(finalUsersKeysAndWorkingHours);
        return usersKeysAndWorkingHours;
    }

    public static List<List<DateAndHour>> getTotalWorkingHoursOfUsers(List<UserWorkingHours> usersAndWorkingHours, List<String> suggestionUsernames){
        List<List<DateAndHour>> usersAndTotalWorkingHours = new ArrayList<>();

        List<List<DateAndHour>> finalUsersAndTotalWorkingHours = usersAndTotalWorkingHours;
        suggestionUsernames.forEach(sugUser -> {
            List<DateAndHour> dateAndHourList = new ArrayList<>();
            usersAndWorkingHours.forEach(userAndWH -> {
                if (userAndWH.getUsername().equals(sugUser)){
                    sameDate = false;
                    userAndWH.getDateAndHourList().forEach(userAndWHDateAndHourList -> {
                        dateAndHourList.forEach(dateAndHour -> {
                            if (dateAndHour.getDate().equals(userAndWHDateAndHourList.getDate())){
                                sameDate = true;
                                dateAndHour.setHour(dateAndHour.getHour() + userAndWHDateAndHourList.getHour());
                            }
                        });
                        if (!sameDate){
                            dateAndHourList.add(new DateAndHour(userAndWHDateAndHourList.getUsername(), userAndWHDateAndHourList.getDate(), userAndWHDateAndHourList.getHour()));
                        }
                        sameDate = false;
                    });
                }
            });
            finalUsersAndTotalWorkingHours.add(dateAndHourList);
        });
        usersAndTotalWorkingHours = sortListListDateAndHoursByDate(finalUsersAndTotalWorkingHours);
        return usersAndTotalWorkingHours;
    }

    public static List<List<DateAndHour>> sortListListDateAndHoursByDate(List<List<DateAndHour>> usersAndTotalWorkingHours){
        usersAndTotalWorkingHours.forEach(userAndTotalWorkingHours -> {
            for (int i=0; i<userAndTotalWorkingHours.size(); i++){
                DateAndHour minDateAndHour = userAndTotalWorkingHours.get(i);
                int minInt = i;
                for (int j=i+1; j<userAndTotalWorkingHours.size(); j++){
                    if (userAndTotalWorkingHours.get(j).getDate().before(minDateAndHour.getDate())) {
                        minDateAndHour = userAndTotalWorkingHours.get(j);
                        minInt = j;
                    }
                }
                DateAndHour tempDateAndHour = (DateAndHour) XSTREAM.fromXML(XSTREAM.toXML(userAndTotalWorkingHours.get(i)));
                userAndTotalWorkingHours.get(i).setDate(minDateAndHour.getDate());
                userAndTotalWorkingHours.get(i).setHour(minDateAndHour.getHour());
                userAndTotalWorkingHours.get(minInt).setDate(tempDateAndHour.getDate());
                userAndTotalWorkingHours.get(minInt).setHour(tempDateAndHour.getHour());
            };
        });

        return usersAndTotalWorkingHours;
    }

    public static List<UserWorkingHours> sortListDateAndHoursByDate(List<UserWorkingHours> usersKeysAndWorkingHours){
        usersKeysAndWorkingHours.forEach(userWorkingHours -> {
            for (int i=0; i<userWorkingHours.getDateAndHourList().size(); i++){
                DateAndHour minDateAndHour = userWorkingHours.getDateAndHourList().get(i);
                int minInt = i;
                for (int j=i+1; j<userWorkingHours.getDateAndHourList().size(); j++){
                    if (userWorkingHours.getDateAndHourList().get(j).getDate().before(minDateAndHour.getDate())){
                        minDateAndHour = userWorkingHours.getDateAndHourList().get(j);
                        minInt = j;
                    }
                }
                DateAndHour tempDateAndHour = (DateAndHour) XSTREAM.fromXML(XSTREAM.toXML(userWorkingHours.getDateAndHourList().get(i)));
                userWorkingHours.getDateAndHourList().get(i).setDate(minDateAndHour.getDate());
                userWorkingHours.getDateAndHourList().get(i).setHour(minDateAndHour.getHour());
                userWorkingHours.getDateAndHourList().get(minInt).setDate(tempDateAndHour.getDate());
                userWorkingHours.getDateAndHourList().get(minInt).setHour(tempDateAndHour.getHour());
            }
        });
        return usersKeysAndWorkingHours;
    }

    public static List<UserWorkingStartAndEndDate> getUserWorkingStartAndEndDateList(List<UserWorkingHours> usersAndWorkingHour){
        List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList = new ArrayList<>();
        usersAndWorkingHour.forEach(userWorkingHours -> {
            int size = userWorkingHours.getDateAndHourList().size();
            Date firstDate = userWorkingHours.getDateAndHourList().get(0).getDate();
            Date lastDate = userWorkingHours.getDateAndHourList().get(size-1).getDate();
            // TODO: workingHour'un dogrulugunu kontrol et emin degilim
            int workingHour = userWorkingHours.getDateAndHourList().get(0).getHour();
            UserWorkingStartAndEndDate userWorkingStartAndEndDate = new UserWorkingStartAndEndDate(userWorkingHours.getUsername(), userWorkingHours.getIssueKey(), firstDate, lastDate);
            userWorkingStartAndEndDate.setWorkingHours(workingHour);
            userWorkingStartAndEndDateList.add(userWorkingStartAndEndDate);
        });
        return userWorkingStartAndEndDateList;
    }

    public static List<UserWorkingStartAndEndDate> checkWorkingTimeAndSuggestAnotherUser(List<List<DateAndHour>> totalWorkingHoursOfUsers, List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList, List<String> suggestionUsernames, String issueKey){
        List<String> willSuggestUsernames = new ArrayList<>();
        suggestedUsers = new ArrayList<>();
        innerDateAndHour = null;
        userWorkingStartAndEndDateList.forEach(userWorkingStartAndEndDate -> {
            if (userWorkingStartAndEndDate.getIssueKey().equals(issueKey)){
                Date startDate = userWorkingStartAndEndDate.getStartDate();
                Date endDate = userWorkingStartAndEndDate.getEndDate();
                totalWorkingHoursOfUsers.forEach(totalWorkingHoursOfUser -> {
                    totalWorkingHoursOfUser.forEach(innerTotal -> {
                        if (innerTotal.getUsername().equals(userWorkingStartAndEndDate.getUsername())){
                            if (startDate.before(innerTotal.getDate()) || endDate.after(innerTotal.getDate()) ||
                            startDate.getTime() == innerTotal.getDate().getTime() || endDate.getTime() == innerTotal.getDate().getTime()){
                                if (innerTotal.getHour() > MAXWORKINGTIME){
                                    isOverload = true;
                                    innerDateAndHour = new DateAndHour(innerTotal.getUsername(), innerTotal.getDate(), innerTotal.getHour());
                                }
                            }
                        }
                    });
                    if (isOverload){
                        suggestedUsers = suggestNewUser(totalWorkingHoursOfUsers, userWorkingStartAndEndDateList, suggestionUsernames, issueKey, innerDateAndHour.getUsername(), startDate, endDate);
                    }
                });
            }
        });

        return suggestedUsers;
    }

    public static List<UserWorkingStartAndEndDate> suggestNewUser(List<List<DateAndHour>> totalWorkingHoursOfUsers, List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList, List<String> suggestionUsernames, String issueKey, String bannedUsername, Date startDate, Date endDate){
        List<String> suggestedUsers = new ArrayList<>();
        List<UserWorkingStartAndEndDate> sendUserWorkingStartAndEndDateList = new ArrayList<>();
        isSuggestable = true;
        sugUser = "";
        totalWorkingHoursOfUsers.forEach(totalWorkingHoursOfUser -> {
            totalWorkingHoursOfUser.forEach(innerTotal -> {
                if (!innerTotal.getUsername().equals(bannedUsername)){
                    sugUser = innerTotal.getUsername();
                    if (startDate.before(innerTotal.getDate()) || endDate.after(innerTotal.getDate()) ||
                            startDate.getTime() == innerTotal.getDate().getTime() || endDate.getTime() == innerTotal.getDate().getTime()){
                        int suggestedWH = innerTotal.getHour();
                        if (suggestedWH > MAXWORKINGTIME){
                            isSuggestable = false;
                        }
                    }
                }
            });
            if (isSuggestable){
                if (!sugUser.equals(""))
                    if (!suggestedUsers.contains(sugUser)) {
                        suggestedUsers.add(sugUser);
                        UserWorkingStartAndEndDate userWorkingStartAndEndDate = new UserWorkingStartAndEndDate(sugUser, issueKey, startDate, endDate);
                        sendUserWorkingStartAndEndDateList.add(userWorkingStartAndEndDate);
                    }
            }
        });
        return sendUserWorkingStartAndEndDateList;
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) throws IOException, URISyntaxException {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(enddate); calendar.add(Calendar.DATE, 1); enddate = calendar.getTime();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            if (!isHoliday(result)) {
                dates.add(result);
            }
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static long getNonHolidayCount(Date startDate, Date endDate) throws IOException, URISyntaxException {
        List<String> holidaysList = getHolidayList();
        List<String> nonHolidaysList = new ArrayList<>();
        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        long farkGun = ((endDateLong - startDateLong)/1000)/86400 +1;
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

    public static boolean isHoliday(Date date) throws IOException, URISyntaxException{
        List<String> holidaysList = getHolidayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        if (((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY)) && (!holidaysList.contains(calendar.getTime().toLocaleString())   ))
            return false;
        return true;
    }

    public static long getWorkingDaysBetweenTwoDate(Date constraintStartDate, Date constraintEndDate, Date allocationStartDate, Date allocationEndDate){
        long fark = 0;
        if (constraintStartDate.before(allocationStartDate)) {
            if (constraintEndDate.before(allocationEndDate)) {
                try {
                    fark = getNonHolidayCount(allocationStartDate, constraintEndDate);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    fark = getNonHolidayCount(allocationStartDate, allocationEndDate);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if (constraintEndDate.before(allocationEndDate)) {
                try {
                    fark = getNonHolidayCount(constraintStartDate, constraintEndDate);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    fark = getNonHolidayCount(constraintStartDate, allocationEndDate);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }
        return fark;
    }

    public static List<String> getUserProperty(){
        List<String> userPropertyList = new ArrayList<>();
        UserPropertyManager userPropertyManager = ComponentAccessor.getUserPropertyManager();
        ApplicationUser applicationUser = ComponentAccessor.getUserManager().getUserByName("2.ibrahim");
        PropertySet propertySet = userPropertyManager.getPropertySet(applicationUser);
        String values = propertySet.getString("jira.meta.projeler");
        return userPropertyList;
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
