<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<title>模型创建</title>
<section class="content">
    <form id="createSubmit" method="post" target="_blank" class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-sm-2 col-md-2" for="modelInput">model名称</label>
            <div class="col-md-8 col-sm-8">
                <input id="modelInput" type="text" name="name" placeholder="model名称" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2 col-md-2" for="modelKeyInput">model Key</label>
            <div class="col-md-8 col-sm-8">
                <input id="modelKeyInput" type="text" name="key" placeholder="model Key" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2 col-md-2" for="descriptionInput">描述</label>
            <div class="col-md-8 col-sm-8">
                <input id="descriptionInput" type="text" name="description" placeholder="描述" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Create</button>
            </div>
        </div>
    </form>
</section>
