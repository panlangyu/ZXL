package pro.bechat.wallet.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import pro.bechat.wallet.domain.service.TransactionRecordService;
import pro.bechat.wallet.domain.service.WalletService;
import pro.bechat.wallet.domain.support.Web3Service;
import rx.Subscription;

import java.util.logging.Logger;

/**
 * create TransactionListening by huc
 * 2018/4/11  下午3:17
 */
@Component
public class EthereumPendingTransactionListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = Logger.getLogger(String.valueOf(EthereumPendingTransactionListener.class));
    /**  修改交易确认状态  */
    private final String STATUS = "0";

    @Autowired
    ApplicationContext context;

    @Autowired
    Web3Service web3Service;

    private Web3j web3j = null;

    @Autowired
    TransactionRecordService recordService;

    @Autowired
    WalletService walletService;

    public static Subscription subscription = null;


    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (web3j == null){
            web3j = web3Service.newHttpWeb3j();
        }

        logger.info("EthereumPendingTransactionListener:服务监听开始");
        /*EthereumPendingTransactionListener.subscription = web3j.pendingTransactionObservable().subscribe(tx -> {
            *//**
             * 如果from 地址不存在本系统，那就是从外部钱包转入，此时记录数据库
             *//*
            logger.info("EthereumPendingTransactionListener--->"+tx.getHash());



        });*/

    }
}