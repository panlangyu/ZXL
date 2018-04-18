package pro.bechat.wallet.business.service.iface;

import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import pro.bechat.wallet.domain.exception.RpcServiceException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
* @ClassName: IEthereumService 
* @author 麦子 wheat235@gmail.com
 */
public interface IEthereumService {

	/**
	 * @Title: getAccountlist
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 获取账户列表
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return List<String>
	 */
	List<String> getAccountlist() throws RpcServiceException;

	/**
	 * @Title: createAccount
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 创建账户
	 * @param password:密码
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return String
	 */
	String createAccount(String password) throws RpcServiceException;

	/**
	 * @Title: getBalance
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 获取用户余额
	 * @param address:账户
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return BigInteger
	 */
	BigInteger getBalance(String address) throws RpcServiceException;

	/**
	 * @Title: getTransactionCount
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 获取用户交易记录数
	 * @param address:账户
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return BigInteger
	 */
	BigInteger getTransactionCount(String address) throws RpcServiceException;

	/**
	 * @Title: getTransaction
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 获取交易信息
	 * @param transactionHash:交易的hash值
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return EthTransaction
	 */
	EthTransaction getTransaction(String transactionHash) throws RpcServiceException;

	/**
	 * @Title: getAddress
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 获取用户的账户
	 * @param walletfile:秘钥文件
	 * @param password：密码
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return String
	 * @throws
	 */
	String getAddress(String walletfile, String password) throws RpcServiceException;

	/**
	 * @Title: ethSendRawTransaction
	 * @author 麦子 wheat235@gmail.com
	 * @Description: 转账
	 * @param walletfile:秘钥文件
	 * @param password：密码
	 * @param toAddress:接受地址
	 * @param coinNum：数量
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return EthSendTransaction
	 */
	EthSendTransaction ethSendRawTransaction(String walletfile, String password, String toAddress, BigDecimal coinNum) throws RpcServiceException;

	/**
	 * @Title: transferSendFunds
	 * @author 麦子 wheat235@gmail.com
	 * @date 2018年4月1日 上午11:33:36
	 * @Description: 转账
	 * @param walletfile:秘钥文件
	 * @param password：密码
	 * @param toAddress:收款账户
	 * @param coinNum：数量
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return TransactionReceipt
	 */
	TransactionReceipt transferSendFunds(String walletfile, String password, String toAddress, BigDecimal coinNum) throws RpcServiceException;

	/**
	 * @Title: ethSendTransaction
	 * @author 麦子 wheat235@gmail.com
	 * @date 2018年4月1日 上午11:34:03
	 * @Description: 转账
	 * @param fromAddress:付款账户
	 * @param fromPassword:付款账户密码
	 * @param toAddress:收款账户
	 * @param coinNum：数量
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return EthSendTransaction
	 */
	EthSendTransaction ethSendTransaction(String fromAddress, String fromPassword, String toAddress, BigDecimal coinNum) throws RpcServiceException;

	EthGasPrice ethGasPrice() throws RpcServiceException;

	/**
	 * @Title: personalSendTransaction
	 * @author 麦子 wheat235@gmail.com
	 * @date 2018年4月1日 上午11:35:45
	 * @Description: 转账
	 * @param fromAddress:付款账户
	 * @param fromPassword:付款账户密码
	 * @param toAddress:收款账户
	 * @param coinNum：数量
	 * @param ip:服务地址
	 * @param port:端口
	 * @throws RpcServiceException
	 * @return EthSendTransaction
	 */
	EthSendTransaction personalSendTransaction(String fromAddress, String fromPassword, String toAddress,
	        BigDecimal coinNum) throws RpcServiceException;

}
