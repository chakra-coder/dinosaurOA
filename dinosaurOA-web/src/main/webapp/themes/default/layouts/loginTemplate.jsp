<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <sitemesh:write property='head' />
    <title><sitemesh:write property='title' /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/themes/default/static/bootstrap-3.3.7/css/bootstrap-theme.min.css">
    <sitemesh:write property='css' />
</head>
<body>
<sitemesh:write property='body' />
</body>
<script type="application/javascript" rel="script" src="${ctx}/themes/default/static/jquery-3.1.0/jquery.min.js"></script>
<script type="application/javascript" rel="script" src="${ctx}/themes/default/static/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<sitemesh:write property='js' />
</html>
