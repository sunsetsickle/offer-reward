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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {

    @Resource
    private LoginService service;
    private String uri;

    @RequestMapping(value = "code.do")
    public void codeImage(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        HttpSession session = request.getSession();

        // 从请求中获得 URI ( 统一资源标识符 )
        uri = request.getRequestURI();
        System.out.println("hello : " + uri);

        final int width = 150; // 图片宽度
        final int height = 36; // 图片高度
        final String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        final OutputStream output = response.getOutputStream(); // 获得可以向客户端返回图片的输出流
        // (字节流)
        // 创建验证码图片并返回图片上的字符串
        String code = GraphicHelper.create(width, height, imgType, output);
        System.out.println("验证码内容(直接获取：): " + code);
        // 建立 uri 和 相应的 验证码 的关联 ( 存储到当前会话对象的属性中 )
        session.setAttribute(uri, code);
    }


    @RequestMapping(value = "login.do")
    public void login(String loginAct,String loginPwd,String codeImage,HttpServletResponse response, HttpServletRequest request){
        HttpSession session=request.getSession();
        System.out.println(session.getAttribute(uri));
        if (!codeImage.equals(session.getAttribute(uri))){
            String msg="验证码错误";
            Map<String,Object> map= new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);//把map打包成json 并发送给前端
        }else{
            session.removeAttribute("uri");
            loginPwd= MD5Util.getMD5(loginPwd);
            System.out.println("======>>"+session.getAttribute(uri));
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
