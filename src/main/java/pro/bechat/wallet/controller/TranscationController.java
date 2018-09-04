package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.service.TranscationService;

/**
 * 币种订单交易Controller
 * create TranscationController by huc
 * 2018/7/23  下午16:01
 */
@RestController
@RequestMapping("/transaction")
@Api(value="交易信息",tags={"交易信息"})
public class TranscationController {


    @Autowired
    private TranscationService transcationService;            //币种订单交易Service


    @ApiOperation(value="查询用户钱包下币种详情订单记录", notes="根据userId和coinType查询订单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="用户名",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="currentSize",value="用户id",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="coinType",value="币种名称",dataType="String", paramType = "query",required = true)
    })
    @RequestMapping(value="/queryUserCoinTransactionList",method=RequestMethod.GET)
    public ApiResponseResult queryUserCoinTransactionList(@RequestParam("currentPage")Integer currentPage,
                                                          @RequestParam("currentSize")Integer currentSize,
                                                          @RequestParam("userId")Integer userId,
                                                          @RequestParam("coinType")String coinType){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectUserCoinTransactionList(currentPage,currentSize,userId,coinType);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="查询用户钱包下币种详情订单记录,按条件查询", notes="根据userId或 加startTime查询订单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="用户名",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="currentSize",value="用户id",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="startTime",value="月份",dataType="String", paramType = "query",required = false)
    })
    @RequestMapping(value="/queryUserCoinTransactionListInfo",method=RequestMethod.GET)
    public ApiResponseResult queryUserCoinTransactionListInfo(@RequestParam("currentPage")Integer currentPage,
                                                              @RequestParam("currentSize")Integer currentSize,
                                                              @RequestParam("userId")Integer userId,
                                                              @RequestParam(value="startTime",required = false)String startTime){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectUserCoinTransactionListInfo(currentPage,currentSize,userId,startTime);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="查询用户币种交易记录 收入 和 支出", notes="根据userId或 加startTime查询收入和支出")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="startTime",value="月份",dataType="String", paramType = "query",required = false)
    })
    @RequestMapping(value="/queryUserCoinTransactionTotal",method=RequestMethod.GET)
    public ApiResponseResult queryUserCoinTransactionTotal(@RequestParam("userId")Integer userId ,
                                                           @RequestParam(value="startTime",required = false)String startTime){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectUserCoinTrunToChargeTotal(userId,startTime);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"error","出现异常","");
        }

        return apiResponse;
    }


    @ApiOperation(value="查询(钱包管理)用户币种交易记录", notes="根据userId和coinType查询钱包管理币种交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="currentPage",value="用户名",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="currentSize",value="用户id",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="userId",value="用户编号",dataType="Integer", paramType = "query",required = true),
            @ApiImplicitParam(name="coinType",value="币种名称",dataType="String", paramType = "query",required = true)
    })
    @RequestMapping(value="/queryWalletUserCoinTransactionList",method=RequestMethod.GET)
    public ApiResponseResult queryWalletUserCoinTransactionList(@RequestParam("currentPage")Integer currentPage,
                                                                @RequestParam("currentSize")Integer currentSize,
                                                                @RequestParam("userId")Integer userId,
                                                                @RequestParam("coinType")String coinType){

        ApiResponseResult apiResponse = new ApiResponseResult();

        try{

            apiResponse = transcationService.selectWalletUserCoinTransactionList(currentPage,currentSize,
                    userId,coinType,null);

        }catch (Exception e){
            e.printStackTrace();

            return ApiResponseResult.build(2004,"error","出现异常","");
        }

        return apiResponse;
    }



}
