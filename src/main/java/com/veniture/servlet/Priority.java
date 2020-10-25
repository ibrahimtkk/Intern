package com.veniture.servlet;

import com.atlassian.crowd.embedded.impl.ImmutableUser;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.component.pico.ComponentManager;
import com.atlassian.jira.favourites.FavouritesManager;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.issue.search.SearchRequestManager;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.portal.PortalPage;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.ApplicationUsers;
import com.atlassian.jira.user.DelegatingApplicationUser;
import com.atlassian.jira.user.UserUtils;
import com.atlassian.jira.user.util.UserManager;
import com.atlassian.jira.web.action.ProjectActionSupport;
import com.atlassian.jira.web.action.filter.ManageFilters;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.veniture.constants.Constants;
import com.veniture.util.GetCustomFieldsInExcel;
import org.apache.batik.css.engine.value.svg.FilterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scanned
public class Priority extends HttpServlet {

//    @JiraImport
//    private IssueService issueService;
//    @JiraImport
//    private ProjectService projectService;
//    @JiraImport
//    private ConstantsManager constantsManager;
//    @JiraImport
//    private RequestFactory requestFactory;
    @JiraImport
    private  SearchService searchService;
    @JiraImport
    private TemplateRenderer templateRenderer;
    @JiraImport
    private  JiraAuthenticationContext authenticationContext;
    private final Logger logger = LoggerFactory.getLogger(Priority.class);// The transition ID

    private static final String PRIORITIZATION_SCREEN_TEMPLATE = "/templates/prioritization.vm";

    public Priority(   SearchService searchService,
                       TemplateRenderer templateRenderer,
                       JiraAuthenticationContext authenticationContext) {
        this.searchService = searchService;
        this.templateRenderer = templateRenderer;
        this.authenticationContext = authenticationContext;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

/*        UserManager userManager = ComponentAccessor.getUserManager();
        //UserUtils.getUsersByEmail("berkkarabacak2@gmail.com").get(0);
        //ApplicationUser appUser = userManager.getUserByKey("906914");

        StringBuilder stringBuilder;
        GroupManager groupManager = ComponentAccessor.getGroupManager();
        for (ApplicationUser user: userManager.getAllUsers()){
            if (groupManager.getGroupNamesForUser(user).contains("okta")){
                continue;
            }
        }

        for (String i : userss.keySet()) {
            rename(userManager,i,userss.get(i));
        }*/

        Map<String, Object> context = new HashMap<String, Object>();
        String restriction = Optional.ofNullable(req.getParameter("restriction")).orElse("");
        JqlQueryParser jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser.class);
        Query conditionQuery = null;
        try {
            if (restriction.equals("gmy")) {
                try{
                    conditionQuery = jqlQueryParser.parseQuery(Constants.arcelikJQL);
                }
                catch (Exception e){
                    logger.info("JQP PARSE ERROR");
                }
            }

            ApplicationUser applicationUser = authenticationContext.getLoggedInUser();
            ComponentManager componentManager = ComponentManager.getInstance();
            FavouritesManager favouritesManager = (FavouritesManager) componentManager.getComponentInstanceOfType(FavouritesManager.class);
            Collection<Long> favourites = favouritesManager.getFavouriteIds(applicationUser, SearchRequest.ENTITY_TYPE);

            SearchRequestManager searchRequestManager = componentManager.getComponentInstanceOfType(SearchRequestManager.class);
            List<String> favouriteFilters = new ArrayList<>();
            favourites.forEach(favourite -> {
                SearchRequest filter = searchRequestManager.getSharedEntity(favourite);
                String queryString = filter.getQuery().getQueryString();
                String filterName = filter.getName();
                favouriteFilters.add(filterName + " (" + queryString + ")");
//                favouriteFilters.add(getQueryString);
//                favouriteFilterNames.add(getFilterName);
            });

//            conditionQuery = jqlQueryParser.parseQuery(Constants.AS_JQL);

            SearchResults results = null;
            try{
                results = searchService.search(authenticationContext.getLoggedInUser(), conditionQuery, PagerFilter.getUnlimitedFilter());
            }
            catch (Exception e){
                logger.error("JQL error");
            }

            List dueDateList = new ArrayList();
            List createdList = new ArrayList();
            List priorityList = new ArrayList();
            CustomFieldManager customFieldManager=ComponentAccessor.getCustomFieldManager();

            results.getResults().forEach(issue -> {
                Issue issue1 = (Issue) issue;
                try{
                    dueDateList.add(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(issue1.getDueDate()));
                } catch (Exception e){
                    dueDateList.add(null);
                }
                createdList.add(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(issue1.getCreated()));

                CustomField customField = customFieldManager.getCustomFieldObject(10400L);
                Double priorityNumber = (Double) customField.getValue(issue1);
                int intPriorityNumber = priorityNumber.intValue();
                priorityList.add(intPriorityNumber);
            });
            List<CustomField> customFieldsInProject = new GetCustomFieldsInExcel().invoke();


            context.put("issues", results.getResults());
            context.put("issueList", results.getResults());
            context.put("restriction", restriction);
            context.put("baseUrl", ComponentAccessor.getApplicationProperties().getString("jira.baseurl"));
            context.put("Priority", customFieldsInProject.get(0));
            context.put("customFieldsInProject", customFieldsInProject);
            context.put("favouriteFilters", favouriteFilters);
            context.put("birimOncelikCF", ComponentAccessor.getCustomFieldManager().getCustomFieldObject(Constants.BIRIM_ONCELIK_ID_STRING));
            context.put("gmyOncelikCF", ComponentAccessor.getCustomFieldManager().getCustomFieldObject(Constants.GMY_ONCELIK_STRING));
            context.put("dueDateList", dueDateList);
            context.put("createdList", createdList);
            context.put("priorityList", priorityList);

            resp.setContentType("text/html;charset=utf-8");
            templateRenderer.render(PRIORITIZATION_SCREEN_TEMPLATE, context, resp.getWriter());
        } catch (Exception e){

        }

    }

    private List<FavouriteFilter> getFavouriteList(){
        ArrayList<FavouriteFilter> list = new ArrayList<>();
        return list;
    }

/*
    private void rename(UserManager userManager,String mail, String newusername) {
        try {
            ApplicationUser appUser = UserUtils.getUsersByEmail(mail).get(0);
            appUser.getUsername()
            ImmutableUser.Builder builder = ImmutableUser.newUser(Objects.requireNonNull(ApplicationUsers.toDirectoryUser(appUser)));
            builder.name(newusername);
            userManager.updateUser(new DelegatingApplicationUser(appUser.getId(),appUser.getKey(), builder.toUser()));
        } catch (Exception e) {

            logger.error("EROooor = " + mail);
            logger.error(UserUtils.getUsersByEmail(mail).toString());
            logger.error(e.getLocalizedMessage());
        }
    }
*/
}

class FavouriteFilter {
    private String filterName;
    private String query;

    public FavouriteFilter(String filterName, String query) {
        this.filterName = filterName;
        this.query = query;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}