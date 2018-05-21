package pro.bechat.wallet.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import pro.bechat.wallet.domain.support.Web3Service;


/**
 * @ClassName: EthereumBlockListener
 * @author 麦子 wheat235@gmail.com
 */

@Component
public class EthereumBlockListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(EthereumBlockListener.class);

	@Autowired
	ApplicationContext context;

	@Autowired
	Web3Service service;

	Web3j web3j = null;



    @Override
	public void onApplicationEvent(ContextRefreshedEvent evt) {
		if (web3j == null){
			web3j = service.newHttpWeb3j();
		}
		log.info("根据新增区块来处理转入转出确认数的逻辑");
		if (evt.getApplicationContext().getParent() != null) {
			return;
		}
		/*web3j.blockObservable(true).subscribe(ethBlock -> {
			log.info("EthereumBlockListener---->:"+ethBlock.toString());
		});*/
	}



}
