package pro.bechat.wallet.domain.service;

import jetbrick.util.codec.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.UserMapper;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.publics.InvitationCodeUtils;
import pro.bechat.wallet.publics.MnemonitUtitls;
import pro.bechat.wallet.publics.PhoneUtils;

import java.util.Date;
import java.util.logging.Logger;

/**
 * create UserService by huc
 * 2018/7/9  上午10:34
 * 20180725 新增注册 登陆等
 */
@Service
public class UserService extends BasicService<User> {
    Logger logger = Logger.getLogger("UserService");
    @Autowired
    UserMapper userMapper;

    @Override
    protected BasicMapper basicMapper() {
        return userMapper;
    }


    /**
     * 注册成功后返回
     *
     * @return
     */
    public void register(String phone, String pass, String keyWord, String invitationCode) throws Exception {
        //校验参数是否存在
        if (!PhoneUtils.isMobile0(phone)) {
            throw new Exception("手机号码不正确");
        }
        if (null == pass || pass.length() != 6) {
            throw new Exception("密码不正确");
        }
        if (null == keyWord || keyWord.length() == 0) {
            throw new Exception("助记词长度不正确");
        }

        //1判断邀请码是否存在 或者错误，生成用户关系
        String relationship = "";
        if (invitationCode == null || invitationCode.length() == 0) {
            //当用户没有填写邀请码
            relationship = "0";
        } else {
            //数据库中根据手机号码查询邀请人（邀请人状态不为出局），并且生成用户关系。
            User user = userMapper.findUserByInvitationCodeAndStatus(invitationCode, User.STATUS_NOMARL);
            if (user == null) {
                throw new Exception("请确认邀请码是否正确");
            }
            relationship = user.getRelationship() + "," + user.getId();
        }

        //2判断用户是否注册过。
        User user = userMapper.findUserByTelAndStatus(phone, User.STATUS_NOMARL);
        if (user != null) {
            throw new Exception("用户已经注册过");
        }


        //开始装配用户对象
        user = new User();
        user.setNickName(MnemonitUtitls.getRandomName());
        //将助记词作为salt
        user.setPasswd(MD5Utils.md5Hex(pass + keyWord));
        user.setSeeds("");
        user.setHeadImg("");
        user.setStatus(User.STATUS_NOMARL);
        user.setCreateTime(new Date());
        user.setPhoneNum(phone);
        user.setUpdateTime(new Date());
        user.setRelationship(relationship);
        user.setInvitationCode("");
        user.setMnemonit(keyWord);
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new Exception("创建用户失败");
        }
        try {
            //生成用户id
            userMapper.updateInvitaionCode(user.getId(), InvitationCodeUtils.getInvitaionCode(user.getId()));
        } catch (Exception e) {
            logger.warning("生成用户邀请码错误");
        }
    }


    public User login(String tel, String pass) throws Exception {
        if (!PhoneUtils.isMobile0(tel)) {
            throw new Exception("您输入的手机号码不正确");
        }
        if (null == pass || pass.length() != 6) {
            throw new Exception("请输入正确的登陆密码");
        }
        //判断用户输入是否正确
        User user = userMapper.findUserByTelAndStatus(tel, User.STATUS_NOMARL);
        if (null == user) {
            throw new Exception("用户不存在");
        }
        if (user.getPasswd().equals(MD5Utils.md5Hex(pass + user.getMnemonit()))) {
            user.setPasswd("");
            user.setSeeds("");
            user.setMnemonit("");
            return user;
        } else {
            throw new Exception("密码与账户不符合");
        }
    }


    public User findUserById(int id) throws Exception {
        User user = null;
        try {
            user = userMapper.findUserById(id);
        } catch (Exception e) {
            throw new Exception("系统异常");
        }
        if (null == user) {
            throw new Exception("用户不存在");
        }
        return user;
    }

    /**
     * 查询用户
     *
     * @param nickName
     * @param sex
     * @param
     */
    public void updateUser(int id, String nickName, int sex,String pic) throws Exception {
        if (sex != 1 && sex != 0) {
            throw new Exception("用户性别错误");
        }
        if (nickName == null || nickName.equals("")) {
            throw new Exception("请输入正确的用户名");
        }
        int i = userMapper.updateUserNameAndSexAndPic(nickName, sex, id,pic);
        if (i == 1) {
            return;
        } else {
            throw new Exception("修改用户信息失败");
        }
    }

    public void updateUserPic(int id, String pic) throws Exception {
        if (pic == null || !pic.contains("http:")) {
            throw new Exception("传递图片地址错误");
        }
        int i = userMapper.updateUserHeadPic(pic, id);
        if (i == 1) {
            return;
        } else {
            throw new Exception("修改用户信息失败");
        }
    }

}
