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
        cfArrayList.add(customFieldManager.getCustomFieldObject(11905L));//TotalEforCF
        cfArrayList.add(customFieldManager.getCustomFieldObject(11304l));//sponsor
        cfArrayList.add(customFieldManager.getCustomFieldObject(departmanCfId));//departman
        cfArrayList.add(customFieldManager.getCustomFieldObject(11403L));//departmanOnceligi
        cfArrayList.add(customFieldManager.getCustomFieldObject(11501L));//GMY Önceliği
        cfArrayList.add(customFieldManager.getCustomFieldObject(11302l));//projeFaz
        cfArrayList.add(customFieldManager.getCustomFieldObject(11305l));//etkilenecel dep
        cfArrayList.add(customFieldManager.getCustomFieldObject(projeEtikleriCfId));//proje etiket
        cfArrayList.add(customFieldManager.getCustomFieldObject(11307l));//satışa pztf etkisini nasıl edersiniz
        cfArrayList.add(customFieldManager.getCustomFieldObject(satisBaremiCfId));//satış barami
        cfArrayList.add(customFieldManager.getCustomFieldObject(11309l));//maliyet etkisi tarifi
        cfArrayList.add(customFieldManager.getCustomFieldObject(maliyetBaremiCfId));//maliyet barami
//        cfArrayList.add(customFieldManager.getCustomFieldObject(11311l));//verimlilik etkisi tarifi
//        cfArrayList.add(customFieldManager.getCustomFieldObject(verimlilikBaremiCfId));//verimlilik barami
        cfArrayList.add(customFieldManager.getCustomFieldObject(SureclerManuelYuruyorMuCF));//süreçler manuel yürütülebilior mu
        cfArrayList.add(customFieldManager.getCustomFieldObject(11314l));//manuel yürütmedki zorluklar
        cfArrayList.add(customFieldManager.getCustomFieldObject(11315l));//danışmanlık gereklimi
        cfArrayList.add(customFieldManager.getCustomFieldObject(11903l));//proje kategori
        cfArrayList.add(customFieldManager.getCustomFieldObject(projeYiliCf));//proje yili
        cfArrayList.add(customFieldManager.getCustomFieldObject(araProjemiCf));// Ara proje mi
        //cfArrayList.add(customFieldManager.getCustomFieldObject(isSelectedCf));// isSelected

        //List<String> arrayList= cfArrayList.stream().map(CustomField::getName).collect(Collectors.toList());
        return cfArrayList;
//            return cfMgr.getCustomFieldObjects(searchContext);
//            ArrayList<CustomField> list = new ArrayList<>();
//            list.add(cfMgr.getCustomFieldObject(11501L));
//            return list;
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

