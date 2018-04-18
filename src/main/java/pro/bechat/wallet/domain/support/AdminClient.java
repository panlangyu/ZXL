package pro.bechat.wallet.domain.support;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

/**
 * @ClassName: IEthereumService
 * @author 麦子 wheat235@gmail.com
 */
public class AdminClient {

	private volatile static Admin admin;

	private AdminClient() {

	}

	private static final String hostAndPort = "http://39.107.103.65:8545";

	public static Admin newInstance() {

		if (admin == null) {
			synchronized (Web3JClient.class) {
				if (admin == null) {
					admin = Admin.build(new HttpService(hostAndPort));
				}
			}
		}
		return admin;
	}
}
