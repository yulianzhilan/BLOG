<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@include file="/WEB-INF/jspi/kindeditor.jspi"%>--%>
<%@include file="/WEB-INF/jspi/main.jspi"%>
<!-- Include Editor style. -->
<link href='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.5.1/css/froala_editor.min.css' rel='stylesheet' type='text/css' />
<link href='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.5.1/css/froala_style.min.css' rel='stylesheet' type='text/css' />

<!-- Include JS file. -->
<script type='text/javascript' src='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.5.1/js/froala_editor.min.js'></script>

<!-- Code Mirror CSS file. -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/codemirror.min.css">

<!-- Include the plugin CSS file. -->
<link rel="stylesheet" href="../css/plugins/code_view.min.css">
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            创建文章
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}/login/home"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">write</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div class="box-header"></div>
            <div class="box-body">
                <form:form name="example" id="example" modelAttribute="articleDTO" method="post" action="${ctx}/article/save">
                    <form:input path="id" cssClass="hidden"/>
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>文章名</label>
                                <%--<input type="text" name="name" class="form-control input-sm required" title="文章名"/>--%>
                                <form:input path="name" cssClass="form-control input-sm required" title="文章名"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>文件夹</label>
                                <form:input path="folder" cssClass="form-control input-sm required" title="文件夹"/>
                                <li id="fat-menu" class="dropdown dropdown-toggle" style="list-style: none;">
                                    <%--<input type="text" name="folder" id="folder" class="form-control input-sm required" title="文件夹"/>--%>
                                    <ul class="dropdown-menu" aria-labelledby="drop3" id="fat-detail" data-toggle="dropdown" aria-expanded="false">

                                    </ul>
                                </li>
                                <%--<div class="input-group">--%>
                                    <%--<select name="folder" id="selectFolder" class="form-control input-sm required" title="文件夹">--%>
                                        <%--<option></option>--%>
                                        <%--<c:forEach items="${folders}" var="folder">--%>
                                            <%--<option value="${folder.dictName}">${folder.dictName}</option>--%>
                                        <%--</c:forEach>--%>
                                    <%--</select>--%>
                                    <%--<div class="input-group-btn">--%>
                                        <%--<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#myModal">NEW</button>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label>标题</label>
                                <%--<input type="text" name="title" class="form-control input-sm required" title="标题"/>--%>
                                <form:input path="title" cssClass="form-control input-sm required" title="标题"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <%--<label for="isPrivate">个人</label>--%>
                                <label>个人</label>
                                <%--<input id="isPrivate" name="isPrivate" title="个人" checked="checked" class="required" type="checkbox">--%>
                                <%--<select class="form-control input-sm required" title="个人" name="isPrivate">--%>
                                    <%--<option value="0"></option>--%>
                                    <%--<option value="0">否</option>--%>
                                    <%--<option value="1">是</option>--%>
                                <%--</select>--%>
                                <form:select path="isPrivate" title="个人" cssClass="form-control input-sm required">
                                    <form:option value="0">否</form:option>
                                    <form:option value="1">是</form:option>
                                </form:select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>地点</label>
                                <%--<input type="text" name="location" class="form-control input-sm required" title="地点"/>--%>
                                <form:input path="location" cssClass="form-control input-sm required" title="地点"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>人物</label>
                                <%--<input type="text" name="person" class="form-control input-sm required" title="人物"/>--%>
                                <form:input path="person" cssClass="form-control input-sm required" title="人物"/>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label>描述</label>
                                <%--<input type="text" name="description" class="form-control input-sm" title="描述"/>--%>
                                <form:input path="description" cssClass="form-control input-sm" title="描述"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div id="froala-editor"></div>
                            <br />
                            <button type="button" onclick="saveContent()">提交内容</button>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="box-footer"></div>
        </div>
    </section>
</div>


<!-- Code Mirror JS files. -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/codemirror.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.3.0/mode/xml/xml.min.js"></script>

<!-- Include the plugin JS file. -->
<script type="text/javascript" src="${ctx}/froalaeditor/js/plugins/code_view.min.js"></script>

<script>
    $(function() {
        $('div#froala-editor').froalaEditor();
    });
</script>


<%@include file="/WEB-INF/jspi/jsrender.jspi"%>
<script type="text/x-jsrender" id="li">
{{for result}}
<li><a href="#" onclick="addFolder('{{:#data.dictName}}')">{{:#data.dictName}}</a></li>
{{/for}}
</script>
<script>

    function saveContent() {
        if($("#example select").val() == ""){
            alert("folder 不能为空！");
            return;
        }
        if(!$("#example").formValidate()){
            alert("i am here");
            return false;
        }
        document.charset = "UTF-8";
        $("#example").submit();
    }

    $(function () {
        $("#folder").keyup(function () {
            $.ajax({
                url: '${ctx}/helper/getInfo.json?table=ARTICLE&column=FOLDER&query='+$("#folder").val(),
                type: 'GET',
                success: function (data) {
                    if(data.status == 'success'){
                        if(data.result.length > 0){
                            $("#fat-detail").html($.templates("#li").render({result: data.result}));
                            $("#fat-menu").addClass("open");
                        } else{
                            $("#fat-menu").removeClass("open");
                        }
                    }
                }
            })
        });

    });
// fixme 需要增加关闭窗口是提醒保存的功能！
    window.onbeforeunload = function() {
        if(confirm("是否保存后退出?")){
            alert("保存失败！");
            return false; // 可以阻止关闭
        }
    }
</script>
