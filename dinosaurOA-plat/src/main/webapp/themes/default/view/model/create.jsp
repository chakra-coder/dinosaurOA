<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="content-header">
    <h1>
        驾驶室
        <small>模型创建</small>
    </h1>
    <ol class="breadcrumb">
        <li>
            <a href="#">
                <i class="fa fa-dashboard"></i>
                Home
            </a>
        </li>
        <li class="active">
            模型创建
        </li>
    </ol>
</section>
<section class="content">
    <form id="createSubmit" method="post">
        <div class="row">
            <div class="col-md-1">
                <label>model名称</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="name" placeholder="model名称" class="box-input">
            </div>
        </div>
        <div class="row">
            <div class="col-md-1">
                <label>model Key</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="key" placeholder="model Key" class="box-input">
            </div>
        </div>
        <div class="row">
            <div class="col-md-1">
                <label>描述</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="description" placeholder="描述" class="box-input">
            </div>
        </div>
        <div class="col-sm-3 vertical">
            <button type="submit">create</button>
        </div>
    </form>
</section>