package pro.bechat.wallet.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.RewardConfigureUtils;

/**
 * @Author ch
 * @Date Create int 2018/8/1 16:24
 * @email 869360026@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test {
    @Autowired
    UserService userService;

    @org.junit.Test
    public void test(){
        RewardConfigureUtils configureUtils = RewardConfigureUtils.getInstance();
        System.out.println(configureUtils);
    }
}
