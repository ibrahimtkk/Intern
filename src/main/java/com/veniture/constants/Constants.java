package com.veniture.constants;

import com.atlassian.jira.component.ComponentAccessor;

public class Constants {

    public static final Boolean isTest = false;
    public static final String SC_SORGUSU = "project = PF";
    public static final String JIRA_BASE_URL = ComponentAccessor.getApplicationProperties().getString("jira.baseurl");

    public static final String adminUsername = "";
    public static final String adminPassword = "";
    public static final String venitureHostname = "test.veniture.com.tr";
    public static final String floHostname = "test.veniture.com.tr";

    public static final String schemeHTTP = "http://";
    public static final String schemeHTTPS = "https://";
    public static final String QUERY_TEAM = "/rest/tempo-teams/2/team";
    public static final String QUERY_WORKLOAD = "/rest/tempo-core/1/workloadscheme/1";
    //public static final String QUERY_AVAILABILITY = "/rest/tempo-planning/1/capacity/report/8?from=2019-11-19&to=2019-12-04&period=P1w";
    public static final String QUERY_AVAILABILITY_YEAR = "/rest/tempo-planning/1/capacity/report/XXX?from=YYY&to=ZZZ&period=P1w";

    //Veniture Jira - Prod Yeni Ortam
    public static final String ProjectId = "APY";
    //public static final String departmanJQL ="project ="+ProjectId+" and cf[11507] =currentUser() and status=\"Departman Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
    //public static final String gmyJQL ="project ="+ProjectId+" and cf[11406]=currentUser() and status = \"Grup/GMY Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";

    public static final String departmanJQL ="project ="+ProjectId+" and cf[11507] =currentUser() and status=\"Departman Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
    public static final String gmyJQL ="project ="+ProjectId+" and cf[11406]=currentUser() and status = \"Grup/GMY Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
    public static final String arcelikJQL = "project = " + ProjectId + " OR project = AP ORDER BY PriorityNumber" ;

    //public static final String ProjectApproveJQL = "project = "+ProjectId+" AND status=\"CEO Onayı Bekleniyor\" ORDER BY \"Grup / GMY Önceliği\"";
    public static final String ProjectApproveJQL = "project = "+ProjectId+" AND status in (\"CEO Onayı Bekleniyor\",\"Onaylı\") ORDER BY \"Onaylı\"";
    public static final String DEMO_JQL ="project ="+ProjectId +" AND key = \"PF-1165\"";
    public static final String AS_JQL ="reporter != 9171599";
    public static final String DEVORTAMI_TEST_SORGUSU = "project = "+ProjectId;
    public static final long TRUE_OPTION_ID_CanliVeniture = 11200L;
    public static final long GENEL_TRUE_OPTION_ID_CanliVeniture = 11404L;
    public static final long onceliklendirildiMiId = 11500L;
    public static final long genelOnceliklendirildiMiId = 11615L;
    public static final long BIRIM_ONCELIK_ID = 11403L;
    public static final String BIRIM_ONCELIK_ID_STRING = "customfield_11403";
    public static final long GMY_ONCELIK_ID = 11501L;
    public static final String GMY_ONCELIK_STRING = "customfield_11501";
    public static final long PriorityNumber = 10400L;
    public static final String PriorityNumberString = "customfield_10400";
    public static final long ZriorityNumber = 10500L;
    public static final String ZriorityNumberString = "customfield_10500";

    //Veniture Jira eski Ortam
    public static final String WFA = "project = FP AND issuetype = \"Project Card\" AND status = \"Waiting for approval\"";
    public static final String PLANLAMA = "project = FP AND issuetype = \"Project Card\" AND Departman = Planlama OR project = FP AND issuetype = \"Project Card\" AND Etiket = \"Satışı Arttıran\"";
    public static final String SATISARTTIRAN ="project = FP AND issuetype = \"Project Card\" AND status = \"Waiting for approval\" AND Etiket = \"Satışı Arttıran\"";
    public static final String PROJECTCARDS ="project = FP AND issuetype = \"Project Card\"";
    public static final Integer ApproveWorkflowTransitionId = 121;
    public static final Integer DeclineWorkflowTransitionId = 151;
    public static final Integer OnayaGeriGöderTransitionId = 271;
    public static final Integer CanliOnayli2CanceledTransitionId = 151;
    public static final Integer Onayli2CanceledTransitionId = 331;

    public static final long ABAPeforCfId       = 11605L;
    public static final long ANeforCfId         = 11606L;
    public static final long ECeforCfId         = 11614L;
    public static final long NSeforCfId         = 11611L;
    public static final long OPeforCfId         = 11613L;
    public static final long PMOeforCfId        = 11609L;
    public static final long SapModEforCfId     = 11604L;
    public static final long SDeforCfId         = 11610L;
    public static final long UDeforCfId         = 11612L;
    public static final long YGeforCfId         = 11606L;
    public static final long BIeforCfId         = 11607L;

    public static final long kapasiteSapCfId = 10808L;
    public static final long kapasiteAbapCfId = 10815L;
    public static final long gerekliAbapEforCfId = 10814L;
    public static final long gerekliSapEforCfId = 10813L;
    public static final long öncelikBerkCfId = 11105L;
    public static final long projeEtikleriCfId = 11306L;
    public static final long departmanCfId = 11405l;
    public static final long verimlilikBaremiCfId = 11312l;
    public static final long maliyetBaremiCfId = 11310l;
    public static final long satisBaremiCfId = 11308l;
    public static final long SureclerManuelYuruyorMuCF = 11313l;
    public static final long gmyOnceligiCF = 11501l;
    public static final long projeKategoriCF = 11903l;
    public static final long projeYiliCf = 11800l;
    public static final long araProjemiCf = 11801l;
    public static final long isSelectedCf = 12200l;
    public static final long sponsorCf = 12304L;
    public static final long CanliIsSelectedCf = 12200l;
    public static final String isSelectedCfId = "customfield_12200";
}
