<%@ page contentType="text/html;charset=UTF-8"%>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>"/>
    <title>用户登录</title>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/jquery-3.6.0.js"></script>
</head>
<script>
    $(function () {
        $("#loginBtn").click(function () {
            $("#msg").html("");
            //var const let的区别
            const username = $.trim($("#username").val());
            const password = $.trim($("#password").val());
            const code = $("#code").val();

            if (username===''||password===''){
                $("#msg").html("用户名密码不能为空");
                return false;
            }
            if (code===''){
                $("#msg").html("请输入验证码");
                return false;
            }
            $.ajax({
                url:"login.do",
                data:{
                    /*
                    后端需要什么：用户名、密码、验证码
                     */
                    "loginAct":username,
                    "loginPwd":password,
                    "codeImage":code
                },
                type:"post",
                dataType:"json",
                success:function (data){
                    /*
                    * 分析需要从后端拿什么
                    * 1、登录成功还是失败，布尔值 success
                    * 2、如果登录失败，需要知道为什么失败（异常信息 msg）
                    * */
                    if (data.success){
                        window.location.href="index.jsp";
                    }else {
                        $("#msg").html(data.msg);

                    }
                }

            });
        })
    })
</script>
<body>
<!-- partial:index.partial.html -->
<div class="login-box">
    <h2>登录</h2>
    <form method="post">
        <div class="user-box">
            <input type="text" required="" id="username">
            <label>用户名</label>
        </div>
        <div class="user-box">
            <input type="password" required="" id="password">
            <label>密码</label>
        </div>

        <%--新增验证码--%>
        <div class="user-box">
            <input type="text" required="" id="code" style="width: 163px">
            <label>验证码</label>
            <img src="code.do" onclick="myRefresh(this)" alt="验证码" style="position:relative;top: 13px;right: -3px;" width="150px">
        </div>

        <a id="loginBtn" style="cursor:pointer;">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            Submit
        </a>
    </form>
    <span id="msg" style="width: 150px;text-align: center;color:red;position:absolute;bottom:55px;right:40px;"></span>
</div>
<!-- partial -->

<script>
    function myRefresh(obj) {
        obj.src = "code.do?time=" + new Date().getTime();
    }
</script>
</body>
</html>