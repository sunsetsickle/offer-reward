package com.liang.controller;


import com.liang.domain.Mission;
import com.liang.service.ListService;
import com.liang.util.PrintJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Resource
    private ListService service;

    @RequestMapping("missionList.do")
    public void listTest(HttpServletResponse response){
        List<Mission> list;
        list=service.listTest();
        Map<String,Object> map=new HashMap<>();
        map.put("dataList",list);
        PrintJson.printJsonObj(response,map);
    }
}
