package com.veniture.util;

import com.atlassian.sal.api.net.RequestFactory;
import model.pojo.TempoTeams.Team;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TeamsWithAvailabilityTimes {

    private Logger logger;
    private RequestFactory requestFactory;
    public TeamsWithAvailabilityTimes(Logger logger, RequestFactory requestFactory) {
        this.requestFactory=requestFactory;
        this.logger=logger;
    }

    public List<Team> invoke() throws Exception {
        List<Team> teams;
        try {
            RemoteSearcher remoteSearcher =  new RemoteSearcher(requestFactory);
            teams=remoteSearcher.getAllTeams();
            for (Team team:teams){
                Double totalRemainingTimeInYearForTeam=0.0;
                try {
                    totalRemainingTimeInYearForTeam = remoteSearcher.getTotalRemainingTimeInYearForTeam(team.getId());
                    team.setRemainingInAYear(totalRemainingTimeInYearForTeam);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Double totalAvailabilityTimeInYearForTeam = 0.0;
                try {
                    totalAvailabilityTimeInYearForTeam = remoteSearcher.getTotalAllocatedTimeInYearForTeam(team.getId())+totalRemainingTimeInYearForTeam;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                team.setTotalAvailabilityInAYear(totalAvailabilityTimeInYearForTeam);
            }
        } catch (Exception e) {
            logger.error("Error at getTeamsAndSetRemaining");
            logger.error(e.getMessage());
            logger.error(e.getLocalizedMessage());
            logger.error(Arrays.toString(e.getStackTrace()));
            throw new Exception();
        }
        return teams;
    }
}
