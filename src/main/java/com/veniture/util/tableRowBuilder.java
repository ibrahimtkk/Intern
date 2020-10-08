package com.veniture.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.search.SearchResults;
import model.CfWithValue;
import model.TableRow;
import org.slf4j.Logger;
import sun.awt.AWTAccessor;

import java.util.ArrayList;
import java.util.List;

import static com.veniture.constants.Constants.isTest;
import static com.veniture.util.functions.getCustomFieldValueFromIssue;

public class tableRowBuilder {
    private SearchResults<Issue> results;
    private List<CustomField> customFieldsInProject;
    private IssueManager issueManager;
    private Logger logger;

    public tableRowBuilder(IssueManager issueManager, Logger logger, SearchResults<Issue> results, List<CustomField> customFieldsInProject) {
        this.results = results;
        this.customFieldsInProject = customFieldsInProject;
        this.issueManager=issueManager;
        this.logger=logger;
    }

    public List<TableRow> invoke() {
        List<TableRow> tableRows = new ArrayList<>();
//        for (Issue issue : results.getResults()) {
        List<Issue> issues;
        if (isTest) {
            //issues = results.getResults().subList(1, 10);
            issues = results.getResults();
        } else {
            issues = results.getResults();
        }

        CustomFieldManager customFieldManager = ComponentAccessor.getCustomFieldManager();
        for (Issue issue : issues) {
            MutableIssue issueFull = issueManager.getIssueByKeyIgnoreCase(issue.getKey());
            ArrayList<CfWithValue> customFieldsWithValues= new ArrayList<>();
            for (CustomField customField:customFieldsInProject){
                try{
                    customFieldsWithValues.add(new CfWithValue(customField,getCustomFieldValueFromIssue(issueFull,customField.getIdAsLong(),customFieldManager)));
                }
                catch (Exception e){
                    customFieldsWithValues.add(new CfWithValue(customField," "));
                    // Bu satırı commentledim,çünkü çok fazla log basiyor. Log dosyasini çöp yaptı.
                    // Her boş cf için log atiyor sanırım.... logger.error("Error at getIssueWithCFS= " +e.getMessage());
                }
            }

            TableRow tableRow =new TableRow(issueFull,customFieldsWithValues);
            tableRows.add(tableRow);
        }
//        tableRows = setParametersForSlider(tableRows, issueFull, customFieldsWithValues);

        return tableRows;
    }

//    private List<TableRow> setParametersForSlider(List<TableRow> tableRows, MutableIssue issueFull, TableRow tableRow) {
//        setDepartmanOnceligi(issueFull, tableRow);
//        setGmyOnceligi(issueFull, tableRow);
//        tableRow.setProjeYili(2019);
//        tableRows.add(tableRow);
//        return tableRows;
//    }

//    private void setDepartmanOnceligi(MutableIssue issueFull, TableRow tableRow) {
//        int departmanOnceligi = 0;
//        try {
//            departmanOnceligi = Integer.parseInt(getCustomFieldValueFromIssue(issueFull, 11403L));
//        } catch (Exception e) {
//            logger.error("Cannot get and set  departmanOnceligi ");
//            e.printStackTrace();
//        }
//        tableRow.setDepartmanOnceligi(departmanOnceligi);
//    }
//
//    private void setGmyOnceligi(MutableIssue issueFull, TableRow tableRow) {
//        int gmyOnceligi = 0;
//        try {
//            gmyOnceligi = Integer.parseInt(getCustomFieldValueFromIssue(issueFull, 11501L));
//        } catch (Exception e) {
//            logger.error("Cannot get and set gmyOnceligi ");
//        }
//        tableRow.setGmyOnceligi(gmyOnceligi);
//    }
}
