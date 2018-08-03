package pro.bechat.wallet.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.bechat.wallet.domain.service.DirectPrizeService;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 */
@Component
public class ExampleTimer {

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private DirectPrizeService directPrizeService;          //直推奖业务Service



    /*@Scheduled(fixedRate = 10000)
    public void timerRate() {
        System.out.println(dateFormat.format(new Date()));
    }*/

    //第一次延迟1秒执行，当执行完后2秒再执行
    /*@Scheduled(initialDelay = 1000, fixedDelay = 2000)
    public void timerInit() {
        System.out.println("init : "+dateFormat.format(new Date()));
    }*/

    //每天20点16分50秒时执行 每天00点00分00秒时执行 17点启动定时
    @Scheduled(cron = "00 28 15 * * ?")
    public void timerCron() throws Exception{
        System.out.println("current time : "+ dateFormat.format(new Date()));

        //directPrizeService.directPrize();           //定时返还直推奖

        //directPrizeService.interestAward();         //利息生息

        directPrizeService.timedTask();             //定时任务(利息生息、直推奖、动态奖)

    }

}
