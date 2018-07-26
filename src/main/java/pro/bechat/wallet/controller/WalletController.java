package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
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

    /**
     * 查询用户个人钱包总额
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryUserWalletTotal",method = RequestMethod.POST)
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


    /**
     * 查询用户钱包币种列表
     * @param userId 用户编号
     * @param coinName 币种名称
     * @return
     */
    @RequestMapping(value = "/queryUserWalletCoinList",method = RequestMethod.POST)
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


    /**
     * 钱包转出到(交易所),提币操作
     * @param wallet 钱包对象
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyWalletTurnOut",method = RequestMethod.POST)
    public ApiResponseResult modifyWalletTurnOut(Wallet wallet, HttpServletRequest request){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = walletService.modifyWalletTurnOut(wallet);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2003,"error","出现异常","");
        }

        return apiResponse;
    }


    /**
     * 钱包管理币种列表信息 (直推总额) 和 (利息总额)
     * @param userId 用户编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryUserWalletCoinStraightOrInterest",method = RequestMethod.POST)
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


    /**
     * 管理钱包用户币种昨日收益 +(冻结数量)
     * @param userId 用户编号
     * @param coinId 币种编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryYesterdayProfit",method = RequestMethod.POST)
    public ApiResponseResult selectYesterdayProfit(@RequestParam("userId")Integer userId,
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
