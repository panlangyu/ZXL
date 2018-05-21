package pro.bechat.wallet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.response.ApiResponse;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.RSAEncrypt;

import java.security.interfaces.RSAPrivateKey;
import java.util.logging.Logger;

/**
 * create IndexController by huc
 * 2018/4/19  上午6:4
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class.getSimpleName());

    @Value("${pro.bechat.wallet.privateKey}")
    String privateKeys;

    @Value("${pro.bechat.wallet.registerWallet}")
    String registerMethod;

    @Autowired
    UserService userService;

    RestTemplate template = new RestTemplate();

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public String signUp(@RequestBody User info)  {
        JSONObject jsonObject = new JSONObject();
        ApiResponse response = new ApiResponse();
        if (info.getMachineId() != null && info.getTransactionPW() != null){
            logger.info("info.getTransactionPW()---->"+info.getTransactionPW());
            try{
                User user = userService.selectByPrimaryKey(info.getMachineId());
                if (user == null){
                    RSAPrivateKey privateKey = RSAEncrypt.loadPrivateKey(privateKeys);
                    String newPass = RSAEncrypt.decrypt(privateKey,RSAEncrypt.strToBase64(info.getTransactionPW()));
                    info.setTransactionPW(newPass);
                    int rows = userService.save(info);
                    String methodUrl  = registerMethod + info.getUserId();
                    logger.info("registerMethod ---->" + methodUrl);
                    logger.info("user info ----->" + JSON.toJSONString(info));
                    if (rows > 0){
                        Thread.sleep(1);
                        ResponseEntity<String> result = template.getForEntity(methodUrl,String.class);
                        logger.info("wallet address---->" + result);

                    }
                    response.setCode(200);
                    jsonObject.put("UID",info.getUserId());
                    response.setData(jsonObject);
                }else{
                    jsonObject.put("UID",user.getUserId());
                    response.setData(jsonObject);
                }
                response.setMessage("请求成功");
            }catch (Exception e){
                logger.info(e.getMessage());
                response.setCode(-2);
                response.setMessage("加解密错误:" + e.getMessage());
            }
        }else {
            response.setCode(-1);
            response.setMessage("请输入设备ID和交易密码");
        }
        return  JSON.toJSONString(response);
    }


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public String index(){

        return "index ok.";
    }

}
