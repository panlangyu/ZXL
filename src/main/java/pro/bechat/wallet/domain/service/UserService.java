package pro.bechat.wallet.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.bechat.wallet.domain.dao.BasicMapper;
import pro.bechat.wallet.domain.dao.UserMapper;
import pro.bechat.wallet.domain.model.model.User;

/**
 * create UserService by huc
 * 2018/7/9  上午10:34
 */
@Service
public class UserService extends BasicService<User> {

    @Autowired
    UserMapper userMapper;

    @Override
    protected BasicMapper basicMapper() {
        return userMapper;
    }
}
