<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>model列表</title>
<section class="content">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>name</th>
                    <th>key</th>
                    <th>description</th>
                    <th>类别</th>
                    <th>创建时间</th>
                    <th>版本</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty model}">
                        <tr>
                            <th colspan="7"><label>没有查询到model<a href="${ctx}/modeler/create">去创建model</a> </label></th>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="model" items="models">
                            <tr>
                                <th>${model.id}</th>
                                <th>${model.nmae}</th>
                                <th>${model.key}</th>
                                <th>${model.category}</th>
                                <th>${model.createTime}</th>
                                <th>${model.version}</th>
                                <th><a href="#" >部署</a> </th>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</section>
