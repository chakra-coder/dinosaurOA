<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>流程列表</title>
<section class="content">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>名称</th>
                <th>key</th>
                <th>版本号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty model}">
                    <tr>
                        <th colspan="7"><label>没有查询到可用流程</label></th>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="model" items="models">
                        <tr>
                            <th>${model.nmae}</th>
                            <th>${model.key}</th>
                            <th>${model.version}</th>
                            <th><a class="startup-process">启动</a> </th>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</section>
