package pro.bechat.wallet.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.response.ApiResponseResult;
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

    @PostMapping("/updateUserHeadPic")
    public Result updateUserPic(int id, String pic) {
        try {
            userService.updateUserPic(id, pic);
            return Result.getSuccess("修改用户头像成功");
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserNameAndSex(int id, String name,int sex,String pic) {
        try {
            userService.updateUser(id, name,sex,pic);
            return Result.getSuccess("修改用户头像成功");
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }


    @ApiOperation(value="用户信息以及ETH地址", notes="用户信息")
    @ApiImplicitParam(name="userId", value="用户编号", dataType="Integer")
    @RequestMapping(value="/queryUserAddressInfo", method= RequestMethod.GET)
    public ApiResponseResult queryUserAddressInfo(@RequestParam("userId")Integer userId){

        ApiResponseResult apiResponseResult = new ApiResponseResult();

        try{

            apiResponseResult = userService.findUserAddressInfo(userId);

        }catch (Exception e){

            return ApiResponseResult.build(2012,"error","出现异常","");
        }

        return apiResponseResult;
    }

}
