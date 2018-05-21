package pro.bechat.wallet.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pro.bechat.wallet.business.service.impl.RedisServiceImpl;
import pro.bechat.wallet.domain.model.model.TransactionRecord;
import pro.bechat.wallet.domain.service.TransactionRecordService;
import pro.bechat.wallet.domain.support.Web3Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

/**
 * create RecordListening by huc
 * 2018/4/12  下午11:04
 *redisKey:
 * contract:transfers:hash  合约转账
 * contract:deploy:hash 创建合约
 * eth:transfers:hash  ETH转账
 */


@Component
public class RecordRedis {

    Logger logger = LoggerFactory.getLogger(RecordRedis.class);

    @Autowired
    TransactionRecordService transactionRecordService;

    @Resource
    RedisServiceImpl redis;

    @Autowired
    Web3Service service;

    /**  修改交易确认状态  */
    String STATUS = "0";

    Web3j web3j = null;

    @Autowired
    Web3Service web3Service;

    /**
     * redisKye: contract:transfers:hash 合约转账
     */
   // @Scheduled(fixedRate = 40000)
    public void contractTransfers(){
        if (web3j == null){
            web3j = service.newHttpWeb3j();
        }
        logger.info("Transaction listener.... ");
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        logger.info("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Set<String> keys = redis.getAllKeys();
        logger.info(String.valueOf(keys));
        logger.info(ArrayUtils.toString(keys.toArray()));
        for (String key : keys) {
            if (key.contains("contract:transfers") || key.contains("contract:deploy") || key.contains("eth:transfers") || key.contains("coin:transaction")){
                String hash= (String) redis.get(key);
                Optional<Transaction> request;
                try {
                    request = web3j.ethGetTransactionByHash(hash).send().getTransaction();
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


    /**
     * contract:deploy:hash 创建合约
     */
    //@Scheduled(fixedRate = 20000)
    public void contractDeploy(){
        logger.info("contract:deploy:hash 创建合约");
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Set<String> keys = redis.getAllKeys();
        System.out.println(ArrayUtils.toString(keys.toArray()));
        for (String key : keys) {
            if (key.contains("contract:deploy")){
                String redisKey= (String) redis.get(key);
                saveTransaction(redisKey);
            }

        }
    }

    /**
     * eth:transfers:hash  ETH转账
     */
    //@Scheduled(fixedRate = 60000)
    public void ethTransfers(){
        logger.info("eth:transfers:hash  ETH转账");
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Set<String> keys = redis.getAllKeys();
        System.out.println(ArrayUtils.toString(keys.toArray()));
        for (String key : keys) {
            if (key.contains("eth:transfers")){
                String redisKey= (String) redis.get(key);
                saveTransaction(redisKey);
            }

        }
    }


    TransactionRecord saveTransaction(String redisKey){
        if (web3j == null){
            web3j = service.newHttpWeb3j();
        }
        TransactionRecord record = new TransactionRecord();
        try {
            Optional<Transaction> request =  web3j.ethGetTransactionByHash(redisKey).send().getTransaction();
            if (request.isPresent()){
                Transaction response =  request.get();
                JSONObject jsonObject = (JSONObject) JSON.toJSON(response);
                record = JSONObject.toJavaObject(jsonObject,TransactionRecord.class);
                Optional<TransactionReceipt> receipts = web3j.ethGetTransactionReceipt(redisKey).send().getTransactionReceipt();
                record.setStatus(STATUS);
                record.setTransactionhash(response.getHash());
                record.setFromaddress(response.getFrom());
                record.setToaddress(response.getTo());
                record.setAmount(String.valueOf(response.getValue()));
                record.setTransactionid(response.getHash());

                if (receipts.isPresent()){
                    TransactionReceipt receipt = receipts.get();
                    record.setBlockhash(receipt.getBlockHash());
                    record.setBlocknumber(String.valueOf(receipt.getBlockNumber()));
                    record.setLogs(String.valueOf(receipt.getLogs()));
                    record.setLogsbloom(receipt.getLogsBloom());
                    record.setContractaddress(receipt.getContractAddress());
                    redis.remove(redisKey);
                }
                transactionRecordService.updateByPrimaryKeySelective(record);
                return record;
            }

        } catch (IOException e) {
            System.out.println("交易未确认！！！");
        }

        return record;
    }



}
