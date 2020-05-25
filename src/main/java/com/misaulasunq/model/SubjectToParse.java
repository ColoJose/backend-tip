package com.misaulasunq.model;

import com.misaulasunq.controller.dto.CommissionDTO;
import com.misaulasunq.controller.dto.ScheduleDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectToParse {

    private String name;
    private String subjectCode;
    private List<CommissionDTO> commissions;
    private Integer degreeId;

    public SubjectToParse() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<CommissionDTO> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<CommissionDTO> commissions) {
        this.commissions = commissions;
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }

    public Subject parse(Degree degreeReceived) {
        Subject subject = new Subject();
        subject.setName(this.getName());
        subject.setSubjectCode(this.getSubjectCode());
        subject.setCommissions(this.parseCommissions(subject));
        // add bidirectional subject and degree
        subject.addDegree(degreeReceived);
        degreeReceived.addSubject(subject);

        return subject;
    }

    private List<Commission> parseCommissions(Subject subject) {
        return commissions.stream()
                             .map( com -> this.parseCommission(com,subject))
                             .collect(Collectors.toList());
    }

    private Commission parseCommission(CommissionDTO commissionDTO, Subject subject) {
        Commission commission = new Commission();
        commission.setName(commissionDTO.getName());
        commission.setYear(commissionDTO.getYear());
        commission.setSemester(Semester.valueOf(this.toEnumSemesterString(commissionDTO.getSemester())));
        commission.setSubject(subject);
        commission.setSchedules(this.parseSchedules(commissionDTO.getSchedules(), commission));

        return commission;
    }

    private String toEnumSemesterString(String semester) {
        String res = new String();
        switch (semester) {
            case "Primer cuatrimestre": res = "PRIMER";
            case "Segundo cuatrimestre": res = "SEGUNDO";
            case "Anual": res = "ANUAL";
        }
        return res;
    }

    private List<Schedule> parseSchedules(List<ScheduleDTO> schedules, Commission commission) {
        return schedules.stream()
                        .map( sch -> this.parseSchedule(sch,commission))
                        .collect(Collectors.toList());
    }

    private Schedule parseSchedule(ScheduleDTO sch, Commission commission) {
        Schedule schedule = new Schedule();
        schedule.setDay(Day.valueOf(this.toDayEnumString(sch.getDay())));
        schedule.setStartTime(sch.getStartTime());
        schedule.setEndTime(sch.getEndTime());
        schedule.setCommission(commission);
        // classroom
        Classroom classroom = new Classroom();
        classroom.setNumber("10");
        classroom.setImageClassRoomBase64("");
        classroom.addSchedule(schedule);
        schedule.setClassroom(classroom);

        return schedule;
    }

    private String toDayEnumString(String day) {
        String dayString = new String();
        switch (day) {
            case "Lunes": dayString = "LUNES";
            case "Martes": dayString = "MARTES";
            case "Miércoles": dayString = "MIERCOLES";
            case "Jueves": dayString = "JUEVES";
            case "Viernes": dayString = "VIERNES";
            case "Sábado": dayString = "SABADO";
        }

        return  dayString;
    }

}
