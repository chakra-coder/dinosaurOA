<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="content">
    <form id="createSubmit" method="post" class="form-horizontal" role="form">
        <div class="form-group">
            <label class="control-label col-sm-2 col-md-2" for="nameInput">组名称</label>
            <div class="col-md-8 col-sm-8">
                <input id="nameInput" type="text" name="name" placeholder="组名称" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2 col-md-2" for="englishNameInput">英文名</label>
            <div class="col-md-8 col-sm-8">
                <input id="englishNameInput" type="text" name="englishName" placeholder="英文名" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Create</button>
            </div>
        </div>
    </form>
</section>
