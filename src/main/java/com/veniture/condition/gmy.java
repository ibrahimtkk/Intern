package com.veniture.condition;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

import java.util.Map;

public class gmy implements Condition {
    ApplicationUser loggedInUser;
    @Override
    public void init(Map<String, String> map) throws PluginParseException {
        loggedInUser=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

    }

    @Override
    public boolean shouldDisplay(Map<String, Object> map) {

        if (!ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser()){
            return false;
        }
        else {
            Boolean exceptionss = loggedInUser.getName().contains("ongul") || loggedInUser.getName().contains("enit") || loggedInUser.getName().contains("eyhan");
            return ComponentAccessor.getGroupManager().getGroupNamesForUser(loggedInUser).contains("GMY") || exceptionss;
        }
    }
}
