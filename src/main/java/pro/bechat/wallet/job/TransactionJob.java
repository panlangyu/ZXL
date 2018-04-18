package pro.bechat.wallet.job;

import org.apache.commons.lang.ArrayUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.Transaction;
import pro.bechat.wallet.configuration.RedisTool;
import pro.bechat.wallet.configuration.SpringContextUtil;
import pro.bechat.wallet.domain.support.Web3Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

import static pro.bechat.wallet.domain.support.AdminClient.newInstance;

/**
 * create TransactionJob by huc
 * 2018/4/16  下午7:24
 */
@Component
public class TransactionJob implements Job{

    private static final Logger logger = LoggerFactory.getLogger(TransactionJob.class);


    /**  修改交易确认状态  */
    String STATUS = "0";

    Jedis jedis = null;

    @Autowired
    Web3Service web3Service;

    ApplicationContext applicationContext;
    protected void executeInternal() {
        logger.info("定时任务2进行中.......");

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        if (jedis == null){
            jedis = RedisTool.getJedis();
        }
        applicationContext = SpringContextUtil.getApplicationContext();
       // redis = (RedisServiceImpl) SpringContextUtil.getBean("redisServiceImpl");
        logger.info("定时任务2进行中.......");
        // do something else
        Admin admin = newInstance();
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        logger.info("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Set<String> keys = jedis.keys("*");
        logger.info(String.valueOf(keys));
        logger.info(ArrayUtils.toString(keys.toArray()));
        for (String key : keys) {
            if (key.contains("contract:transfers") || key.contains("contract:deploy") || key.contains("eth:transfers") || key.contains("coin:transaction")){
                String hash= (String) jedis.get(key);
                Optional<Transaction> request;
                try {
                    request = admin.ethGetTransactionByHash(hash).send().getTransaction();
                    if (request.isPresent()){
                        Transaction tx = request.get();
                        web3Service.process(tx);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
