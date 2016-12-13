<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>model列表</title>
<section class="content">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>key</th>
                    <th>description</th>
                    <th>创建时间</th>
                    <th>版本</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty models}">
                        <tr>
                            <th colspan="7"><label>没有查询到model<a href="${ctx}/modeler/create">去创建model</a> </label></th>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="model" items="${models}">
                            <tr>
                                <th>${model.id}</th>
                                <th>${model.name}</th>
                                <th>${model.key}</th>
                                <th>${model.category}</th>
                                <th>${model.createTime}</th>
                                <th>${model.version}</th>
                                <th>
                                    <a class="deploy" >部署</a>
                                    <a href="${ctx}/themes/default/modeler/modeler.html?modelId=${model.id}" target="_blank">编辑</a>
                                </th>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</section>
<js>
    <script>
        $(".deploy").click(function () {
            var id = $(this).parent().parent().find("th").eq(0).html();
            $.get("${ctx}/modeler/deploy",{modelId:id},
                    function (data) {
                        alert(data.message);
                    }
            )
        })
    </script>
</js>
