package com.liang.controller;
import com.liang.domain.User;
import com.liang.imag.GraphicHelper;
import com.liang.service.LoginService;
import com.liang.util.MD5Util;
import com.liang.util.PrintJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    @Resource
    private LoginService service;


    @RequestMapping(value = "code.do")
    public void codeImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        GraphicHelper.create(request,response);
    }


    @RequestMapping(value = "login.do")
    public void login(String loginAct,String loginPwd,String codeImage,HttpServletResponse response, HttpServletRequest request){
        HttpSession session=request.getSession();
        if (!codeImage.equals(session.getAttribute("captchaString"))){
            String msg="验证码错误";
            Map<String,Object> map= new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);//把map打包成json 并发送给前端
        }else{
            loginPwd= MD5Util.getMD5(loginPwd);
            try {
                User user= service.login(loginAct,loginPwd);
                request.getSession().setAttribute("user",user);
                PrintJson.printJsonFlag(response,true);

            } catch (Exception e) {
                e.printStackTrace();
                String msg= e.getMessage();
                Map<String,Object> map= new HashMap<>();
                map.put("success",false);
                map.put("msg",msg);
                PrintJson.printJsonObj(response,map);//把map打包成json 并发送给前端
            }
        }

    }

    @RequestMapping(value = "outLogin.do")
    public void outLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    @RequestMapping("editPwd.do")
    public void editPwd(String oldPwd,String newPwd,HttpServletResponse response,HttpServletRequest request){
        oldPwd=MD5Util.getMD5(oldPwd);
        newPwd=MD5Util.getMD5(newPwd);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            int cont= service.editPwd(user,oldPwd,newPwd);
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg= e.getMessage();
            Map<String,Object> map= new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);//把map打包成json 并发送给前端
        }

    }
}
