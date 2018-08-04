package pro.bechat.wallet.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.DictionaryMapper;
import pro.bechat.wallet.domain.dao.SystemMapper;
import pro.bechat.wallet.domain.model.model.Admin;
import pro.bechat.wallet.domain.model.model.SystemStatistics;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.publics.SpringUtil;


import java.util.List;

/**
 * @Author ch
 * @Date Create int 2018/8/3 16:35
 * @email 869360026@qq.com
 */
@Service
public class SystemStatisticsService extends BasicService<SystemMapper> {
    @Autowired
    SystemMapper systemMapper;

    @Autowired
    DictionaryMapper dictionaryMapper;
    @Override
    protected BasicMapper basicMapper() {
        return systemMapper;
    }

    public SystemStatistics getStatices(){
        return systemMapper.selectStatistics();
    }


    /**
     * 获得手续费配置
     * @return
     */
    public List selectCharge() throws Exception {
        return dictionaryMapper.selectDictionaryList("DEDUCT_FORMALITIES");
    }


    public int updateCharge(int id,String value){
       return  dictionaryMapper.updateDictionaryValueById(id,value);
    }




    /**
     * 用户退出
     * @return
     */
    public Result exit(){
       Admin adminSerssion =  SpringUtil.getBean(Admin.class);
        adminSerssion.setId(0);
        adminSerssion.setUserName(null);
        return Result.getSuccess("退出成功");
    }

    /**
     * 将登录的用户存入session
     * @param
     */
    public void addSession(String name){
        Admin adminSerssion =  SpringUtil.getBean(Admin.class);
        adminSerssion.setId(0);
        adminSerssion.setUserName(name);
    }

}
