package pro.bechat.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import pro.bechat.wallet.job.TransactionJob;


@SpringBootApplication
@MapperScan("pro.bechat.wallet.domain.dao")
@ImportResource("task.xml")
@ComponentScan("pro.bechat.wallet.*")
public class MessagesApplication {

    public static void main(String[] args) throws SchedulerException {

        ApplicationContext context = SpringApplication.run(MessagesApplication.class, args);
        System.out.println(context.toString());
        Scheduler scheduler = (Scheduler) context.getBean("multitaskScheduler");
        //配置定时任务对应的Job，这里执行的是ScheduledJob2类中定时的方法
        JobDetail jobDetail = JobBuilder.newJob(TransactionJob.class) .withIdentity("job1", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        // 每6s执行一次
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1") .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);


    }


}
