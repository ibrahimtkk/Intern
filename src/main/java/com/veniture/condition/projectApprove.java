package com.veniture.condition;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

import java.util.Map;

public class projectApprove implements Condition {
    private ApplicationUser loggedInUser;
    @Override
    public void init(Map<String, String> map) throws PluginParseException {
        //ComponentAccessor.getJiraAuthenticationContext().clearLoggedInUser();
        loggedInUser=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        //Bunuda kullan : isLoggedInUser()
    }

    @Override
    public boolean shouldDisplay(Map<String, Object> map) {
        return checkIfLoggedInForProjectApprove(loggedInUser);
    }

    static boolean checkIfLoggedInForProjectApprove(ApplicationUser loggedInUser) {
        if (!ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
            return false;
        }
        else {
            boolean exceptionUsers = loggedInUser.getName().contains("ongul") || loggedInUser.getName().contains("enit") || loggedInUser.getName().contains("eyhan") ;
            return exceptionUsers ;
        }
    }
    static boolean checkIfLoggedInForDepartmanOncelik(ApplicationUser loggedInUser) {
        if (!ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
            return false;
        }
        else {
            boolean exceptionUsers = loggedInUser.getName().contains("ongul") || loggedInUser.getName().contains("enit") || loggedInUser.getName().contains("eyhan") ;
            return ComponentAccessor.getGroupManager().getGroupNamesForUser(loggedInUser).contains("Direktörler") || exceptionUsers ;
        }
    }
}
