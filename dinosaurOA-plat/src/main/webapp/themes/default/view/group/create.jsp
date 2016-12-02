<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="content-header">
    <h1>
        驾驶室
        <small>用户组创建</small>
    </h1>
    <ol class="breadcrumb">
        <li>
            <a href="#">
                <i class="fa fa-dashboard"></i>
                Home
            </a>
        </li>
        <li class="active">
            用户组创建
        </li>
    </ol>
</section>
<section class="content">
    <form id="createSubmit" method="post">
        <div class="row">
            <div class="col-md-1">
                <label>组名称</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="name" placeholder="组名称" class="box-input">
            </div>
        </div>
        <div class="row">
            <div class="col-md-1">
                <label>英文名</label>
            </div>
            <div class="col-md-8">
                <input type="text" name="englishName" placeholder="英文名" class="box-input">
            </div>
        </div>
        <div class="col-sm-3 vertical">
            <button type="submit">加入</button>
        </div>
    </form>
</section>