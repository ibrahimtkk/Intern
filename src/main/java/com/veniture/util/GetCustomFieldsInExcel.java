package com.veniture.util;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.search.SearchContext;
import com.atlassian.jira.jql.parser.JqlParseException;
import com.atlassian.jira.jql.parser.JqlQueryParser;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.veniture.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.veniture.constants.Constants.*;

public class GetCustomFieldsInExcel {

    private JqlQueryParser jqlQueryParser= ComponentAccessor.getComponent(JqlQueryParser.class);
    private SearchService searchService=ComponentAccessor.getComponent(SearchService.class);
    private JiraAuthenticationContext authenticationContext =ComponentAccessor.getJiraAuthenticationContext();

    public List<CustomField> invoke()   {
        ArrayList<CustomField> cfArrayList= new ArrayList<>();
        CustomFieldManager customFieldManager=ComponentAccessor.getCustomFieldManager();
//        cfArrayList.add(customFieldManager.getCustomFieldObject(10600L));//PriorityNumber
//        cfArrayList.add(customFieldManager.getCustomFieldObject(11000L)); // Bütçe
        cfArrayList.add(customFieldManager.getCustomFieldObject(11104L)); // ProjectLead
        cfArrayList.add(customFieldManager.getCustomFieldObject(11101L)); // Proje Ismi
//        cfArrayList.add(customFieldManager.getCustomFieldObject(11102L)); // Baslangic Tarihi
//        cfArrayList.add(customFieldManager.getCustomFieldObject(10900L)); // Bitis Tarihi -> Due Date
        cfArrayList.add(customFieldManager.getCustomFieldObject(11300L)); // Kaynak
        cfArrayList.add(customFieldManager.getCustomFieldObject(11501L));//Proje insan gücü maliyeti-gerçekleşen
        cfArrayList.add(customFieldManager.getCustomFieldObject(11502L));//Proje insan gücü maliyeti-kalan
        cfArrayList.add(customFieldManager.getCustomFieldObject(11500L));//Proje insan gücü maliyeti-planlanan

        cfArrayList.add(customFieldManager.getCustomFieldObject(11504L));//Proje harcama maliyetleri-gerçekleşen
        cfArrayList.add(customFieldManager.getCustomFieldObject(11505L));//Proje harcama maliyetleri-kalan
        cfArrayList.add(customFieldManager.getCustomFieldObject(11503L));//Proje harcama maliyetleri-planlanan

        cfArrayList.add(customFieldManager.getCustomFieldObject(11510L));//Proje fikri haklar maliyetleri-gerçekleşen
        cfArrayList.add(customFieldManager.getCustomFieldObject(11511L));//Proje fikri haklar maliyetleri-kalan
        cfArrayList.add(customFieldManager.getCustomFieldObject(11509L));//Proje fikri haklar maliyetleri-planlanan
        return cfArrayList;
    }

    public List<CustomField> getCfsForProjectApproveCfPicker() throws JqlParseException {
        SearchContext searchContext= searchService.getSearchContext(authenticationContext.getLoggedInUser(),jqlQueryParser.parseQuery(Constants.SC_SORGUSU));
        //            return cfMgr.getCustomFieldObjects(searchContext).subList(2,5);

        ArrayList<CustomField> cfArrayList= new ArrayList<>();
        CustomFieldManager customFieldManager=ComponentAccessor.getCustomFieldManager();
        cfArrayList.add(customFieldManager.getCustomFieldObject(11302l));//projeFaz
        cfArrayList.add(customFieldManager.getCustomFieldObject(11305l));//etkilenecel dep
        cfArrayList.add(customFieldManager.getCustomFieldObject(projeEtikleriCfId));//proje etiket
        cfArrayList.add(customFieldManager.getCustomFieldObject(11307l));//satışa pztf etkisini nasıl edersiniz
        cfArrayList.add(customFieldManager.getCustomFieldObject(satisBaremiCfId));//satış barami
        cfArrayList.add(customFieldManager.getCustomFieldObject(11309l));//maliyet etkisi tarifi
        cfArrayList.add(customFieldManager.getCustomFieldObject(maliyetBaremiCfId));//maliyet barami
//      cfArrayList.add(customFieldManager.getCustomFieldObject(11311l));       //verimlilik etkisi tarifi
//      cfArrayList.add(customFieldManager.getCustomFieldObject(verimlilikBaremiCfId));//verimlilik barami
        cfArrayList.add(customFieldManager.getCustomFieldObject(SureclerManuelYuruyorMuCF));//süreçler manuel yürütülebilior mu
        cfArrayList.add(customFieldManager.getCustomFieldObject(11314l));       //manuel yürütmedki zorluklar
        cfArrayList.add(customFieldManager.getCustomFieldObject(11315l));       //danışmanlık gereklimi
        cfArrayList.add(customFieldManager.getCustomFieldObject(11903l));       //projectCategory
        cfArrayList.add(customFieldManager.getCustomFieldObject(projeYiliCf));//proje yili
        //cfArrayList.add(customFieldManager.getCustomFieldObject(araProjemiCf));// Ara proje mi

        return cfArrayList;
//            return cfMgr.getCustomFieldObjects(searchContext);
//            ArrayList<CustomField> list = new ArrayList<>();
//            list.add(cfMgr.getCustomFieldObject(11501L));
//            return list;
    }
}

