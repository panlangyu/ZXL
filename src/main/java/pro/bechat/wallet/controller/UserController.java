package pro.bechat.wallet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.bechat.wallet.domain.model.model.User;
import pro.bechat.wallet.domain.model.response.ApiResponse;
import pro.bechat.wallet.domain.service.UserService;
import pro.bechat.wallet.publics.SendEmail;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

/**
 * create IndexController by huc
 * 2018/4/19  上午6:4
 */
@RestController
@RequestMapping("/user")
@Api(value="用户业务接口",tags={"user service"})
public class UserController {

    Logger logger = Logger.getLogger(UserController.class.getSimpleName());

    @Autowired
    UserService userService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public String index(){

        return "index ok.";
    }


    @GetMapping(value = "/sendEmail")
    public String sendEmail(String email){
        JetEngine engine = JetEngine.create();
        JetTemplate template = engine.getTemplate("/email.jetx");
        Map<String, Object> context = new HashMap<>();
        String code = getThree();
        // String newEmail = email.replace("\"","").replace("\"","");
        context.put("userName", email);
        context.put("code",code);
        StringWriter writer = new StringWriter();
        template.render(context, writer);
        String output = writer.toString();
        System.out.println(output);
        SendEmail.sendEmails(email,output);
        return code;
    }

    @GetMapping(value = "/register")
    public String register(@RequestBody User user){
        JSONObject object = new JSONObject();
        int userId = 0;
        try{
            if (!user.getUserEmail().isEmpty() && !user.getTransactionPW().isEmpty()){
                userId = userService.save(user);
                object.put("userId",userId);
            }else{
                object.put("msg","email and password is not null!!!");
            }
        } catch (Exception e){
            object.put("msg",e.getMessage());
        }
        return JSON.toJSONString(new ApiResponse(4,JSON.toJSONString(object)));
    }



    @PostMapping(value = "/login")
    public String login(@RequestBody User user){
        JSONObject object = new JSONObject();
         List<User> users = userService.findList(user);
         if (users.size()!=0){
             object.put("userID",users.get(0).getUserId());
             object.put("email",users.get(0).getUserEmail());
             object.put("msg","登陆成功");
         }else{
             object.put("msg","用户不存在");
         }
        return JSON.toJSONString(new ApiResponse(4,JSON.toJSONString(object)));
    }


    @PostMapping(value = "/update")
    public String update(@RequestBody User user){


        return null;
    }

    public static String getThree() {
        Random rad = new Random();
        return rad.nextInt(1000000) + "";
    }





}
