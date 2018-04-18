package pro.bechat.wallet.domain.support;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import pro.bechat.wallet.business.service.impl.RedisServiceImpl;
import pro.bechat.wallet.domain.model.model.Contract;
import pro.bechat.wallet.domain.model.model.TransactionRecord;
import pro.bechat.wallet.domain.service.ContractsService;
import pro.bechat.wallet.domain.service.TransactionRecordService;

import java.io.IOException;
import java.util.Optional;

/**
 * create Web3Service by huc
 * 2018/4/3  上午10:09
 */
@Component
public class Web3Service {

    @Value("${pro.bechat.wallet.gethHostAndPort:unknown}")
    String gethHostAndPort;

    public Web3j newHttpWeb3j() {

        if (gethHostAndPort.equals("unknown")) {
            gethHostAndPort = "http://127.0.0.1:8545";
        }
        return Web3j.build(new HttpService(gethHostAndPort));
    }

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Web3Service.class);
    /**
     * 修改交易确认状态
     */
    private final String STATUS = "0";

    @Autowired
    TransactionRecordService recordService;

    @Autowired
    ContractsService contractsService;


    private static Web3j web3j = null;

    @Autowired
    RedisServiceImpl redis;

    String redisKey = "coin:transaction:,contract:transfers:,contract:deploy:,eth:transfers:,coin:transaction:";


    /**
     * redisKey coin:transaction:hash
     *
     * @param tx
     */
    public void process(Transaction tx) {
        if (web3j == null) {
            web3j = newHttpWeb3j();
        }
        boolean flag = true;
        logger.info("pendingTransactionObservable 带交易区块监听...");
        String hash = tx.getHash();
        System.out.println(hash);

        /** 如果没有交易记录，要么是外部转入，要么是合约发布 */
        TransactionRecord transactionRecord = recordService.selectByPrimaryKey(hash);
        if (transactionRecord == null) {
            flag = false;
        }

        TransactionRecord record = new TransactionRecord();
        Contract contract = new Contract();
        try {
            Optional<TransactionReceipt> receipts = web3j.ethGetTransactionReceipt(hash).send().getTransactionReceipt();
            TransactionReceipt receipt = null;
            logger.info(String.valueOf(receipts));
            if (receipts.isPresent()) {
                receipt = receipts.get();
                record.setStatus(STATUS);
                record.setContractaddress(receipt.getContractAddress());
                record.setTransactionhash(receipt.getTransactionHash());
                if (flag) {
                    recordService.updateByPrimaryKeySelective(record);
                    for (String redisKeys : redisKey.split(",")) {
                        redis.remove(redisKeys + hash);
                        logger.info("removeValue:"+redis.get(redisKeys));
                    }

                } else {
                    contract.setContractaddr(receipt.getContractAddress());
                    contract.setTransactionaddr(hash);
                    contract.setStatus(STATUS);
                    contractsService.updateByPrimaryKeySelective(contract);
                    String removeKey = redisKey.split(",")[2] + hash;
                    logger.info("removeKey:" + removeKey);
                    redis.remove(removeKey);
                    logger.info("removeValue:"+ redis.get(removeKey));
                }
            } else {
                logger.info(tx.toString());
                String cacheRedisKey = redisKey.split(",")[0] + hash;
                redis.set(cacheRedisKey, hash);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
