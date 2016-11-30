<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="content-header">
    <h1>
        驾驶室
        <small>用户创建</small>
    </h1>
    <ol class="breadcrumb">
        <li>
            <a href="#">
                <i class="fa fa-dashboard"></i>
                Home
            </a>
        </li>
        <li class="active">
            用户创建
        </li>
    </ol>
</section>
<section class="content">
    <form id="createSubmit" method="post" action="/user/manager/add">
        <div class="row">
            <div class="col-md-1">
                <label>用户名</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="userName" placeholder="用户名" class="box-input">
            </div>
        </div>
        <div class="row">
            <div class="col-md-1">
                <label>电话号码</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="userName" placeholder="电话号码" class="box-input">
            </div>
        </div>
        <div class="row">
            <div class="col-md-1">
                <label>密码</label>
            </div>
            <div class="col-md-8">
                <input type="password" name="userName" placeholder="密码" class="box-input">
            </div>
        </div>
        <div class="col-sm-3 vertical">
            <button type="submit">加入</button>
        </div>
    </form>
</section>