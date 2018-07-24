package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.response.ApiResponse;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.domain.service.MnemonitService;
import pro.bechat.wallet.domain.service.UserService;

import java.util.logging.Logger;

/**
 * create IndexController by huc
 * 2018/4/19  上午6:4
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户业务接口", tags = {"user service"})
public class UserController {

    Logger logger = Logger.getLogger(UserController.class.getSimpleName());

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

}
