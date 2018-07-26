package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.domain.service.MnemonitService;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * create IndexController by huc
 * 2018/4/19  上午6:4
 */
@RestController
@RequestMapping("/common")
@Api(value = "公共业务接口", tags = {"用户登陆登出、助记词获取等，不校验"})
public class CommonController {

    Logger logger = Logger.getLogger(CommonController.class.getSimpleName());

    @Autowired
    UserService userService;

    @Autowired
    MnemonitService mnemonitService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "index ok.";
    }


    @GetMapping(value = "/mnemonit")
    public Result productMnemonit() {
        try {
            return Result.getSuccess(mnemonitService.getMnemonit());
        } catch (Exception e) {
            return Result.getErro("资源不正确");
        }
    }


    @PostMapping("/register")
    public Result register(@RequestParam(name = "phone", defaultValue = "") String phone,
                           @RequestParam(name = "pass", defaultValue = "") String pass,
                           @RequestParam(name = "invitationCode", defaultValue = "") String invitationCode,
                           @RequestParam(name = "keyWords", defaultValue = "") String keyWords
    ) {
        try {
            userService.register(phone, pass, keyWords, invitationCode);
            return Result.getSuccess("注册成功");
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }


    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/login")
    public Result login(String tel, String password) {
        try {
            User user = userService.login(tel, password);
            String token = TokenUtil.generToken(user.getId() + "", null, null);
            response.addHeader("token",token);
            return Result.getSuccess(user);
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }


}