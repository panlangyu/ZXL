package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.User;

/**
 * create UserMapper by huc
 * 2018/4/20  下午2:23
 */
@Repository
public interface UserMapper extends BasicMapper<User>{
    /**
     * 根据联系人邀请码和状态查询
     * @return
     */
    User findUserByInvitationCodeAndStatus(@Param("invitationCode") String invitationCode,@Param("status") int status);

    /**
     * 根据手机号码和状态查询用户
     * @param tel
     * @param status
     * @return
     */
    User findUserByTelAndStatus(@Param("tel") String tel,@Param("status") int status);


    /**
     * 修改用户用户邀请码
     * @param id
     * @param invitationCode
     * @return
     */
    int updateInvitaionCode(@Param("id") int id,@Param("invitationCode") String invitationCode);

    /**
     * 根据用户id查询信息
     * @param id
     * @return
     */
    User findUserById(@Param("id") int id);
}
