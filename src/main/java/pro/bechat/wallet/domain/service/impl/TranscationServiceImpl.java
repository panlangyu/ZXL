package pro.bechat.wallet.domain.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.TranscationMapper;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
import pro.bechat.wallet.domain.model.vo.TranscationVo;
import pro.bechat.wallet.domain.service.TranscationService;
import pro.bechat.wallet.publics.CalendarUtil;
import pro.bechat.wallet.publics.PageBean;
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
    public ApiResponseResult selectUserCoinTransactionList(Integer currentPage,Integer currentSize,
                                                           Integer userId, String coinType) throws Exception {

        PageHelper.startPage(currentPage,currentSize);

        List<TranscationVo> voList = transcationMapper.selectUserCoinTransactionList(currentPage,currentSize,userId,coinType);

        if(null == voList){
            return ApiResponseResult.build(2010,"error","未查询用户钱包币种交易记录转入和转出","");
        }

        PageInfo<TranscationVo> pageInfo = new PageInfo<>(voList);

        PageBean<TranscationVo> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentSize(currentSize);
        pageBean.setTotalNum(pageInfo.getTotal());
        pageBean.setItems(voList);

        return ApiResponseResult.build(200,"success","查询用户钱包币种交易记录转入和转出",pageBean);
    }

    @Override
    public ApiResponseResult selectUserCoinTransactionListInfo(Integer currentPage,Integer currentSize,
                                                               Integer userId,String startTime) throws Exception {

        String endTime = "";            //结束日期 30-31
        if(startTime != null && !startTime.equals("")){

            String [] str = CalendarUtil.assemblyDate(startTime);
            startTime = str[0];
            endTime = str[1];
        }

        PageHelper.startPage(currentPage,currentSize);

        List<TranscationVo> voList = transcationMapper.selectWalletUserCoinTransactionList(currentPage,currentSize,
                userId,null,startTime,endTime);

        if(null == voList){

            return ApiResponseResult.build(2010,"error","未查询用户币种交易信息","");
        }

        PageInfo<TranscationVo> pageInfo = new PageInfo<>(voList);

        PageBean<TranscationVo> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentSize(currentSize);
        pageBean.setTotalNum(pageInfo.getTotal());
        pageBean.setItems(voList);

        return ApiResponseResult.build(200,"success","查询用户币种交易信息",pageBean);
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

        return ApiResponseResult.build(200,"success","查询币种交易记录收入支出总额",list);
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

        return ApiResponseResult.build(200,"success","查询币种交易记录收入支出总额",list);
    }


    @Override
    public ApiResponseResult selectWalletUserCoinTransactionList(Integer currentPage, Integer currentSize,
                                                                 Integer userId, String coinType,String startTime) throws Exception {

        PageHelper.startPage(currentPage,currentSize);

        List<TranscationVo> voList = transcationMapper.selectWalletUserCoinTransactionList(currentPage,currentSize,
                userId,coinType,startTime,null);

        if(null == voList){

            return ApiResponseResult.build(2010,"error","未钱包管理用户币种交易记录","");
        }

        PageInfo<TranscationVo> voPageInfo = new PageInfo<>(voList);

        PageBean<TranscationVo> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentSize(currentSize);
        pageBean.setTotalNum(voPageInfo.getTotal());
        pageBean.setItems(voList);

        return ApiResponseResult.build(200,"success","钱包管理用户币种交易记录",pageBean);
    }


}
