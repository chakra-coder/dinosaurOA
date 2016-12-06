<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <sitemesh:write property='head' />
    <title><sitemesh:write property='title' /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/Font-Awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/css/AdminLTE.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/css/skins/_all-skins.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/style/main.css">
    <sitemesh:write property='css' />
</head>
<body class="skin-blue sidebar-mini">
    <div class="wrapper">
        <%@ include file="header.jsp"%>
        <%@ include file="sidebar.jsp"%>
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    驾驶室
                    <small><sitemesh:write property='title' /></small>
                </h1>
                <ol class="breadcrumb">
                    <li>
                        <a href="#">
                            <i class="fa fa-dashboard"></i>
                            Home
                        </a>
                    </li>
                    <li class="active">
                        <sitemesh:write property='title' />
                    </li>
                </ol>
            </section>
            <hr />
            <sitemesh:write property='body' />
        </div>
        <%@ include file="footer.jsp"%>
    </div>
</body>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/js/app.min.js"></script>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/bootstrap/js/bootstrap.min.js"></script>
    <sitemesh:write property='js' />
</html>
