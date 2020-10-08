package com.veniture.condition;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

import java.util.Map;

import static com.veniture.condition.projectApprove.checkIfLoggedInForDepartmanOncelik;

public class department implements Condition {
    ApplicationUser loggedInUser;
    @Override
    public void init(Map<String, String> map) throws PluginParseException {
        loggedInUser=ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
    }

    @Override
    public boolean shouldDisplay(Map<String, Object> map) {
        return checkIfLoggedInForDepartmanOncelik(loggedInUser);
    }
}
