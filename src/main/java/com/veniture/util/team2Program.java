package com.veniture.util;

import com.veniture.servlet.ProjectApprove;
import model.pojo.Program;
import model.pojo.TempoTeams.Team;

import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class team2Program {
    private Map<String, Object> context;
    private List<Team> teams;

    public static Double getTotalRemainingCapacityOfAllPrograms() {
        return totalRemainingCapacityOfAllPrograms;
    }

    public static Double totalRemainingCapacityOfAllPrograms = 0.0;
    public static Double totalCapacityOfAllPrograms =0.0;

    public team2Program(Map<String, Object> context, List<Team> teams) {
        this.context = context;
        this.teams = teams;
    }

    public Map<String, Object> invoke() {
        totalRemainingCapacityOfAllPrograms =0.0;
        totalCapacityOfAllPrograms =0.0;

        Set<Program> BasicPrograms = getPrograms(teams);
        final Set<Program> programsWithCapacities = createProgramsWithCapacitiesFromTeams(teams, BasicPrograms);
        for (Program program:programsWithCapacities){
            Double programRemainingCapacity;
            if (program.getRemainingCapacity() > 0) {
                programRemainingCapacity= program.getRemainingCapacity();
                totalRemainingCapacityOfAllPrograms += programRemainingCapacity;
            }
            else {
                programRemainingCapacity=0.0;
            }

            Double programTotalCapacity;
            if (program.getTotalCapacity() > 0) {
                programTotalCapacity= program.getTotalCapacity();
                totalCapacityOfAllPrograms += programTotalCapacity;
            }
            else {
                programTotalCapacity=0.0;
            }

            //Türkçe karakterler sorun çıkarıyordu.

            String ProgramNameEscaped= Normalizer.normalize(program.getName().replaceAll("\\s",""), Normalizer.Form.NFD).replaceAll("\\p{Mn}", "").replaceAll("ı", "i");
            context.put(ProgramNameEscaped+"Remaining",com.veniture.util.functions.calculateDayCountFromHour(programRemainingCapacity));
            context.put(ProgramNameEscaped+"Total",com.veniture.util.functions.calculateDayCountFromHour(programTotalCapacity));
        }
        context.put("TotalRemainingCapacity", com.veniture.util.functions.calculateDayCountFromHour(totalRemainingCapacityOfAllPrograms));
        context.put("totalCapacityOfAllPrograms",com.veniture.util.functions.calculateDayCountFromHour( totalCapacityOfAllPrograms));
        return context;
    }

    private Set<Program> getPrograms(List<Team> teams) {
        HashSet<Program> programs = new HashSet<>();

        programs.add(new Program("Analiz",0.0));
        programs.add(new Program("Yazılım Geliştirme",0.0));
        programs.add(new Program("SAP Uygulama",0.0));
        programs.add(new Program("SAP Abap",0.0));
        programs.add(new Program("İş Zekası ve Raporlama",0.0));
        programs.add(new Program("Proje Yönetimi",0.0));

//        programs.add(new Program("Analiz",0));
//        programs.add(new Program("YazilimGelistirme",0));
//        programs.add(new Program("SAPUygulama",0));
//        programs.add(new Program("SAPAbap",0));
//        programs.add(new Program("IsZekasiveRaporlama",0));
//        programs.add(new Program("ProjeYonetimi",0));
//        Set<String> programNames = teams.stream().map(Team::getProgram).collect(Collectors.toSet());
//        programNames.remove(null);
//        for (String programName: programNames){
//            programs.add(new Program(programName,0));
//        }
        return programs;
    }

    private Set<Program> createProgramsWithCapacitiesFromTeams(List<Team> teams, Set<Program> Programs) {
        for (Team team:teams){
            for (Program program:Programs){
                try {
                    if (team.getProgram().equalsIgnoreCase(program.getName())){
                        Programs.remove(program);
                        program.addRemainingCapacity(team.getRemainingInAYear());
                        program.addTotalCapacity(team.getTotalAvailabilityInAYear());
                        Programs.add(program);
                        break;
                    }
                } catch (Exception e) {
                    ProjectApprove.logger.debug("This team does not have any program related to it");
                }
            }
        }
        return Programs;
    }
}
