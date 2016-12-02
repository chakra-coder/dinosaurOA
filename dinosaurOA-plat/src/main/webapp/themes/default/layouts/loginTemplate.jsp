<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <sitemesh:write property='head' />
    <title><sitemesh:write property='title' /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/css/AdminLTE.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/css/skins/_all-skins.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/AdminLTE-2.3.8/plugins">
    <sitemesh:write property='css' />
</head>
<body class="login-page">
    <sitemesh:write property='body' />
</body>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/js/app.min.js"></script>
    <script type="application/javascript" rel="script" src="${ctx}/themes/default/static/AdminLTE-2.3.8/bootstrap/js/bootstrap.min.js"></script>
    <sitemesh:write property='js' />
</html>
