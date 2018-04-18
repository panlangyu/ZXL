package pro.bechat.wallet.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import pro.bechat.wallet.domain.support.Web3Service;


/**
 * @ClassName: IEthereumService
 * @author 麦子 wheat235@gmail.com
 */
@Component
public class EthereumTransactionListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(EthereumTransactionListener.class);


	@Autowired
	Web3Service web3Service;

	Web3j web3j = null;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		if (web3j == null){
			web3j = web3Service.newHttpWeb3j();
		}
		log.info("监听用户转入的交易记录");
		if (evt.getApplicationContext().getParent() != null) {
			return;
		}
		// 监听用户转入的交易记录 DefaultBlockParameterName.LATEST
		web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.LATEST).subscribe(web3Service::process);

	}
}
