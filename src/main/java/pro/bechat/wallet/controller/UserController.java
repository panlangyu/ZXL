package pro.bechat.wallet.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * @Author ch
 * @Date Create int 2018/7/25 17:20
 * @email 869360026@qq.com
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户相关", tags = {"校验cookie "})
public class UserController {
    Logger logger = Logger.getLogger(CommonController.class.getSimpleName());

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/user")
    public Result getUserById() {
        try {
            String token = request.getHeader("token");
            int id = 0;
            try {
                Claims claims = TokenUtil.verifyToken(token);
                id = Integer.parseInt(claims.getId());
            } catch (Exception e) {
                return Result.getErro("token校验失败");
            }
            return Result.getSuccess(userService.findUserById(id));
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }
}
