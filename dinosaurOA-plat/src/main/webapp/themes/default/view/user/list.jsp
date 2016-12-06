<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>用户列表</title>
<section class="content">
    <div class="row">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>姓名</th>
                    <th>昵称</th>
                    <th>电话号码</th>
                    <th>创建日期</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty users}">
                        <tr>
                            <th colspan="7"><label>没有查询到用户</label></th>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="user" items="${users.content}">
                            <tr>
                                <th>${user.name}</th>
                                <th>${user.nickName}</th>
                                <th>${user.phone}</th>
                                <th>${user.createDate}</th>
                                <th>
                                    <a id="model" type="button" data-toggle="modal" data-target="#myModal" onclick="formClick('${user.id}')">
                                        添加到组
                                    </a>
                                </th>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</section>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">选择组</h4>
            </div>
            <div class="modal-body">
                <form id="relationshipForm" method="post" class="form-horizontal" role="form" action="${ctx}/user/manager/relationship">
                    <input type="hidden" name="userId" id="userIdAttr">
                    <select class="form-control" name="groupId">
                        <c:choose>
                            <c:when test="${empty groups.content}">
                                <option>---</option>
                            </c:when>
                            <c:otherwise>
                                <option>---</option>
                                <c:forEach var="group" items="${groups.content}">
                                    <option value="${group.id}">${group.name}</option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <button id="relationshipSubmit" type="button" class="btn btn-primary" onclick="submit()">Save changes</button>
                </form>
            </div>
            <div class="modal-footer">
                <%--<button type="submit" class="btn btn-default" data-dismiss="modal">Close</button>--%>

            </div>
        </div>
    </div>
</div>
<js>
    <script>
        function submit() {
            $("#relationshipForm").submit(function () {
                alert(data);
            });
        }

        function formClick(obj) {
            $("#userIdAttr").val(obj);
        }
    </script>
</js>

