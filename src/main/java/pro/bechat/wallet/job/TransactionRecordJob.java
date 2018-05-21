package pro.bechat.wallet.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pro.bechat.wallet.configuration.SpringContextUtil;
import pro.bechat.wallet.domain.model.model.TransactionRecord;
import pro.bechat.wallet.domain.service.TransactionRecordService;
import pro.bechat.wallet.domain.support.AdminClient;

import java.util.List;
import java.util.Optional;

/**
 * create ContractJob by huc
 * 2018/4/19  上午1:29
 */
@Component
public class TransactionRecordJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRecordJob.class);

    Web3j web3j = null;

    TransactionRecordService transactionRecordService;

    private final String STATUS = "0";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("交易记录Job开始...");
        Admin admin = AdminClient.newInstance();
        transactionRecordService   = (TransactionRecordService) SpringContextUtil.getBean(TransactionRecordService.class);
        List<TransactionRecord> records = transactionRecordService.selectTransactionPending();
        try{
            for (TransactionRecord record: records) {
                Optional<TransactionReceipt> receipts =  admin.ethGetTransactionReceipt(record.getTransactionhash()).send().getTransactionReceipt();
                if (receipts.isPresent()){
                    TransactionReceipt receipt = receipts.get();
                    record.setBlockhash(receipt.getBlockHash());
                    record.setBlocknumber(String.valueOf(receipt.getBlockNumber()));
                    record.setContractaddress(receipt.getContractAddress());
                    record.setStatus(STATUS);
                    transactionRecordService.updateByPrimaryKeySelective(record);
                }
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }




    }
}
