package pro.bechat.wallet.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.bechat.wallet.domain.model.response.Result;
import pro.bechat.wallet.domain.service.CodeService;
import pro.bechat.wallet.publics.PhoneUtils;

/**
 * @Author ch
 * @Date Create int 2018/7/24 15:42
 * @email 869360026@qq.com
 * 验证码控制器
 */
@RestController
@RequestMapping("/code")
@Api(value = "验证码接口", tags = {"code service"})
public class CodeController {

    @Autowired
    CodeService smsService;

    @GetMapping("/getCode")
    public Result getSMSCode(@RequestParam(name = "phone", defaultValue = "") String phone,
                             @RequestParam(name = "length", defaultValue = "0") int length) {
        try {
            if (phone == null || !PhoneUtils.isMobile0(phone)) {
                return Result.getErro("请输入正确的手机号码");
            }
            if (length < 4 || length > 10) {
                return Result.getErro("验证码长度不正确");
            }
            smsService.getCode(phone, length);
            return Result.getSuccess("发送短信成功");
        } catch (Exception e) {
            return Result.getErro("发送短信失败");
        }
    }

    @GetMapping("/check")
    public Result checkCode(@RequestParam(name = "phone", defaultValue = "") String phone,
                            @RequestParam(name = "code", defaultValue = "") String code) {
        try {
            //smsService.checkCode(phone, code);
            return Result.getSuccess("校验验证码成功");
        } catch (Exception e) {
            return Result.getErro(e.getMessage());
        }
    }
}
