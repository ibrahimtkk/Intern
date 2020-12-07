package com.veniture.servlet;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.component.pico.ComponentManager;
import com.atlassian.jira.favourites.FavouritesManager;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.search.SearchRequest;
import com.atlassian.jira.issue.search.SearchRequestManager;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;
import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.veniture.constants.Constants;
import com.veniture.util.GetCustomFieldsInExcel;
import com.veniture.util.PersonalAvailabilityTimes;
import com.veniture.util.TeamsWithAvailabilityTimes;
import model.pojo.TempoPlanner.Allocation;
import model.pojo.TempoTeams.Team;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @JiraImport
    public RequestFactory requestFactory;
    private final Logger logger = LoggerFactory.getLogger(Priority.class);// The transition ID

    private static final String PRIORITIZATION_SCREEN_TEMPLATE = "/templates/prioritization.vm";

    public Priority(   SearchService searchService,
                       TemplateRenderer templateRenderer,
                       JiraAuthenticationContext authenticationContext,
                       RequestFactory requestFactory) {
        this.searchService = searchService;
        this.templateRenderer = templateRenderer;
        this.authenticationContext = authenticationContext;
        this.requestFactory = requestFactory;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
            });

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

            results.getResults().forEach(issue1 -> {
                Issue issue = (Issue) issue1;
                try{
                    dueDateList.add(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(issue.getDueDate()));
                } catch (Exception e){
                    dueDateList.add(null);
                }
                createdList.add(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(issue.getCreated()));

                CustomField customField = customFieldManager.getCustomFieldObject(Constants.PriorityNumber);
                Double priorityNumber = (Double) customField.getValue(issue);
                int intPriorityNumber = priorityNumber.intValue();
                priorityList.add(intPriorityNumber);

                Project project = issue.getProjectObject();
                ApplicationUser projectLead = project.getProjectLead();

                // Proje Lideri
                customField = customFieldManager.getCustomFieldObject(Constants.ProjeLideri);
                ModifiedValue modifiedValue = new ModifiedValue(customField.getValue(issue), projectLead.getDisplayName());
                customField.updateValue(null, issue, modifiedValue, new DefaultIssueChangeHolder());

                //Proje Ismi
                customField = customFieldManager.getCustomFieldObject(Constants.ProjeIsmi);
                modifiedValue = new ModifiedValue(customField.getValue(issue), project.getName());
                customField.updateValue(null, issue, modifiedValue, new DefaultIssueChangeHolder());
            });
            List<CustomField> customFieldsInProject = new GetCustomFieldsInExcel().invoke();
            List<Issue> issueList = results.getResults();

            String fullUrl = "http://localhost:8080/rest/tempo-teams/2/team";
//            Request request = requestFactory.createRequest(Request.MethodType.GET, fullUrl);
//            request.addBasicAuthentication("http://localhost:8089", "ibrahim.takak", "qwerty");
//
//            try{
//                String reqs = request.execute();
//            } catch (ResponseException e) {
//                e.printStackTrace();
//            }


//            List<Team> teams = new TeamsWithAvailabilityTimes(logger, requestFactory).invoke();
            List<Allocation> allocations = new PersonalAvailabilityTimes(logger, requestFactory).invoke();

            ProjectManager projectKeys = ComponentAccessor.getProjectManager();
            List<Project> projectList = projectKeys.getProjects();
            List<String> projectKeysList = new ArrayList<>();
            projectList.forEach(projectKey -> {
                Project project = projectKey;
                projectKeysList.add(project.getKey());
            });

            context.put("projectKeysList", projectKeysList);
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