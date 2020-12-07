package com.veniture.rest;


import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.UserPropertyManager;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.user.util.UserUtil;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.jira.workflow.TransitionOptions;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;
import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.servicedesk.api.organization.OrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.module.propertyset.PropertySet;
import com.veniture.constants.Constants;
import com.veniture.util.GetCustomFieldsInExcel;
import com.veniture.util.RemoteSearcher;
import com.veniture.util.tableRowBuilder;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import model.DateAndHour;
import model.TableRow;
import model.UserWorkingHours;
import model.UserWorkingStartAndEndDate;
import model.pojo.ProjectAndRole;
import model.pojo.ProjectAndUsers;
import model.pojo.TempoPlanner.Allocation;
import model.pojo.UserAndProject;
import model.pojo.UserAndRoleAndTeam;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.veniture.constants.Constants.*;
import static com.veniture.util.functions.*;

@Path("/rest")
public class rest {
    @JiraImport
    private RequestFactory requestFactory;
//    private ApplicationProperties applicationProperties;
    @JiraImport
    private SearchService searchService;
    @JiraImport
    private JiraAuthenticationContext authenticationContext;
    @JiraImport
    private IssueManager issueManager;
    @JiraImport
    OrganizationService organizationService;

    List<UserWorkingStartAndEndDate> availableUsers = null;
    List<List<UserWorkingStartAndEndDate>> availableUserList = null;
    List<String> suggestionUserName = null;

    Date allocationStartDate = null;
    Date allocationEndDate = null;
    private long resource = 0;
    long fark = 0;
    private static final Logger logger = LoggerFactory.getLogger(rest.class);// The transition ID
//    private static final Gson GSON = new Gson();
    private static final IssueService ISSUE_SERVICE = ComponentAccessor.getIssueService();
    private static final ApplicationUser CURRENT_USER = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

    public rest(RequestFactory requestFactory, SearchService searchService, JiraAuthenticationContext authenticationContext){
        this.requestFactory = requestFactory;
        this.issueManager= ComponentAccessor.getIssueManager();
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
    }

    @POST
    @Path("/getCfValueFromIssue")
    public String getCfValueFromIssue(@Context HttpServletRequest req, @Context HttpServletResponse resp) {
        CustomField customField= ComponentAccessor.getCustomFieldManager().getCustomFieldObject(req.getParameterValues("customFieldId")[0]);
        return ISSUE_SERVICE.getIssue(CURRENT_USER,req.getParameterValues("issueKey")[0]).getIssue().getCustomFieldValue(customField).toString();
    }

    @POST
    @Path("/favFilterJQL")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getFavFilter(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws JqlParseException, SearchException {
        List jqlList = new ArrayList();
        JqlQueryParser jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser.class);
        String JQLquery = getQueryFromRequest(req.getParameterValues("jqlQuery")[0]);
        Query conditionQuery = jqlQueryParser.parseQuery(JQLquery);
        SearchResults<Issue> results = searchService.search(authenticationContext.getLoggedInUser(), conditionQuery, PagerFilter.getUnlimitedFilter());
        List<Issue> resultsList =  results.getResults();
        List<String> issueKeys = new ArrayList<>();
        resultsList.forEach(issue -> {
            issue = (Issue) issue;
            issueKeys.add(issue.getKey());
        });
        return issueKeys;
    }

//    @POST
//    @Path("/getProjectIssues")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getProjectIssues(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws SearchException, JqlParseException, JSONException {
//        SearchResults<Issue> IssueResults = getIssueSearchResults(authenticationContext,searchService);
//        List<CustomField> customFieldsInProject = new GetCustomFieldsInExcel().invoke();
//        List<TableRow> tableRows = new tableRowBuilder(ComponentAccessor.getIssueManager(), logger,IssueResults, customFieldsInProject).invoke();
//        JSONArray jsonArray=  new JSONArray();
//
//        for (TableRow tableRow : tableRows){
//            JSONObject tableRowJson = tableRow.toJSON();
////          tableRowJson = addEforCfsToJson(tableRow, tableRowJson);
//            jsonArray.put(tableRowJson);
//        }
//
//        String result = jsonArray.toString();
//        return Response.status(201).entity(result).build();
//    }


    @POST
    @Path("/suggestUserFromProjects")
    public String suggestUserFromIssueKey(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
        String[] projects = req.getParameterValues("projects[]");
        List<String> projectList = Arrays.asList(projects);
        String startDateString = req.getParameterValues("baslangicTarihiProject")[0];
        String endDateString = req.getParameterValues("bitisTarihiProject")[0];
        Date constraintStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
        Date constraintEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);

        availableUserList = new ArrayList<>();

        List<Allocation> allocations = getAllocationsByDate(startDateString, endDateString);
        List<UserAndRoleAndTeam> usersRolesAndTeams = getUsersRolesAndTeams(projectList);
        List<UserAndProject> userAndProjects = getUsersAndProjects(allocations);
        List<ProjectAndRole> allProjectAndRoles = getProjectsAndRoles(userAndProjects, usersRolesAndTeams);
        List<ProjectAndRole> wantedProjectsAndRoles = getWantedProjectAndRoles(projectList, allProjectAndRoles);
        List<ProjectAndUsers> projectsAndUsersByRole = getProjectsAndUsersByRole(wantedProjectsAndRoles, usersRolesAndTeams);
//        List<String> suggestionUserName = projectsAndUsersByRole.get(0).getUsers();
//        List<String> suggestionUserName = getHaveAuthorizedUsers(projectList, allocations);


        projectsAndUsersByRole.forEach(projectAndUsers -> {
            suggestionUserName = projectAndUsers.getUsers();
            try {
                List<UserWorkingHours> usersAndWorkingHour = getWorkingHoursOfUsers(allocations, suggestionUserName);
                List<List<DateAndHour>> totalWorkingHoursOfUsers = getTotalWorkingHoursOfUsers(usersAndWorkingHour, suggestionUserName);
                List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList = getUserWorkingStartAndEndDateList(usersAndWorkingHour);
                logger.info("availableUser: ", availableUsers);
                availableUsers = checkWorkingTimeAndSuggestAnotherUser(totalWorkingHoursOfUsers, userWorkingStartAndEndDateList, suggestionUserName, projectAndUsers.getProjectKey());
                availableUserList.add(availableUsers);
                logger.info("111");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        for (String i:suggestionUserName){
            String name = i;
        }
        String json = new Gson().toJson(availableUserList);

//        issueKeys.forEach(issueKey -> {
//            try {
//                List<UserWorkingHours> usersAndWorkingHour = getWorkingHoursOfUsers(allocations, suggestionUserName);
//                List<List<DateAndHour>> totalWorkingHoursOfUsers = getTotalWorkingHoursOfUsers(usersAndWorkingHour, suggestionUserName);
//                List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList = getUserWorkingStartAndEndDateList(usersAndWorkingHour);
//                availableUsers = checkWorkingTimeAndSuggestAnotherUser(totalWorkingHoursOfUsers, userWorkingStartAndEndDateList, suggestionUserName, issueKey);
//                logger.info("111");
//            } catch (IOException | URISyntaxException e) {
//                e.printStackTrace();
//            }
//        });
//        for (String i:suggestionUserName){
//            String name = i;
//        }
//        String json = new Gson().toJson(availableUsers );

        return json;
    }


    @POST
    @Path("/bulkGetCfValueFromIssue")
    public String bulkGetCfValueFromIssue(@Context HttpServletRequest req, @Context HttpServletResponse resp) {
        CustomField customField= ComponentAccessor.getCustomFieldManager().getCustomFieldObject(req.getParameterValues("customFieldId")[0]);
        String issueKeysJoined = req.getParameterValues("issueKey")[0];
        String[] issueKeys = issueKeysJoined.split(",");
        List<String> cfValues = new ArrayList<>();
        for (String issueKey:issueKeys){
            String cfValue;
            String[] cfValueArr;
            try {
                Issue issue= ISSUE_SERVICE.getIssue(CURRENT_USER,issueKey).getIssue();
                if (customField.getCustomFieldType().getName().equals("User Picker (single user)")){
                    ApplicationUser applicationUser=(ApplicationUser) issue.getCustomFieldValue(customField);
                    cfValue=applicationUser.getDisplayName();
                }else {
                    cfValue = issue.getCustomFieldValue(customField).toString();

                    if (customField.getName().equals("Başlangıç Tarihi") || customField.getName().equals("Bitiş Tarihi")){
                        cfValueArr = cfValue.split(" ");
                        cfValue = cfValueArr[0];
                    }

                }
            } catch (Exception e) {
                cfValue = "-";
//              This means Could not get CF value for this CF
            }
            cfValues.add(cfValue);
        }
        return String.join(",,,", cfValues);
    }

    @POST
    @Path("/setPriorityCfValuesInJira")
    public String setPriorityCfValuesInJira(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws Exception {
        String[] jsonTable = req.getParameterValues("jsontable");
        String gmyOrBirim = null;
        try {
            gmyOrBirim = req.getParameterValues("gmyOrBirim")[0];
        } catch (Exception e) {
            gmyOrBirim = "";
            logger.error("gmyOrBirim parameter is null");
        }
        //logger.debug("jsonTable  " +jsonTable[0]);
        JSONArray tableAsJSONarray = jsonString2JsonArray(jsonTable[0]);
        ObjectMapper mapper = new ObjectMapper();

        //logger.debug(tableAsJSONarray.toString());
        int failTryCount=4;
        for (int i = 0; i < tableAsJSONarray.length(); i++) {
            try {
                parseJsonAndSetPriorityCFs(gmyOrBirim, tableAsJSONarray, mapper, i);
            } catch (Exception e) {
                failTryCount--;
                if (failTryCount>0){
                    parseJsonAndSetPriorityCFs(gmyOrBirim, tableAsJSONarray, mapper, i);
                }
                logger.error("JSONObject'i POJO'ya çevirirken hata oldu: " +e.getMessage());
            }
        }
        return null;
    }


    @POST
    @Path("/sepetikaydet")
    public String sepetikaydet(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws Exception {
        String[] issueHtml = req.getParameterValues("issues");
        IssueService issueService = ComponentAccessor.getIssueService();
        if (issueHtml!=null){
            ArrayList<String> issues = (ArrayList<String>) Arrays.stream(issueHtml)
                    .map(element -> element.substring(element.indexOf(">")+1,element.indexOf("<",7)))
                    .collect(Collectors.toList());
            ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
            issues.stream()
                    .forEach(issue->setCustomFieldValue(issueService.getIssue(currentUser, issue).getIssue().getKey(), Constants.isSelectedCfId,"true"));
        }

        String[] NotSelectedissueHtml = req.getParameterValues("issuesNotSelected");
        if (NotSelectedissueHtml!=null){
            ArrayList<String> NotSelectedissues = (ArrayList<String>) Arrays.stream(NotSelectedissueHtml)
                    .map(element -> element.substring(element.indexOf(">")+1,element.indexOf("<",7)))
                    .collect(Collectors.toList());
            ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
            NotSelectedissues.stream()
                    .forEach(issue->setCustomFieldValue(issueService.getIssue(currentUser, issue).getIssue().getKey(), Constants.isSelectedCfId,"false"));
        }
        return null;
    }

    @POST
    @Path("/calculateResource")
    public String calculateResource(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
        Map map = req.getParameterMap();
        String[] issueKeysArray = req.getParameterValues("selectedRowKeys[]");
        String startDateString = req.getParameterValues("startDate")[0];
        String endDateString = req.getParameterValues("endDate")[0];
        String resourceString = req.getParameterValues("resource")[0];
        List<Allocation> allocations = getAllocationsByDate(startDateString, endDateString);
        List<String> issueKeys = Arrays.asList(issueKeysArray);
        Date constraintStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
        Date constraintEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
        int constraintResource = Integer.parseInt(resourceString);
        resource = 0;

        allocations.forEach(allocation1 -> {
            Allocation allocation = allocation1;
            if ( issueKeys.contains( allocation.getPlanItem().getKey() )){
                try {
                    allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getStart());
                    allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getEnd());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                fark = getWorkingDaysBetweenTwoDate(constraintStartDate, constraintEndDate, allocationStartDate, allocationEndDate);
                resource += fark * allocation.getSecondsPerDay()/3600;

            }
        });
        String kaynak = String.valueOf(resource);


        List<String> suggestionUserName = getHaveSameProjectUsers(issueKeys);

        issueKeys.forEach(issueKey -> {
            try {
                List<UserWorkingHours> usersAndWorkingHour = getWorkingHoursOfUsers(allocations, suggestionUserName);
                List<List<DateAndHour>> totalWorkingHoursOfUsers = getTotalWorkingHoursOfUsers(usersAndWorkingHour, suggestionUserName);
                List<UserWorkingStartAndEndDate> userWorkingStartAndEndDateList = getUserWorkingStartAndEndDateList(usersAndWorkingHour);
                availableUsers = checkWorkingTimeAndSuggestAnotherUser(totalWorkingHoursOfUsers, userWorkingStartAndEndDateList, suggestionUserName, issueKey);
                logger.info("111");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        for (String i:suggestionUserName){
            String name = i;
        }
        String json = new Gson().toJson(availableUsers );

        return kaynak;
    }

//    @POST
//    @Path("/resourceOverload")
//    public String resourceOverload(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
//        Map map = req.getParameterMap();
//        String[] issueKeysArray = req.getParameterValues("selectedRowKeys[]");
//        String startDateString = req.getParameterValues("startDate")[0];
//        String endDateString = req.getParameterValues("endDate")[0];
//        String resourceString = req.getParameterValues("resource")[0];
//        List<Allocation> allocations = getAllocationsByDate(startDateString, endDateString);
//
//        List<String> issueKeys = Arrays.asList(issueKeysArray);
//        Date constraintStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
//        Date constraintEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
//        int constraintResource = Integer.parseInt(resourceString);
//        resource = 0;
//
//        List<String> userNames = new ArrayList<>();
//        allocations.forEach(allocation1 -> {
//            Allocation allocation = allocation1;
//            if ( issueKeys.contains( allocation.getPlanItem().getKey())){
//                try {
//                    allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getStart());
//                    allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getEnd());
//
//                    List<Allocation> allocation2 = getAllocationsByDateAndAssigneeKey(allocation.getStart(), allocation.getEnd(), allocation.getAssignee().getKey());
//                    allocation2.forEach(al2->{
//                        Allocation all2 = al2;
//                        try{
//                            allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getStart());
//                            allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getEnd());
//                        }catch  (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        fark = getWorkingDaysBetweenTwoDate(constraintStartDate, constraintEndDate, allocationStartDate, allocationEndDate);
//                        resource += all2.getSecondsPerDay()/3600;
//
//                        if(resource > constraintResource){
//                            //kaynak aşımı oluştu
//                            userNames.add(all2.getAssignee().getKey() + ","+ allocation.getPlanItem().getKey()+","+allProjectsUser );
//                        }
//                    });
//                    resource=0;
//                } catch (ParseException | IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        String names = userNames.stream().collect(Collectors.joining(" "));
//        return names;
//    }

//    @POST
//    @Path("/resourceOverloadProjectPlanning")
//    public String resourceOverloadProjectPlanning(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
//        Map map = req.getParameterMap();
//        String[] issueKeysArray = req.getParameterValues("projectKey[]");
//        String startDateString = req.getParameterValues("startDate")[0];
//        String endDateString = req.getParameterValues("endDate")[0];
//        String resourceString = req.getParameterValues("resource")[0];
//        List<Allocation> allocations = getAllocationsByDate(startDateString, endDateString);
//
//        List<String> issueKeys = Arrays.asList(issueKeysArray);
//        Date constraintStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
//        Date constraintEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
//        int constraintResource = Integer.parseInt(resourceString);
//        resource = 0;
//
//        List<String> userNames = new ArrayList<>();
//        allocations.forEach(allocation1 -> {
//            Allocation allocation = allocation1;
//            if ( issueKeys.contains( allocation.getPlanItem().getKey())){
//                try {
//                    allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getStart());
//                    allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getEnd());
//
//                    List<Allocation> allocation2 = getAllocationsByDateAndAssigneeKey(allocation.getStart(), allocation.getEnd(), allocation.getAssignee().getKey());
//                    allocation2.forEach(al2->{
//                        Allocation all2 = al2;
//                        try{
//                            allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getStart());
//                            allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getEnd());
//                        }catch  (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        fark = getWorkingDaysBetweenTwoDate(constraintStartDate, constraintEndDate, allocationStartDate, allocationEndDate);
//                        resource += all2.getSecondsPerDay()/3600;
//
//
//                        if(resource > constraintResource){
//                            //kaynak aşımı oluştu
//                            userNames.add(all2.getAssignee().getKey() + ","+ allocation.getPlanItem().getKey() );
//                        }
//                    });
//                    resource=0;
//                } catch (ParseException | IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        String names = userNames.stream().collect(Collectors.joining(" "));
//        return names;
//    }

    @POST
    @Path("/resourceOverloadProjectPlanning")
    public String resourceOverloadProjectPlanning(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
        Map map = req.getParameterMap();
        String[] issueKeysArray = req.getParameterValues("projectKey[]");
        String startDateString = req.getParameterValues("startDate")[0];
        String endDateString = req.getParameterValues("endDate")[0];
        String resourceString = req.getParameterValues("resource")[0];
        List<Allocation> allocations = getAllocationsByDate(startDateString, endDateString);

        List<String> issueKeys = Arrays.asList(issueKeysArray);
        Date constraintStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
        Date constraintEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
        int constraintResource = Integer.parseInt(resourceString);
        resource = 0;

        List<String> userNames = new ArrayList<>();
        allocations.forEach(allocation1 -> {
            Allocation allocation = allocation1;
            if ( issueKeys.contains( allocation.getPlanItem().getKey())){
                try {
                    allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getStart());
                    allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation.getEnd());

                    List<Allocation> allocationJustUser = getAllocationsByDateAndAssigneeKey(startDateString ,endDateString, allocation.getAssignee().getKey());
                    String allProjectsUser = getUsersProjects(allocationJustUser);
                    List<Allocation> allocation3 = getAllocationsByDateAndAssigneeKey(allocation.getStart(), allocation.getEnd(), allocation.getAssignee().getKey());
                    List<Allocation> allocation2 = new ArrayList<>();
                    allocation3.forEach(allocation4 -> {
                        if (allocation4.getAssignee().getKey().equals(allocation.getAssignee().getKey()))
                            allocation2.add(allocation4);
                    });
                    allocation2.forEach(al2->{
                        Allocation all2 = al2;
                        try{
                            allocationStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getStart());
                            allocationEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(all2.getEnd());
                        }catch  (ParseException e) {
                            e.printStackTrace();
                        }
                        fark = getWorkingDaysBetweenTwoDate(constraintStartDate, constraintEndDate, allocationStartDate, allocationEndDate);
                        resource += all2.getSecondsPerDay()/3600;

                        if(resource > constraintResource){
                            //kaynak aşımı oluştu
                            userNames.add(all2.getAssignee().getKey() + ","+ allocation.getPlanItem().getKey()+","+allProjectsUser );
//                            if(!userNames.contains(all2.getAssignee().getKey() +","+allProjectsUser)){
//                                userNames.add(all2.getAssignee().getKey() +","+allProjectsUser);
//                            }

                        }
                    });
                    resource=0;
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }

            }
        });

        String names = userNames.stream().collect(Collectors.joining(",,,"));
        return names;
    }

//    public String getUsersProjects(List<Allocation> allocation){
//        List<String> projects = new ArrayList<>();
//        allocation.forEach(al->{
//            Allocation all2 = al;
//            try{
//                projects.add(al.getPlanItem().getKey());
//            }catch  (Exception e) {
//                e.printStackTrace();
//            }
//
//        });
//        String project = projects.stream().collect(Collectors.joining(" - "));
//        return project;
//    }

    public String getUsersProjects(List<Allocation> allocation){
        List<String> projects = new ArrayList<>();
        allocation.forEach(al->{
            Allocation all2 = al;
            try{
                projects.add(al.getPlanItem().getName());
            }catch  (Exception e) {
                e.printStackTrace();
            }

        });
        String project = projects.stream().collect(Collectors.joining(" - "));
        return project;
    }


    @POST
    @Path("/assignUserToTempo")
    public String assignUserToTempo(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws IOException, ParseException {
        String issue = req.getParameterValues("issue")[0];

        return "";
    }

    public static final String REST_SERVICEDESKAPI_ORGANIZATION = "https://crm.turkishtechnic.com/rest/servicedeskapi/organization";
    public static final String REST_SERVICEDESKAPI_SERVICEDESK_PROJECT_ORGANIZATION = "https://crm.turkishtechnic.com/rest/servicedeskapi/servicedesk/1/organization";
    public static final String Authorization = "Basic TV9BWUFMOk1hQDIwMjA=";

    private static HttpResponse<String> sendReq() {
        HttpResponse<String> response = null;
        try {
            Unirest.config().verifySsl(false);
            String s = "{\r\n    \"name\": \"XXX\"\r\n}";
//            s = s.replace("XXX",asd);
            response = Unirest.get("https://crm.turkishtechnic.com/rest/servicedeskapi/organization")
                    .header("X-ExperimentalApi", "opt-in")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic TV9BWUFMOk1hQDIwMjA=")
                    .asString();

//          .body(s)

            return response;
        } catch (UnirestException e) {
            System.out.println("error" + e.getMessage());
            e.printStackTrace();
        return null;

        }
    }
    private String getQueryFromRequest(String request){
        String req = request;
        int firstIndex = req.indexOf("(");
        int lastIndex = req.lastIndexOf(")");
        String query = req.substring(firstIndex+1, lastIndex);
        return query;
    }


    private void parseJsonAndSetPriorityCFs(String gmyOrBirim, JSONArray tableAsJSONarray, ObjectMapper mapper, int i) throws Exception {
        JSONObject jsonObj = tableAsJSONarray.getJSONObject(i);
        //ProjectsDetails projectsDetails = mapper.readValue(jsonObj.toString(),ProjectsDetails.class);
        //MutableIssue issue = ISSUE_SERVICE.getIssue(CURRENT_USER, jsonObj.getString("key")).getIssue();
        MutableIssue issue = issueManager.getIssueByKeyIgnoreCase(jsonObj.getString("key"));

        if (issue==null){
            logger.error("CANNOT GET ISSUE "+jsonObj.getString("key")+" CRITICAL ERROR");
            throw new Exception();
        }

        if(gmyOrBirim.equalsIgnoreCase("gmy")){
            try{
//                updateCustomFieldValue(issue, Constants.GMY_ONCELIK_ID,Double.valueOf(jsonObj.getString("$cf.getId()")),CURRENT_USER);
                updateCustomFieldValue(issue, Constants.PriorityNumber, Double.valueOf(i+1), CURRENT_USER);
//                updateCfValueForSelectList(issue,Constants.genelOnceliklendirildiMiId, Constants.GENEL_TRUE_OPTION_ID_CanliVeniture,CURRENT_USER);
            }
            catch (Exception e){
                logger.info("1234");
            }


            logger.info("123");
        }
        else if (gmyOrBirim.equalsIgnoreCase("dp")){
            if (jsonObj.getString("DP")==null){
                logger.error("DP priority is null");
                throw new Exception();
            }
            updateCustomFieldValue(issue,Constants.BIRIM_ONCELIK_ID,Double.valueOf(jsonObj.getString("DP")),CURRENT_USER);
            updateCfValueForSelectList(issue,Constants.onceliklendirildiMiId, Constants.TRUE_OPTION_ID_CanliVeniture,CURRENT_USER);
        }
        else {
            logger.error("Neither gmy nor dp restriction has been set");
        }
        logger.info("123");
    }

//    @GET
//    @Path("/test")
//    public String test() throws URIException {
//        RemoteSearcher remoteSearcher =  new RemoteSearcher(requestFactory);
//        return remoteSearcher.getTotalRemainingTimeInYearForTeam(7).toString();
//    }

    private JSONArray jsonString2JsonArray(String responseString) {
        try {
            JSONArray jsonArr = new JSONArray(responseString);
            return jsonArr;
        } catch (JSONException e) {
            return null;
        }
//        JsonParser parser = new JsonParser();
//        JsonElement jsonElement = parser.parse(responseString);
//        JsonArray jsonArray = jsonElement.getAsJsonArray();
//        return jsonArray;
    }

    private void transitionIssue(IssueService issueService, ApplicationUser currentUser, Issue issue, Integer workflowTransitionId) {
        try {
            final TransitionOptions transitionOptions = new TransitionOptions.Builder().skipPermissions().skipValidators().setAutomaticTransition().skipConditions().build();
            IssueService.TransitionValidationResult result = issueService.validateTransition(currentUser,
                    issue.getId(),
                    workflowTransitionId,
                    issueService.newIssueInputParameters(),
                    transitionOptions);
            if (result.isValid()) {
                issueService.transition(currentUser, result);
                logger.warn("Issue transition is successful");

            } else {
                logger.error(result.getErrorCollection().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("CANNOT TRANSITION ISSUE " + issue.getKey() + issue.getId() + "with workflow transiiton id = " + workflowTransitionId);
        }
    }
}