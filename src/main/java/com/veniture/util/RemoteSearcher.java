//package com.veniture.util;
//
//import com.atlassian.sal.api.net.Request;
//import com.atlassian.sal.api.net.RequestFactory;
//import com.atlassian.sal.api.net.ResponseException;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import model.pojo.Day;
//import model.pojo.TempoPlanner.FooterTotalAvailabilityInfos;
//import model.pojo.TempoPlanner.IssueTableData;
//import model.pojo.TempoTeams.Team;
//import model.pojo.Workload;
//import org.apache.commons.httpclient.URIException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.lang.reflect.Type;
//import java.util.Calendar;
//import java.util.List;
//
//import static com.veniture.constants.Constants.*;
//
//public class RemoteSearcher {
//    private final RequestFactory<?> requestFactory;
//    private static final Gson GSON = new GsonBuilder().serializeNulls().create();
//    private static final Logger logger = LoggerFactory.getLogger(RemoteSearcher.class);// The transition ID
//
//    public RemoteSearcher(final RequestFactory<?> requestFactory) {
//        this.requestFactory = requestFactory;
//    }
//
//    public Double getTotalRemainingTimeInYearForTeam(Integer teamId) {
//        String responseString = getResponseForAvailability(teamId);
//        List<FooterTotalAvailabilityInfos> availabilityInfoColumns;
//        availabilityInfoColumns = GSON.fromJson(responseString, IssueTableData.class).getFooter().getColumns();
//        Double totalRemaining;
//        try {
//            totalRemaining = availabilityInfoColumns.stream().map(FooterTotalAvailabilityInfos::getRemaining).reduce(Double::sum).orElse(0.0);
//        } catch (Exception e) {
//            //Buraya giriyorsa takımlarin kapasitesi set edilmemiştir demekttir, o halde kapasiteyi sıfır yap.
//            totalRemaining=0.0;
//        }
//        return totalRemaining;
//
////        return 123;
//    }
//
//    public Double getTotalAllocatedTimeInYearForTeam(Integer teamId) {
//        String responseString = getResponseForAvailability(teamId);
//        List<FooterTotalAvailabilityInfos> availabilityInfoColumns;
//        availabilityInfoColumns = GSON.fromJson(responseString, IssueTableData.class).getFooter().getColumns();
//        Double totalAllocated;
//        try {
//            totalAllocated = availabilityInfoColumns.stream().map(FooterTotalAvailabilityInfos::getTotalAllocated).reduce(Double::sum).orElse(0.0);
//        } catch (Exception e) {
//            //Buraya giriyorsa takımlarin kapasitesi set edilmemiştir demekttir, o halde kapasiteyi sıfır yap.
//            totalAllocated=0.0;
//        }
//        return totalAllocated;
//     //   return 2222;
//    }
//
//    private String getResponseForAvailability(Integer teamId) {
//        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
//        int CurrentMonth = Calendar.getInstance().get(Calendar.MONTH);
//        int CurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        String StartDate = CurrentYear+"-"+(CurrentMonth+1)+"-"+CurrentDay;
//        String EndDate = (CurrentYear) +"-12-30";
//        //String EndDate = (CurrentYear) +"-"+(CurrentMonth+2)+"-30";
//        String QUERY = QUERY_AVAILABILITY_YEAR.replace("XXX", teamId.toString()).replace("YYY", StartDate).replace("ZZZ",EndDate);
//        return getResponseString(QUERY);
//    }
//
////    public List<Integer> getAllTeamIds() throws URIException {
////        Type tempoTeamDataType = new TypeToken<List<model.pojo.Team>>() {}.getType();
////        List<Team> tempoTeamData = GSON.fromJson(getResponseString(Constants.QUERY_TEAM), tempoTeamDataType);
////        List<Integer> ids = tempoTeamData.stream().map(model.pojo.Team::getId).collect(Collectors.toList());
////        return ids;
////    }
//
//    public List<Team> getAllTeams() throws URIException {
//        Type tempoTeamDataType = new TypeToken<List<Team>>() {}.getType();
//        List<Team> tempoTeamData = GSON.fromJson(getResponseString(QUERY_TEAM), tempoTeamDataType);
//        // List<String> names = tempoTeamData.stream().map(Team::getName).collect(Collectors.toList());
//        return tempoTeamData;
//    }
//
////    public Workload test() throws URIException {
////        Type workload = new TypeToken<Workload>() {}.getType();
////        Workload workloadData = GSON.fromJson(getResponseString(QUERY_WORKLOAD), workload);
////        Integer hourly=workloadData.getDays().stream().filter(o -> o.getSeconds() > - 10).mapToInt(Day::getSeconds).sum()/3600/5;
////        return workloadData;
////    }
//
//    public String getResponseString(String Query) {
//        //final String fullUrl = scheme + hostname + URIUtil.encodeWithinQuery(QUERY);
//        String hostname;
//        String scheme;
//        assert JIRA_BASE_URL != null;
//        if (JIRA_BASE_URL.contains("veniture")){
//            hostname= venitureHostname;
//            scheme = schemeHTTPS;
//        }else {
//            hostname= floHostname;
//            scheme = schemeHTTP;
//        }
//
//        final String fullUrl = scheme + hostname + Query;
//        final Request request = requestFactory.createRequest(Request.MethodType.GET, fullUrl);
//        request.addBasicAuthentication(hostname, adminUsername, adminPassword);
//
//        try {
//            return request.execute();
//        } catch (final ResponseException e) {
//            logger.error("Error at request" + e.getMessage() + e.getLocalizedMessage());
//            throw new RuntimeException("Search for " + Query + " on " + fullUrl + " failed.", e);
//        }
//    }
//}


package com.veniture.util;

import com.atlassian.sal.api.net.RequestFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.pojo.TempoPlanner.FooterTotalAvailabilityInfos;
import model.pojo.TempoPlanner.IssueTableData;
import model.pojo.TempoTeams.Team;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;

import static com.veniture.constants.Constants.*;

public class RemoteSearcher {
    private final RequestFactory<?> requestFactory;
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();
    private static final Logger logger = LoggerFactory.getLogger(RemoteSearcher.class);// The transition ID

    public RemoteSearcher(final RequestFactory<?> requestFactory) {
        this.requestFactory = requestFactory;
    }

    public Double getTotalRemainingTimeInYearForTeam(Integer teamId) throws IOException {
        String responseString = getResponseForAvailability(teamId);
        List<FooterTotalAvailabilityInfos> availabilityInfoColumns;
        availabilityInfoColumns = GSON.fromJson(responseString, IssueTableData.class).getFooter().getColumns();
        Double totalRemaining;
        try {
            totalRemaining = availabilityInfoColumns.stream().map(FooterTotalAvailabilityInfos::getRemaining).reduce(Double::sum).orElse(0.0);
        } catch (Exception e) {
            //Buraya giriyorsa takımlarin kapasitesi set edilmemiştir demekttir, o halde kapasiteyi sıfır yap.
            totalRemaining=0.0;
        }
        return totalRemaining;

//        return 123;
    }

    public Double getTotalAllocatedTimeInYearForTeam(Integer teamId) throws IOException {
        String responseString = getResponseForAvailability(teamId);
        List<FooterTotalAvailabilityInfos> availabilityInfoColumns;
        availabilityInfoColumns = GSON.fromJson(responseString, IssueTableData.class).getFooter().getColumns();
        Double totalAllocated;
        try {
            totalAllocated = availabilityInfoColumns.stream().map(FooterTotalAvailabilityInfos::getTotalAllocated).reduce(Double::sum).orElse(0.0);
        } catch (Exception e) {
            //Buraya giriyorsa takımlarin kapasitesi set edilmemiştir demekttir, o halde kapasiteyi sıfır yap.
            totalAllocated=0.0;
        }
        return totalAllocated;
        //   return 2222;
    }

    private String getResponseForAvailability(Integer teamId) throws IOException {
        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        int CurrentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int CurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String StartDate = CurrentYear+"-"+(CurrentMonth+1)+"-"+CurrentDay;
        String EndDate = (CurrentYear) +"-12-30";
        //String EndDate = (CurrentYear) +"-"+(CurrentMonth+2)+"-30";
        String QUERY = QUERY_AVAILABILITY_YEAR.replace("XXX", teamId.toString()).replace("YYY", StartDate).replace("ZZZ",EndDate);
        return getResponseString(QUERY);
    }

//    public List<Integer> getAllTeamIds() throws URIException {
//        Type tempoTeamDataType = new TypeToken<List<model.pojo.Team>>() {}.getType();
//        List<Team> tempoTeamData = GSON.fromJson(getResponseString(Constants.QUERY_TEAM), tempoTeamDataType);
//        List<Integer> ids = tempoTeamData.stream().map(model.pojo.Team::getId).collect(Collectors.toList());
//        return ids;
//    }

    public List<Team> getAllTeams() throws IOException {
        Type tempoTeamDataType = new TypeToken<List<Team>>() {}.getType();
        List<Team> tempoTeamData = GSON.fromJson(getResponseString(QUERY_TEAM), tempoTeamDataType);
        return tempoTeamData;
    }

//    public Workload test() throws URIException {
//        Type workload = new TypeToken<Workload>() {}.getType();
//        Workload workloadData = GSON.fromJson(getResponseString(QUERY_WORKLOAD), workload);
//        Integer hourly=workloadData.getDays().stream().filter(o -> o.getSeconds() > - 10).mapToInt(Day::getSeconds).sum()/3600/5;
//        return workloadData;
//    }

    public String getResponseString(String Query) throws IOException {
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

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity responseEntity = httpResponse.getEntity();
        InputStream inputStream = responseEntity.getContent();
        String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        return response;
    }

//    public String getResponseString(String Query) {
//        //final String fullUrl = scheme + hostname + URIUtil.encodeWithinQuery(QUERY);
//        String hostname;
//        String scheme;
//        assert JIRA_BASE_URL != null;
//        if (JIRA_BASE_URL.contains("veniture")){
//            hostname= venitureHostname;
//            scheme = schemeHTTPS;
//        }
//        else if (JIRA_BASE_URL.contains("localhost")){
//            hostname = JIRA_BASE_URL;
//            scheme = "";
//        }
//        else {
//            hostname= floHostname;
//            scheme = schemeHTTP;
//        }
//
//        String fullUrl = scheme + hostname + Query;
//        fullUrl = "http://localhost:8089/rest/tempo-teams/2/team";
//        Request request = requestFactory.createRequest(Request.MethodType.GET, fullUrl);
//        request.addBasicAuthentication("http://localhost:8089", "ibrahim.takak", "qwerty");
//
//        try{
//            String req = request.execute();
//        } catch (ResponseException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            return request.execute();
//        } catch (final ResponseException e) {
//            logger.error("Error at request" + e.getMessage() + e.getLocalizedMessage());
//            throw new RuntimeException("Search for " + Query + " on " + fullUrl + " failed.", e);
//        }
//    }
}
