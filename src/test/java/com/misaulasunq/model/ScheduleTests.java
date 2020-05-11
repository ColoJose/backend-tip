package com.misaulasunq.model;


import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.Set;

@RunWith(SpringRunner.class)
public class ScheduleTests {

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private Schedule schedule;

    @Before
    public void setUp(){
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    public void whenCreatingScheduleWithNoStartTimeShouldRaiseStartTimeShouldBeSetted() {
        // given
        schedule = new Schedule();
        schedule.setEndTime(LocalTime.now());
        schedule.setDay(Day.LUNES);

        // when
        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);

        // then
        ConstraintViolation<Schedule> violationSchedule = violations.iterator().next();
        Assert.assertEquals("A Start Time Should Be Setted", violationSchedule.getMessage());
    }

    @Test
    public void whenCreatingScheduleWithNoEndTimeShouldRaiseEnTimeShouldBeSet() {
        // given
        schedule = new Schedule();
        schedule.setDay(Day.LUNES);
        schedule.setStartTime(LocalTime.now());

        // when
        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);

        // then
        ConstraintViolation<Schedule> violationSchedule = violations.iterator().next();
        Assert.assertEquals("A End Time Should Be Setted", violationSchedule.getMessage());

    }

    @Test
    public void whenCreatingScheduleWithNoWeekDayShouldRaiseDayShouldBePutIt() {
        // given
        schedule = new Schedule();
        schedule.setEndTime(LocalTime.now());
        schedule.setStartTime(LocalTime.now());

        // when
        Set<ConstraintViolation<Schedule>> violations = validator.validate(schedule);


        // then
        ConstraintViolation<Schedule> violationSchedule = violations.iterator().next();
        Assert.assertEquals("A Day Should Be Put It", violationSchedule.getMessage());

    }
}
