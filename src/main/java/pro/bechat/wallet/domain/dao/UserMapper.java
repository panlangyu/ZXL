package pro.bechat.wallet.domain.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pro.bechat.wallet.domain.model.model.User;

import java.util.List;

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
     * 修改用户用户状态
     * @param id
     * @param status
     * @return
     */
    int updateUserStatusById(@Param("id") int id,@Param("status") int status);


    /**
     * 根据用户id查询信息
     * @param id
     * @return
     */
    User findUserById(@Param("id") int id);

    /**
     * 用户修改性别和姓名
     * @param name
     * @param sex
     * @return
     */
    int updateUserNameAndSexAndPic(@Param("name") String name,@Param("sex") int sex,@Param("id")int id,@Param("pic") String pic);

    /**
     * 修改用户地址
     * @param picUrl
     * @return
     */
    int updateUserHeadPic(@Param("picUrl")String picUrl,@Param("id")int id);


    /**
     * 查询自己的直推用户
     * @param relationship
     * @return
     */
    List<User> findUserByShip(@Param("relationship") String relationship);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUsers(@Param("currentPage")Integer currentPage,
                            @Param("currentSize")Integer currentSize,@Param("search")String search);


    /**
     * 根据名字或者手机号码查询
     * @param search
     * @return
     */
    User findUserByNameOrPhenoe(@Param("search")String search);


    /**
     * 根据id查询
     * @param ids
     * @return
     */
    List<User> findUsersByIds(@Param("ids")List<String> ids);


    /**
     * 根据用户关系查询子类关系图
     * @param
     * @return
     */
    List<User> findLineUsersByShip(@Param("relationship")String relationship,@Param("relationship2")String relationship2);
}
