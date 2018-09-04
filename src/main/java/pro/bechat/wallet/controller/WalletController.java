package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jetbrick.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.UserWalletVo;
import pro.bechat.wallet.domain.model.vo.WalletContractVo;
import pro.bechat.wallet.domain.model.vo.WalletUtilsVo;
import pro.bechat.wallet.domain.service.WalletService;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * 钱包Controller
 * create WalletController by huc
 * 2018/7/23  上午12:06
 */
@RestController
@RequestMapping("/wallet")
@Api(value="钱包业务接口",tags={"钱包功能"})
public class WalletController {

    Logger logger = Logger.getLogger(WalletController.class.getSimpleName());

    @Autowired
    private WalletService walletService;                //钱包业务Service

    /*
    @ApiOperation(value="查询用户个人钱包总额", notes="根据userId查询个人钱包币种数量总额")
    @ApiImplicitParam(name = "userId", value = "用户编号userId",dataType="Integer", paramType = "query",required = true)
    @RequestMapping(value = "/queryUserWalletTotal",method = RequestMethod.GET)
    public ApiResponseResult queryUserWalletTotal(@RequestParam("userId")Integer userId){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.selectUserWalletTotal(userId);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2001,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="查询用户钱包币种列表", notes="根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="currentSize",value="页面容量",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="coinName",value="币种名称",dataType="String", paramType = "query",required = false)
    })
    @RequestMapping(value = "/queryUserWalletCoinList",method = RequestMethod.GET)
    public ApiResponseResult queryUserWalletCoinList(@RequestParam("currentPage")Integer currentPage,
                                                     @RequestParam("currentSize")Integer currentSize,
                                                     @RequestParam("userId")Integer userId,
                                                     @RequestParam(value="coinName",required = false)String coinName){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

             apiResponse = walletService.selectUserWalletCoinList(currentPage,currentSize,userId,coinName);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2002,"error","出现异常","");
        }
        return apiResponse;
    }


    @ApiOperation(value="钱包转出到(交易所),提币操作", notes="钱包提币操作")
    @ApiImplicitParam(name = "wallet", value = "钱包对象wallet",dataType="Wallet")
    @RequestMapping(value = "/modifyWalletTurnOut",method = RequestMethod.POST)
    public ApiResponseResult modifyWalletTurnOut(@RequestBody Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.modifyWalletTurnOut(wallet);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2003,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="钱包管理币种列表信息 (直推总额) 和 (利息总额)", notes="根据userId查询钱包管理币种列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="当前页码",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="currentSize",value="页面容量",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true)
    })
    @RequestMapping(value = "/queryUserWalletCoinStraightOrInterest",method = RequestMethod.GET)
    public ApiResponseResult queryUserWalletCoinStraightOrInterest(@RequestParam("currentPage") Integer currentPage,
                                                                   @RequestParam("currentSize") Integer currentSize,
                                                                   @RequestParam("userId")Integer userId,
                                                                   HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.selectUserWalletCoinStraightOrInterest(currentPage,currentSize,userId);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="管理钱包用户币种昨日收益 +(冻结数量)", notes="根据userId和coinId查询昨日收益")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="coinId",value="币种编号",dataType="Integer", paramType = "query",required = true)
    })
    @RequestMapping(value = "/queryYesterdayProfit",method = RequestMethod.GET)
    public ApiResponseResult queryYesterdayProfit(@RequestParam("userId")Integer userId,
                                                   @RequestParam("coinId")Integer coinId,
                                                   HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.selectYesterdayProfit(userId,coinId);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2007,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="钱包管理(转入) 本金不发生改变,冻结增加和可用减少", notes="钱包管理(转入)")
    @ApiImplicitParam(name = "wallet", value = "钱包对象wallet",dataType="Wallet")
    @RequestMapping(value = "/modifyWalletDepositToChangeInfo",method = RequestMethod.POST)
    public ApiResponseResult modifyWalletDepositToChangeInfo(@RequestBody Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.modifyWalletDepositToChangeInto(wallet);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2016,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="钱包管理(转出) 本金不发生改变,冻结减少和可用发生增加", notes="钱包管理(转出)")
    @ApiImplicitParam(name = "wallet", value = "钱包对象wallet",dataType="Wallet")
    @RequestMapping(value = "/modifyWalletDepositTurnTo",method = RequestMethod.POST)
    public ApiResponseResult modifyWalletDepositTurnTo(@RequestBody Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.modifyWalletDepositTurnOut(wallet);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2016,"error","出现异常","");
        }

        return apiResponse;
    }



    @ApiOperation(value="用户充币信息", notes="用户充币")
    @ApiImplicitParam(name = "wallet", value = "钱包对象wallet",dataType="Wallet")
    @RequestMapping(value = "/modifyChargeMoneyInfo",method = RequestMethod.POST)
    public ApiResponseResult modifyChargeMoneyInfo(@RequestBody Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.modifyChargeMoneyInfo(wallet);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2017,"error","出现异常","");
        }

        return apiResponse;
    }
*/

    /*@ApiOperation(value="用户充币信息", notes="用户充币")
    @RequestMapping(value = "/modifyChargeMoneyInfos",method = RequestMethod.POST)
    public ApiResponseResult modifyChargeMoneyInfos( HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        Integer num = null;
        try{

            num = walletService.insertUserWalletInfo(17);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2017,"error","出现异常","");
        }

        return ApiResponseResult.build(200,"success","成功",num);
    }*/



    @ApiOperation(value="用户创建钱包", notes="创建钱包")
    @ApiImplicitParam(name="user", value="用户对象user", dataType="User")
    @RequestMapping(value="/createWalletInfo", method=RequestMethod.POST)
    public ApiResponseResult createWalletInfo(@RequestBody UserWalletVo user,
                                              HttpServletRequest request) {

        ApiResponseResult apiResponse = new ApiResponseResult();
        try {

            apiResponse = walletService.createWalletInfo(user);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2017, "error", "出现异常", "");
        }
        return apiResponse;
    }

    @ApiOperation(value="查询用户钱包币种列表", notes="根据用户编号查询用户钱包信息,调用第三方接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="id", value="币种编号", dataType="Integer", paramType="query", required=false)
    })
    @RequestMapping(value="/queryUserWalletList", method=RequestMethod.GET)
    public ApiResponseResult queryUserWalletList(@RequestParam("userId") Integer userId,
                                                 @RequestParam(value="id", required=false) Integer id) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.findUserWalletList(userId,id);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2011, "error", "出现异常", "");
        }
        return apiResponseResult;
    }

    @ApiOperation(value="用户提币", notes="调用第三方转账")
    @ApiImplicitParam(name="wallet", value="钱包对象wallet", dataType="Wallet")
    @RequestMapping(value="/modifyWithdrawMoney", method=RequestMethod.POST)
    public ApiResponseResult modifyWithdrawMoney(@RequestBody WalletUtilsVo wallet,
                                                 HttpServletRequest request){

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.modifyWithdrawMoney(wallet);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2017, "error", "出现异常", "");
        }
        return apiResponseResult;
    }

    @ApiOperation(value="查询合约币信息", notes="合约币")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="contractAddr", value="合约币地址", dataType="String", paramType="query", required=true)
    })
    @RequestMapping(value="/queryContractAddr", method=RequestMethod.GET)
    public ApiResponseResult queryContractAddr(@RequestParam("userId") Integer userId,
                                               @RequestParam("contractAddr") String contractAddr) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.queryContractAddr(userId, contractAddr);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }
        return apiResponseResult;
    }


    @ApiOperation(value="新增合约币信息", notes="增加合约币")
    @ApiImplicitParam(name="walletContractVo", value="合约币对象walletContractVo", dataType="WalletContractVo")
    @RequestMapping(value="/createContractWalletInfo", method=RequestMethod.POST)
    public ApiResponseResult createContractWalletInfo(@RequestBody WalletContractVo walletContractVo) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();

        try {

            apiResponseResult = walletService.createContractWalletInfo(walletContractVo);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2017, "error", "出现异常", "");
        }
        return apiResponseResult;
    }


    @ApiOperation(value="查询用户已有的币种信息", notes="币种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer", paramType="query", required=true),
            @ApiImplicitParam(name="list", value="币种信息", dataType="String", paramType="query", required=true)
    })
    @RequestMapping(value="/queryUserWalletListStatus", method= RequestMethod.GET)
    public ApiResponseResult queryUserWalletListStatus(@RequestParam("userId")Integer userId,
                                                       @RequestParam("list")String  list) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.findUserWalletListStatus(userId,list);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }
        return apiResponseResult;
    }

    @ApiOperation(value="删除用户合约币信息", notes="合约币")
    @ApiImplicitParam(name="wallet", value="钱包对象wallet", dataType="Wallet")
    @RequestMapping(value="/deleteUserContractAddr", method= RequestMethod.POST)
    public ApiResponseResult deleteUserContractAddr(@RequestBody Wallet wallet) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.deleteContractAddrInfo(wallet);

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }

        return apiResponseResult;
    }



    @ApiOperation(value="查询所有账户", notes="所有账户")
    @RequestMapping(value="/queryAccountInfo", method= RequestMethod.GET)
    public ApiResponseResult queryAccountInfo() {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.queryAccountList();

        } catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }
        return apiResponseResult;
    }

    @ApiOperation(value="查询阻塞数信息", notes="阻塞数")
    @RequestMapping(value="/blockNumber", method=RequestMethod.GET)
    public ApiResponseResult blockNumber() {

        return this.walletService.blockNumber();
    }






    /*@ApiOperation(value="查询两个用户之间的币种比较", notes="币种比较")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone", value="手机号", dataType="String", paramType="query", required=true),
            @ApiImplicitParam(name="earnerPhone", value="对方手机号", dataType="String", paramType="query", required=true)
    })
    @RequestMapping(value="/queryUserWalletInfo", method=RequestMethod.GET)
    public ApiResponseResult queryUserWalletInfo(@RequestParam("phone") String phone,
                                                 @RequestParam("earnerPhone") String earnerPhone) {

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.queryUserWalletInfo(phone, earnerPhone);

        }
        catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }

        return apiResponseResult;
    }*/



   /* @ApiOperation(value="查询用户下拥有的币种", notes="币种名称")
    @ApiImplicitParam(name="phone", value="手机号", dataType="String", paramType="query", required=true)
    @RequestMapping(value="/queryWalletListInfo", method=RequestMethod.GET)
    public ApiResponseResult queryWalletListInfo(@RequestParam("phone")String phone){

        ApiResponseResult apiResponseResult = new ApiResponseResult();
        try {

            apiResponseResult = walletService.findWalletListInfo(phone);

        }
        catch (Exception e) {
            e.printStackTrace();

            return ApiResponseResult.build(2016, "error", "出现异常", "");
        }

        return apiResponseResult;
    }*/




}
