package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.Code;

/**
 * @Author ch
 * @Date Create int 2018/7/24 17:25
 * @email 869360026@qq.com
 */
@Repository
public interface CodeMapper extends BasicMapper<Code>{
    /**
     * 获取用户最后一条短信
     * @param phone
     * @return
     */
    Code lastSmsCode(@Param("phone") String phone);

    /**
     * 修改验证码状态为已验证
     * @param id
     * @return
     */
    int update_status(@Param("id") long id);
}
