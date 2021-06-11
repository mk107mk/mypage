package com.code.mypage.controller.login;


import com.code.mypage.service.login.LoginService;
import com.code.mypage.vo.login.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/page")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login")
    public ModelAndView loginForm() {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/login/login");
        return view;
    }

    @RequestMapping(value = "/login/proc")
    @ResponseBody
    public Map<String ,Object> goLogin (@RequestParam String userId,
                          @RequestParam String userPw){

        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> param = new HashMap<>();

       try{
           param.put("userId",userId);
           param.put("userPw",userPw);

           int result = loginService.isLogin(param);
           if (result > 0) {
               resultMap.put("resultCode", 200);
               resultMap.put("resultMsg", "OK");
           } else {
               resultMap.put("resultCode", 400);
               resultMap.put("resultMsg", "Fail");
           }
       }catch(Exception e){
           resultMap.put("resultCode", 500);
           resultMap.put("resultMsg", "ERROR");
       }

       return resultMap;
    }

    @RequestMapping(value = "/join")
    public ModelAndView joinForm(){
        ModelAndView view =  new ModelAndView();
        view.setViewName("views/login/signup");
        return view;
    }

    @RequestMapping(value = "/user/join", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> joinUser(@ModelAttribute UserInfoVO userInfo){
        Map<String,Object> resultMap = new HashMap<>();
        int result = 0;

        try {
            result = loginService.insertUserInfo(userInfo);
            if (result > 0) {
                resultMap.put("resultCode", 200);
                resultMap.put("resultMsg", "OK");
            } else {
                resultMap.put("resultCode", 400);
                resultMap.put("resultMsg", "Fail");
            }
        } catch(Exception e){
            resultMap.put("resultCode", 500);
            resultMap.put("resultMsg", "ERROR");
        }
        return resultMap;

    }

}
