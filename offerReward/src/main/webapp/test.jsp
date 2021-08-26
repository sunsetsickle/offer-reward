<%--
  Created by IntelliJ IDEA.
  User: 凉
  Date: 2021/8/22
  Time: 14:33
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <title>ajax获取数据库</title>
    <script type="text/javascript" src="js/jquery-3.6.0.js"></script>
</head>
<script>
    $(function (){
        dataList();
    })
    function dataList() {
        $.ajax({
            url:"missionList.do",
            data:{

            },
            type:"get",
            dataType:"json",
            success:function (data){
                var html="";
                $.each(data,function (i,n){
                    html+= '<tr>';
                    html+= '<td>'+n.name+'</td>';
                    html+= '<td>'+n.difficulty+'</td>';
                    html+= '<td>'+n.endTime+'</td>';
                    html+= '<td>'+n.awards+'</td>';
                    html+= '</tr>';
                })
                $("#tbody").html(html);
            }
        })
    }
</script>
<body>
    <table border="1">
        <thead>
            <tr>
                <td>任务名</td>
                <td>任务等级</td>
                <td>截至日期</td>
                <td>任务报酬</td>
            </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
    </table>
</body>
</html>
