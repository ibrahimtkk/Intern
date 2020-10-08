package com.veniture.servlet;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.issue.*;
import com.atlassian.jira.issue.customfields.option.Options;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.query.Query;
import com.atlassian.sal.api.net.RequestFactory;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.veniture.util.team2Program;
import com.veniture.util.GetCustomFieldsInExcel;
import com.veniture.util.ProgramEforCfs;
import com.veniture.util.TeamsWithAvailabilityTimes;
import model.pojo.CfWithOptions;
import model.pojo.TempoTeams.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.veniture.constants.Constants.*;

@Scanned
public class ProjectApprove extends HttpServlet {

    @JiraImport
    public IssueManager issueManager;
//    @JiraImport
//    private ProjectService projectService;
    @JiraImport
    private SearchService searchService;
    @JiraImport
    private TemplateRenderer templateRenderer;
    @JiraImport
    private JiraAuthenticationContext authenticationContext;
    @JiraImport
    private ConstantsManager constantsManager;
    @JiraImport
    public RequestFactory requestFactory;
    private String action;

    private static final String LIST_ISSUES_TEMPLATE = "/templates/projectApprove.vm";
    public static final Logger logger = LoggerFactory.getLogger(ProjectApprove.class);

    public ProjectApprove(IssueManager issueManager,
                          SearchService searchService,
                          TemplateRenderer templateRenderer,
                          JiraAuthenticationContext authenticationContext,
                          ConstantsManager constantsManager,
                          RequestFactory requestFactory) {
        this.issueManager = issueManager;
        this.searchService = searchService;
        this.authenticationContext = authenticationContext;
        this.templateRenderer = templateRenderer;
        this.constantsManager = constantsManager;
        this.requestFactory = requestFactory;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        action = Optional.ofNullable(req.getParameter("action")).orElse("");
        Map<String, Object> context = null;

        try {
            context = createContext();
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
        }
        resp.setContentType("text/html;charset=utf-8");
        templateRenderer.render(LIST_ISSUES_TEMPLATE, context, resp.getWriter());
    }

    private Map<String, Object> createContext() throws Exception {

        Map<String, Object> context = new HashMap<>();
       // Query projectApproveQuery = ComponentAccessor.getComponent(JqlQueryParser.class).parseQuery(DEVORTAMI_TEST_SORGUSU);
       // SearchResults<Issue> IssueResults = getIssueSearchResults(authenticationContext,searchService);
        List<CustomField> customFieldsInProjectApproveCfPicker = new GetCustomFieldsInExcel().getCfsForProjectApproveCfPicker();
       //context.put("issuesWithCF", new tableRowBuilder(issueManager,logger, IssueResults, customFieldsInProject).invoke());
        context.put("customFieldsInProject", customFieldsInProjectApproveCfPicker);
        context.put("baseUrl", JIRA_BASE_URL);
//        context.put("teams", teams);
        context = new ProgramEforCfs(context).invoke();
        List<Team> teams= new TeamsWithAvailabilityTimes(logger,requestFactory).invoke();
        context = new team2Program(context, teams).invoke();
        //context.put("programs", programsWithCapacities);
        //context.put("projectCFs",getCustomFieldsInProject(Constants.ProjectId));
        addCfOptionsToContext(context);//ProjeEtikleri
        addDepartmanOptionsToContext(context);//ProjeEtikleri
        return context;
    }

    private Map<String, Object> addCfOptionsToContext(Map<String, Object> context) {
        ArrayList<Long> cfIds = new ArrayList<>(Arrays.asList(SureclerManuelYuruyorMuCF, projeEtikleriCfId, departmanCfId,gmyOnceligiCF,maliyetBaremiCfId,satisBaremiCfId, projeKategoriCF,projeYiliCf,araProjemiCf));
        ArrayList<CfWithOptions> cfs = new ArrayList<>();

        for (Long cfId:cfIds){
            CustomField cf= ComponentAccessor.getCustomFieldManager().getCustomFieldObject(cfId);
            assert cf != null;
            //FieldConfig oneAndOnlyConfig = cf.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig().getConfigItems().get(1).getFieldConfig();
            FieldConfig oneAndOnlyConfig = cf.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig();
            Options options = ComponentAccessor.getOptionsManager().getOptions(oneAndOnlyConfig);
            cfs.add(new CfWithOptions(cf,options));
        }

        context.put("CfFilters", cfs);
      // cfs.get(0).getCustomField().getCustomFieldType()
//        cfs.get(0).getOptions().getOptionById(0L).getValue()

        return context;
    }

    private Map<String, Object> addDepartmanOptionsToContext(Map<String, Object> context) {

        CustomField cf= ComponentAccessor.getCustomFieldManager().getCustomFieldObject(departmanCfId);
        assert cf != null;
        //FieldConfig oneAndOnlyConfig = cf.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig().getConfigItems().get(1).getFieldConfig();
        FieldConfig oneAndOnlyConfig = cf.getConfigurationSchemes().listIterator().next().getOneAndOnlyConfig();
        Options options = ComponentAccessor.getOptionsManager().getOptions(oneAndOnlyConfig);
        //options.get(0).getValue()
        context.put("departmanOptions", options);
      // cfs.get(0).getCustomField().getCustomFieldType()
//        cfs.get(0).getOptions().getOptionById(0L).getValue()

        return context;
    }

//    JSONObject getRow(String firstName, String lastName){
//        JSONObject person = new JSONObject();
//        person.put("firstName", firstName);
//        person.put("lastName", lastName);
//        return person ;
//    }
//
//    public JSONArray getTable(){
//
//        JSONArray employees = new JSONArray();
//        employees.put(getRow("John","Doe"));
//        employees.put(getRow("Anna","Smith"));
//        employees.put(getRow("Peter","Jones"));
//
//        return employees;
//    }

    public static SearchResults<Issue> getIssueSearchResults(JiraAuthenticationContext authenticationContext,SearchService searchService) throws JqlParseException, SearchException {
        Query projectApproveQuery = ComponentAccessor.getComponent(JqlQueryParser.class).parseQuery(ProjectApproveJQL);
        return searchService.search(authenticationContext.getLoggedInUser(),projectApproveQuery , PagerFilter.getUnlimitedFilter());
    }

    //    private Map<String, Object> addIssuesToTheContext(Map<String, Object> context, String JQL, JqlQueryParser jqlQueryParser, CustomField kapasiteAbapCf,CustomField kapasiteSapCf,CustomField gerekliAbapEforCf,CustomField gerekliSapEforCf) throws SearchException, JqlParseException {
//        try {
//            Query conditionQuery;
//                    switch (action) {
//                case "WFA":
//                    conditionQuery = jqlQueryParser.parseQuery(Constants.WFA);
//                    break;
//                case "PLANLAMA":
//                    conditionQuery = jqlQueryParser.parseQuery(Constants.PLANLAMA);
//                    break;
//                case "SATISARTTIRAN":
//                    conditionQuery = jqlQueryParser.parseQuery(Constants.SATISARTTIRAN);
//                    break;
//                default:
//                     //conditionQuery = jqlQueryParser.parseQuery(ProjectApproveJQL);
//                   // conditionQuery = jqlQueryParser.parseQuery(DEVORTAMI_TEST_SORGUSU);
//            }
//
////            for (Issue issue : issues) {
////                Object kapasiteABAPvalue = kapasiteAbapCf.getValue(issue);
////            }
////            IssueService.IssueResult kapasiteIssue = issueService.getIssue(authenticationContext.getLoggedInUser(),"FP-17");
//
//            //List<CustomField> customFieldsInProject = new ArrayList<>();
////            customFieldsInProject.add(kapasiteAbapCf);
////            customFieldsInProject.add(kapasiteSapCf);
//
//
//
//
////            context.put("kapasiteAbap",kapasiteIssue.getIssue().getCustomFieldValue(kapasiteAbapCf));
////            context.put("kapasiteSap",kapasiteIssue.getIssue().getCustomFieldValue(kapasiteSapCf));
////            context.put("gerekliAbapEforCf", gerekliAbapEforCf);
////         // context.put("issueService", issueService);
////         // context.put("user", authenticationContext.getLoggedInUser());
////            context.put("gerekliSapEforCf", gerekliSapEforCf);
//
//            return context;
//
//        } catch (JqlParseException e) {
//            logger.error("JqlParseException error at project approve" + e.getParseErrorMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
}
