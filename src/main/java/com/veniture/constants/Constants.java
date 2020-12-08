package com.veniture.constants;

import com.atlassian.jira.component.ComponentAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final Boolean isTest = false;
    public static final String SC_SORGUSU = "project = PF";
    public static final String JIRA_BASE_URL = ComponentAccessor.getApplicationProperties().getString("jira.baseurl");

    public static final String adminUsername = "ibrahim.takak";
    public static final String adminPassword = "qwerty";
    public static final String venitureHostname = "test.veniture.com.tr";
    public static final String floHostname = "test.veniture.com.tr";

    public static final String STARTDATE = "2020-11-09";
    public static final String ENDDATE = "2020-11-19";

    public static final String YEAR = "2020";
    public static final String Holiday_API = "631e6283-8620-499b-a400-ea24bee74e4f";
//    public static final String Holiday_API_Url = "curl -G -d country=\"TR\" -d year=\"YYYY\" -d pretty -d key=\"KKKK\" \"https://holidayapi.com/v1/holidays\"";
    public static final String Holiday_API_Url = "https://holidayapi.com/v1/holidays";

    public static final String[] holidayArray = {"2020-01-01", "2020-4-23", "2020-04-23", "2020-5-1", "2020-05-01", "2020-5-19", "2020-05-19",
    "2020-5-23", "2020-5-24", "2020-5-25", "2020-5-26", "2020-05-23", "2020-05-24", "2020-05-25", "2020-05-26",
    "2020-7-15", "2020-07-15",
    "2020-7-31", "2020-8-1", "2020-8-2", "2020-8-3", "2020-07-31", "2020-08-1", "2020-08-2", "2020-08-3",
            "2020-08-01", "2020-08-02", "2020-08-03", "2020-8-30", "2020-08-30", "2020-10-29", "2020-11-10"};
    public static final List<String> holidays = Arrays.asList(holidayArray);




    public static final String schemeHTTP = "http://";
    public static final String schemeHTTPS = "https://";
    // TODO: tempo ile ilgili id'ler(takim idleri) dinamik olacak yoksa calismaz
    public static final String QUERY_TEAM = "/rest/tempo-teams/2/team";
    public static final String QUERY_TEAM_MEMBER = "/rest/tempo-teams/2/team/xxx/member/";
    public static final String QUERY_ALLOCATION = "/rest/tempo-planning/1/allocation";
    public static final String QUERY_WORKLOAD = "/rest/tempo-core/1/workloadscheme/1";
    //public static final String QUERY_AVAILABILITY = "/rest/tempo-planning/1/capacity/report/8?from=2019-11-19&to=2019-12-04&period=P1w";
    public static final String QUERY_AVAILABILITY_YEAR = "/rest/tempo-planning/1/capacity/report/XXX?from=YYY&to=ZZZ&period=P1w";
    public static final String QUERY_AVAILABILITY_YEAR_BY_DAY = "/rest/tempo-planning/1/capacity/report/XXX?from=YYY&to=ZZZ&period=P1d";
    public static final String QUERY_ALLOCATION_BY_DATE = "/rest/tempo-planning/1/allocation?startDate=SSS&endDate=EEE";
    public static final String QUERY_ALLOCATION_BY_DATE_ASSIGNEE_KEY = "/rest/tempo-planning/1/allocation?startDate=SSS&endDate=EEE&assigneeKeys=AAA";
//public static final String QUERY_ALLOCATION_BY_DATE_ASSIGNEE_KEY = "/rest/tempo-planning/1/allocation?startDate=SSS&endDate=EEE";
    public static final int MAXWORKINGTIME = 8;
    public static final double WORKINGRATIO = 0.9;
    //Veniture Jira - Prod Yeni Ortam
    public static final String ProjectId = "APY";
    //public static final String departmanJQL ="project ="+ProjectId+" and cf[11507] =currentUser() and status=\"Departman Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
    //public static final String gmyJQL ="project ="+ProjectId+" and cf[11406]=currentUser() and status = \"Grup/GMY Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";

    public static final String departmanJQL ="project ="+ProjectId+" and cf[11507] =currentUser() and status=\"Departman Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
    public static final String gmyJQL ="project ="+ProjectId+" and cf[11406]=currentUser() and status = \"Grup/GMY Önceliklendirmesi\" ORDER BY \"Departman Önceliği\"";
//    public static final String arcelikJQL = "project = "+ ProjectId;
    public static final String arcelikJQL = "";

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
    public static final long ProjeLideri = 10700L;
    public static final String ProjeLideriString = "customfield_10700";
    public static final long ProjeIsmi = 10701L;
    public static final String ProjeIsmiString = "customfield_10701";
    public static final long BaslangicTarihi = 10703L;
    public static final String BaslangicTarihiString = "customfield_10703";
    public static final long BitisTarihi = 10704L;
    public static final String BitisTarihiString = "customfield_10704";

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
