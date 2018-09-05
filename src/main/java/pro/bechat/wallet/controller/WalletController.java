package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.UserWalletVo;
import pro.bechat.wallet.domain.model.vo.WalletContractVo;
import pro.bechat.wallet.domain.model.vo.WalletUtilsVo;
import pro.bechat.wallet.domain.service.WalletService;
import javax.servlet.http.HttpServletRequest;

/**
 * 钱包Controller
 * create WalletController by huc
 * 2018/7/23  上午12:06
 */
@RestController
@RequestMapping("/wallet")
@Api(value="钱包业务接口",tags={"钱包功能"})
public class WalletController {


    @Autowired
    private WalletService walletService;                //钱包业务Service


    @ApiOperation(value="用户创建钱包", notes="创建钱包")
    @ApiImplicitParam(name="user", value="用户对象user", dataType="User")
    @RequestMapping(value="/createWalletInfo", method=RequestMethod.POST)
    public ApiResponseResult createWalletInfo(@RequestBody UserWalletVo user) {

        return walletService.createWalletInfo(user);
    }

    @ApiOperation(value="查询用户钱包币种列表", notes="根据用户编号查询用户钱包信息,调用第三方接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="id", value="币种编号", dataType="Integer", paramType="query", required=false)
    })
    @RequestMapping(value="/queryUserWalletList", method=RequestMethod.GET)
    public ApiResponseResult queryUserWalletList(@RequestParam("userId") Integer userId,
                                                 @RequestParam(value="id", required=false) Integer id) {

         return walletService.findUserWalletList(userId,id);
    }

    @ApiOperation(value="用户提币", notes="调用第三方转账")
    @ApiImplicitParam(name="wallet", value="钱包对象wallet", dataType="Wallet")
    @RequestMapping(value="/modifyWithdrawMoney", method=RequestMethod.POST)
    public ApiResponseResult modifyWithdrawMoney(@RequestBody WalletUtilsVo wallet,
                                                 HttpServletRequest request){

          return walletService.modifyWithdrawMoney(wallet);
    }

    @ApiOperation(value="查询合约币信息", notes="合约币")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="contractAddr", value="合约币地址", dataType="String", paramType="query", required=true)
    })
    @RequestMapping(value="/queryContractAddr", method=RequestMethod.GET)
    public ApiResponseResult queryContractAddr(@RequestParam("userId") Integer userId,
                                               @RequestParam("contractAddr") String contractAddr) {

         return walletService.queryContractAddr(userId, contractAddr);
    }


    @ApiOperation(value="新增合约币信息", notes="增加合约币")
    @ApiImplicitParam(name="walletContractVo", value="合约币对象walletContractVo", dataType="WalletContractVo")
    @RequestMapping(value="/createContractWalletInfo", method=RequestMethod.POST)
    public ApiResponseResult createContractWalletInfo(@RequestBody WalletContractVo walletContractVo) {

         return walletService.createContractWalletInfo(walletContractVo);
    }


    @ApiOperation(value="查询用户已有的币种信息", notes="币种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="list", value="币种信息", dataType="String", paramType="query", required=true)
    })
    @RequestMapping(value="/queryUserWalletListStatus", method= RequestMethod.GET)
    public ApiResponseResult queryUserWalletListStatus(@RequestParam("userId")Integer userId,
                                                       @RequestParam("list")String  list) {

        return walletService.findUserWalletListStatus(userId,list);
    }

    @ApiOperation(value="删除用户合约币信息", notes="合约币")
    @ApiImplicitParam(name="wallet", value="钱包对象wallet", dataType="Wallet")
    @RequestMapping(value="/deleteUserContractAddr", method= RequestMethod.POST)
    public ApiResponseResult deleteUserContractAddr(@RequestBody Wallet wallet) {

        return walletService.deleteContractAddrInfo(wallet);
    }

    @ApiOperation(value="查询所有账户", notes="所有账户")
    @RequestMapping(value="/queryAccountInfo", method= RequestMethod.GET)
    public ApiResponseResult queryAccountInfo() {

        return walletService.queryAccountList();
    }

    @ApiOperation(value="查询阻塞数信息", notes="阻塞数")
    @RequestMapping(value="/blockNumber", method=RequestMethod.GET)
    public ApiResponseResult blockNumber() {

        return walletService.blockNumber();
    }



}
