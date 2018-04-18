package pro.bechat.wallet.business.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import pro.bechat.wallet.business.service.iface.IEthereumService;
import pro.bechat.wallet.domain.exception.RpcServiceException;
import pro.bechat.wallet.domain.support.AdminClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName: IEthereumService
 * @author 麦子 wheat235@gmail.com
 */
@Service
public class EthereumService implements IEthereumService {

	private static final Logger log = LoggerFactory.getLogger(EthereumService.class);

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(90000);

	/**
	 * 获取账户列表
	 */
	@Override
	public List<String> getAccountlist() throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			return admin.personalListAccounts().send().getAccountIds();
		} catch (Exception e) {
			log.error("获取账户列表异常:{}", e.getMessage());
			throw new RpcServiceException("获取账户列表异常");
		}

	}

	/**
	 * 创建帐户
	 */
	@Override
	public String createAccount(String password) throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
			return newAccountIdentifier.getAccountId();
		} catch (Exception e) {
			log.error("创建帐户异常:{}", e.getMessage());
			throw new RpcServiceException("创建帐户异常");
		}
	}

	/**
	 * 获取帐户余额
	 */
	@Override
	public BigInteger getBalance(String address) throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			EthGetBalance ethGetBalance = admin.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			return ethGetBalance.getBalance();
		} catch (Exception e) {
			log.error("获取账户余额异常:{}", e.getMessage());
			throw new RpcServiceException("获取账户余额异常");
		}
	}

	/**
	 * 获取用户交易记录数
	 */
	@Override
	public BigInteger getTransactionCount(String address) throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			EthGetTransactionCount ethGetTransactionCount = admin
			        .ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

			if (null == ethGetTransactionCount) {
				throw new RpcServiceException("失获取用户交易记录数败");
			}
			return ethGetTransactionCount.getTransactionCount();
		} catch (Exception e) {
			log.error("获取用户交易记录数异常:{}", e.getMessage());
			throw new RpcServiceException("获取用户交易记录数异常");
		}
	}

	/**
	 * 获取交易信息
	 */
	@Override
	public EthTransaction getTransaction(String transactionHash) throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			return admin.ethGetTransactionByHash(transactionHash).sendAsync().get();
		} catch (Exception e) {
			log.error("获取交易信息异常:{}", e.getMessage());
			throw new RpcServiceException("获取交易信息异常");
		}
	}

	/**
	 * 获取用户账号
	 */
	@Override
	public String getAddress(String walletfile, String password) throws RpcServiceException {

		try {
			Credentials credentials = WalletUtils.loadCredentials(password, walletfile);
			return credentials.getAddress();
		} catch (Exception e) {
			log.error("获取用户帐号异常:{}", e.getMessage());
			throw new RpcServiceException("获取交易信息异常");
		}
	}

	@Override
	public EthSendTransaction ethSendRawTransaction(String walletfile, String password, String toAddress,
	        BigDecimal coinNum) throws RpcServiceException {

		String fromAddress = "";

		try {
			Credentials credentials = WalletUtils.loadCredentials(password, walletfile);
			fromAddress = credentials.getAddress();

			Admin admin = AdminClient.newInstance();
			EthGetTransactionCount ethGetTransactionCount = admin
			        .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync()
			        .get();

			BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			BigDecimal weiValue = Convert.toWei(coinNum, Convert.Unit.ETHER);

			RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE, GAS_LIMIT, toAddress,
			        weiValue.toBigInteger(), null);

			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			return admin.ethSendRawTransaction(hexValue).sendAsync().get();

		} catch (Exception e) {
			log.error("[{}]转账给[{}]{}个ETH异常:{}", fromAddress, toAddress, coinNum, e.toString());
			throw new RpcServiceException("转账异常");
		}
	}

	@Override
	public TransactionReceipt transferSendFunds(String walletfile, String password, String toAddress,
	        BigDecimal coinNum) throws RpcServiceException {

		String fromAddress = "";
		try {
			Credentials credentials = WalletUtils.loadCredentials(password, walletfile);
			fromAddress = credentials.getAddress();

			Admin admin = AdminClient.newInstance();

			TransactionReceipt transactionReceipt = Transfer
			        .sendFunds(admin, credentials, toAddress, coinNum, Convert.Unit.ETHER).sendAsync().get();

			return transactionReceipt;
		} catch (Exception e) {
			log.error("[{}]转账给[{}]{}个ETH异常:{}", fromAddress, toAddress, coinNum, e.toString());
			throw new RpcServiceException("转账异常");
		}

	}

	@Override
	public EthSendTransaction ethSendTransaction(String fromAddress, String fromPassword, String toAddress,
	        BigDecimal coinNum) throws RpcServiceException {

		try {

			Admin admin = AdminClient.newInstance();

			// 获取nonce
			EthGetTransactionCount ethGetTransactionCount = admin
			        .ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();

			BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			// 进行单位换算
			BigDecimal weiValue = Convert.toWei(coinNum, Convert.Unit.ETHER);

			// 创建交易记录实体 Transfer.GAS_PRICE, Transfer.GAS_LIMIT
			Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, GAS_PRICE, GAS_LIMIT,
			        toAddress, weiValue.toBigInteger());

			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, fromPassword).send();
			if (!personalUnlockAccount.accountUnlocked()) {
				return null;
			}

			// 提交交易
			return admin.ethSendTransaction(transaction).sendAsync().get();

		} catch (Exception e) {
			log.error("[{}]转账给[{}]{}个ETH异常:{}", fromAddress, toAddress, coinNum, e.toString());
			throw new RpcServiceException("转账异常");
		}
	}

	@Override
	public EthSendTransaction personalSendTransaction(String fromAddress, String fromPassword, String toAddress,
	        BigDecimal coinNum) throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();

			// 获取nonce
			EthGetTransactionCount ethGetTransactionCount = admin
			        .ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();

			BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			// 进行单位换算
			BigDecimal weiValue = Convert.toWei(coinNum, Convert.Unit.ETHER);

			// 创建交易记录实体 Transfer.GAS_PRICE, Transfer.GAS_LIMIT
			Transaction transaction = Transaction.createEtherTransaction(fromAddress, nonce, GAS_PRICE, GAS_LIMIT,
			        toAddress, weiValue.toBigInteger());

			EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, fromPassword).sendAsync()
			        .get();
			if (null == ethSendTransaction) {
				throw new RpcServiceException("资产转出失败");
			} else if (null != ethSendTransaction.getError()) {
				log.error("[{}]转账给[{}]{}个ETH异常:{}", fromAddress, toAddress, coinNum,
				        ethSendTransaction.getError().getMessage());
				throw new RpcServiceException(ethSendTransaction.getError().getMessage());
			}
			return ethSendTransaction;

		} catch (Exception e) {
			log.error("[{}]转账给[{}]{}个ETH异常:{}", fromAddress, toAddress, coinNum, e.toString());
			throw new RpcServiceException("转账异常");
		}
	}

	@Override
	public EthGasPrice ethGasPrice() throws RpcServiceException {

		try {
			Admin admin = AdminClient.newInstance();
			return admin.ethGasPrice().sendAsync().get();
		} catch (Exception e) {
			log.error("获取EthGasPrice异常:{}", e.getMessage());
			throw new RpcServiceException("获取EthGasPrice异常");
		}
	}
}
