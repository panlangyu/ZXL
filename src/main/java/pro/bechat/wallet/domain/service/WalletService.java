package pro.bechat.wallet.domain.service;

import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.UserWalletVo;
import pro.bechat.wallet.domain.model.vo.WalletContractVo;
import pro.bechat.wallet.domain.model.vo.WalletUtilsVo;

/**
 * 钱包业务Service
 * create WalletMapper by huc
 * 2018/7/23  上午12:03
 */
public interface WalletService {


    /** 新增用户钱包(ETH)币种信息 **/
    Integer insertUserWalletInfo(Integer userId);

    /** 创建钱包 ETH **/
    ApiResponseResult createWalletInfo(UserWalletVo user) ;

    /** 查询钱包列表 **/
    ApiResponseResult findUserWalletList(Integer userId, Integer id) ;

    /** 钱包转账 **/
    ApiResponseResult modifyWithdrawMoney(WalletUtilsVo walletUtilsVo) ;

    /** 查询合约币是否存在 **/
    ApiResponseResult queryContractAddr(Integer userId, String contractAddr) ;

    /** 查询账户总数 **/
    ApiResponseResult queryAccountList() ;

    /** 查询阻塞书 **/
    ApiResponseResult blockNumber();

    /** 新增合约币信息 **/
    ApiResponseResult createContractWalletInfo(WalletContractVo walletContractVo);

    /** 用户已拥有币种 **/
    ApiResponseResult findUserWalletListStatus(Integer userId,String list);

    /** 删除合约币信息 **/
    ApiResponseResult deleteContractAddrInfo(Wallet wallet);




}
