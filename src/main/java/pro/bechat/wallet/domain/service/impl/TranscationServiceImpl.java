package pro.bechat.wallet.domain.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.TranscationMapper;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.TranscationVo;
import pro.bechat.wallet.domain.service.TranscationService;
import pro.bechat.wallet.publics.CalendarUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户币种交易订单Service实现
 */
@Service
public class TranscationServiceImpl implements TranscationService {


    @Autowired
    private TranscationMapper transcationMapper;                //币种交易订单Mapper

    @Override
    public ApiResponseResult selectUserCoinTransactionList(Integer userId, String coinType) throws Exception {

        List<TranscationVo> voList = transcationMapper.selectUserCoinTransactionList(userId,coinType,null,null);

        return ApiResponseResult.build(200,"币种交易信息","查询用户币种交易信息",voList);
    }

    @Override
    public ApiResponseResult selectUserCoinTransactionListInfo(Integer userId,String startTime) throws Exception {

        String endTime = "";            //结束日期 30-31
        if(startTime != null && !startTime.equals("")){

            String [] str = CalendarUtil.assemblyDate(startTime);
            startTime = str[0];
            endTime = str[1];
        }

        List<TranscationVo> voList = transcationMapper.selectUserCoinTransactionList(userId,null,startTime,endTime);

        return ApiResponseResult.build(200,"币种交易信息","查询用户币种交易信息",voList);

    }

    @Override
    public ApiResponseResult selectUserCoinTransactionTotal(Integer userId, String startTime) throws Exception {

        Map<String,Object> map = new HashMap<>();

        String endTime = "";            //结束日期 30-31
        if(startTime != null && !startTime.equals("")){

            String [] str = CalendarUtil.assemblyDate(startTime);
            startTime = str[0];
            endTime = str[1];
        }
        //转入总额
        Double toChangeInto = transcationMapper.selectUserCoinToChargeInfoTotal(userId,startTime,endTime);

        map.put("toChangeInto",0);     //没有就为0
        if(toChangeInto != null && toChangeInto != 0){

            map.put("toChangeInto",toChangeInto);
        }

        //支出总额
        Double turnTo = transcationMapper.selectUserCoinTurnToTotal(userId,startTime,endTime);

        map.put("turnTo",0);           //没有就为0
        if(turnTo != null && turnTo != 0){

            map.put("turnTo",turnTo);
        }

        List<Map<String ,Object>> list = new ArrayList<>();         //封装成list  返回前端数据格式
        list.add(map);

        return ApiResponseResult.build(200,"收入和支出","查询币种交易记录收入支出总额",list);
    }

    @Override
    public ApiResponseResult selectUserCoinTrunToChargeTotal(Integer userId, String startTime) throws Exception {

        Map<String,Object> map = new HashMap<>();

        String endTime = "";            //结束日期 30-31

        if(startTime != null && !startTime.equals("")){

            String [] str = CalendarUtil.assemblyDate(startTime);
            startTime = str[0];
            endTime = str[1];
        }

        map = transcationMapper.selectUserCoinTrunToChargeTotal(userId,startTime,endTime);

        List<Map<String ,Object>> list = new ArrayList<>();         //封装成list  返回前端数据格式
        list.add(map);


        return ApiResponseResult.build(200,"收入和支出","查询币种交易记录收入支出总额",list);
    }

    @Override
    public ApiResponseResult selectWalletUserCoinTransactionList(Integer userId, String coinType) throws Exception {

        List<TranscationVo> voList = transcationMapper.selectWalletUserCoinTransactionList(userId,coinType);

        return ApiResponseResult.build(200,"币种交易","钱包管理用户币种交易记录",voList);
    }


}
