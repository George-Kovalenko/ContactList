package edu.itechart.contactlist.util.email;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class EmailScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailScheduler.class);
    private Scheduler scheduler;

    public void init() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("emailconfig");
        String jobName = resourceBundle.getObject("schedule_job_name").toString();
        String jobGroup = resourceBundle.getObject("schedule_job_group").toString();
        int hour = Integer.parseInt(resourceBundle.getObject("dispatch_time_hour").toString());
        int minute = Integer.parseInt(resourceBundle.getObject("dispatch_time_minute").toString());
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            JobDetail sendEmailJob = JobBuilder.newJob(SendEmailJob.class).withIdentity(jobName, jobGroup).build();
            Trigger sendEmailTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(hour, minute)).build();
            scheduler.scheduleJob(sendEmailJob, sendEmailTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error("Can't init scheduler", e);
        }
    }

    public void shutdown() {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            LOGGER.error("Can't shutdown scheduler", e);
        }
    }
}
