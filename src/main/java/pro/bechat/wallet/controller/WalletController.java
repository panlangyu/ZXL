package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jetbrick.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.model.Wallet;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
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
@Api(value="钱包业务接口",tags={"wallet service"})
public class WalletController {

    Logger logger = Logger.getLogger(WalletController.class.getSimpleName());

    @Autowired
    private WalletService walletService;                //钱包业务Service


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
    public ApiResponseResult modifyWalletTurnOut(Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            if(StringUtils.isBlank(wallet.getRemark())){

                wallet.setRemark("");
            }

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



}
