package pro.bechat.wallet.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pro.bechat.wallet.configuration.SpringContextUtil;
import pro.bechat.wallet.domain.model.model.Contract;
import pro.bechat.wallet.domain.service.ContractsService;
import pro.bechat.wallet.domain.support.AdminClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * create TransactionJob by huc
 * 2018/4/16  下午7:24
 */
@Component
public class ContractJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ContractJob.class);

    ContractsService contractsService;

    /**  修改交易确认状态  */
    String STATUS = "0";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logger.info("合约Job开始...");
        contractsService = (ContractsService) SpringContextUtil.getBean(ContractsService.class);
        Admin admin = AdminClient.newInstance();
        List<Contract> contracts = contractsService.selectContractPending();
        try {
            for (Contract c : contracts) {
                Optional<TransactionReceipt> response =  admin.ethGetTransactionReceipt(c.getTransactionaddr()).send().getTransactionReceipt();
                if (response.isPresent()){
                    TransactionReceipt receipt = response.get();
                    logger.info(receipt.toString());
                    c.setStatus(STATUS);
                    c.setContractaddr(receipt.getContractAddress());
                    contractsService.updateByPrimaryKeySelective(c);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
