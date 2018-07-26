package pro.bechat.wallet.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.bechat.wallet.domain.model.response.ApiResponse;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.service.TranscationService;

/**
 * 币种订单交易Controller
 * create TranscationController by huc
 * 2018/7/23  下午16:01
 */
@RestController
@RequestMapping("/transaction")
@Api(value="钱包业务接口",tags={"transaction service"})
public class TranscationController {


    @Autowired
    private TranscationService transcationService;            //币种订单交易Service

    /**
     * 查询用户钱包下币种详情订单记录
     * @param userId
     * @param coinType
     * @return
     */
    @RequestMapping(value="/queryUserCoinTransactionList",method=RequestMethod.POST)
    public ApiResponseResult queryUserCoinTransactionList(@RequestParam("userId")Integer userId
                                                        ,@RequestParam("coinType")String coinType){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectUserCoinTransactionList(userId,coinType);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"出现异常","出现异常","");
        }

        return apiResponse;
    }


    /**
     * 查询用户钱包下币种详情订单记录,按条件查询
     * @param userId
     * @param startTime
     * @return
     */
    @RequestMapping(value="/queryUserCoinTransactionListInfo",method=RequestMethod.POST)
    public ApiResponseResult queryUserCoinTransactionListInfo(@RequestParam("userId")Integer userId
                                                            ,@RequestParam(value="startTime",required = false)String startTime){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectUserCoinTransactionListInfo(userId,startTime);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"出现异常","出现异常","");
        }

        return apiResponse;
    }


    /**
     * 查询用户币种交易记录 收入 和 支出
     * @param userId
     * @param startTime
     * @return
     */
    @RequestMapping(value="/queryUserCoinTransactionTotal",method=RequestMethod.POST)
    public ApiResponseResult queryUserCoinTransactionTotal(@RequestParam("userId")Integer userId
                                                        ,@RequestParam(value="startTime",required = false)String startTime){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            //apiResponse = transcationService.selectUserCoinTransactionTotal(userId,startTime);

            apiResponse = transcationService.selectUserCoinTrunToChargeTotal(userId,startTime);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"出现异常","出现异常","");
        }

        return apiResponse;
    }


    /**
     * 查询(钱包管理)用户币种交易记录
     * @param userId
     * @param coinType
     * @return
     */
    @RequestMapping(value="/queryWalletUserCoinTransactionList",method=RequestMethod.POST)
    public ApiResponseResult queryWalletUserCoinTransactionList(@RequestParam("userId")Integer userId,
                                                                @RequestParam("coinType")String coinType){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectWalletUserCoinTransactionList(userId,coinType);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"出现异常","出现异常","");
        }

        return apiResponse;
    }



}
