package pro.bechat.wallet.domain.support;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @ClassName: IEthereumService
 * @author 麦子 wheat235@gmail.com
 */
public class Web3JClient {

	private Web3JClient() {

	}

	private volatile static Web3j web3j;

	public static Web3j getClient(String hostAndPort) {

		if (web3j == null) {
			synchronized (Web3JClient.class) {
				if (web3j == null) {
					web3j = Web3j.build(new HttpService(hostAndPort));
				}
			}
		}
		return web3j;
	}
}
