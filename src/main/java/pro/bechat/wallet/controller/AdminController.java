package pro.bechat.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.RewardConfigureUtils;

/**
 * @Author ch
 * @Date Create int 2018/7/31 19:52
 * @email 869360026@qq.com
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public Result login(String username, String password) {
        if(username.equals("admin") &&password.equals("123456")){
            return Result.getSuccess("登陆成功");
        }
        return Result.getErro("登陆失败");
    }


    @GetMapping("/userList")
    public Result userList(int page,String keyWorkd){
        try {
            return Result.getSuccess(userService.findAllUser(10, page, keyWorkd));
        }catch (Exception e){
            return Result.getErro(e.getMessage());
        }
    }


    /**
     * 获取我直推用户
     * @param content
     * @return
     */
    @GetMapping("/childrenUsers")
    public Result childrenUsers(String content){
        try {
            return Result.getSuccess(userService.adminGetChildren(content));
        }catch (Exception e){
            return Result.getErro(e.getMessage());
        }
    }

    @GetMapping("/parentUsers")
    public Result getLineShip(int page,int size,String content){
        try {
            return Result.getSuccess(userService.adminGetLineUsers(page,size,content));
        }catch (Exception e){
            return Result.getErro(e.getMessage());
        }
    }

    @GetMapping("/getConfig")
    public Result getConfig(){
        return Result.getSuccess(RewardConfigureUtils.getInstance());
    }

    @PostMapping("/updateConfig")
    public Result updateConfig(RewardConfigureUtils utils){
        utils.update();
        return Result.getSuccess("修改成功");
    }

}
