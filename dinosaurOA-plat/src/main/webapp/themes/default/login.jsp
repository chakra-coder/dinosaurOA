<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>login</title>
</head>
<body>
    <div class="login-box">
        <div class="login-logo">
            <a href="#"><b>dinosaurOA-管理端系统</b></a>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">

            <p class="login-box-msg">管理员登陆</p>

            <form method="post" id="loginform">
                <div class="form-group has-feedback">
                    <label>用户名：</label> <input class="form-control" name="username">
                    <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <label>密码：</label> <input type="password" name="password"
                                              class="form-control"> <span
                        class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-8">
                        <div class="form-group checkbox">
                            <label> <input type="checkbox" name="remember_me">
                                记住我的登陆信息
                            </label>
                        </div>
                    </div>
                    <!-- /.col -->
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat">登陆</button>
                    </div>
                    <!-- /.col -->
                </div>
            </form>
        </div>
    </div>
<script>
    $(function() {
        $(document).ajaxStart(function() {
            Pace.restart();
        });

        $("#loginform").submit(function() {
            $(this).ajaxSubmit({
                type : "post",
                dataType : "json",
                success : function(data) {
                    if(data.errorCode == 0){
                        window.location.href="/admin/index";
                    }else{
                        alert(data.message);
                    }
                },
                error : function() {
                    alert("信息提交错误");
                }
            });
            return false; //不刷新页面
        });
    });
</script>
</body>
</html>
