package pro.bechat.wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pro.bechat.wallet.domain.model.model.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author ch
 * @Date Create int 2018/7/31 19:52
 * @email 869360026@qq.com
 */
@Controller
public class AdminController {

    @GetMapping("/admin")
    public String main(HttpServletRequest httpServletRequest) {
        return "/login";
    }


    @PostMapping("/login")
    public String login(Admin admin, Model model, HttpSession httpSession) {
        Admin adminRes = null;
        if(admin.getUserName().equals("admin") && admin.getPassword().equals("123456")){
            adminRes = admin;
        }
        if (adminRes != null) {
            httpSession.setAttribute("admin", adminRes);
            return "redirect:index";
        } else {
            model.addAttribute("error", "用户名或密码错误，请重新登录！");
            return "login";
        }
    }


    @GetMapping("/index")
    public String index(Model model, HttpSession httpSession) {
        return "/index";
    }


    @GetMapping("/welcome")
    public String welcome(Model model) {
        return "/welcome";
    }


    @GetMapping("/member-list")
    public String memberList(Model model) {
        return "/member-list";
    }


    
}
