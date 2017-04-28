<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspi/editor.jspi"%>
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
                                <form:input path="name" cssClass="form-control input-sm required" title="文章名"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>文件夹</label>
                                <form:input path="folder" cssClass="form-control input-sm required" title="文件夹"/>
                                <li id="fat-menu" class="dropdown dropdown-toggle" style="list-style: none;">
                                    <ul class="dropdown-menu" aria-labelledby="drop3" id="fat-detail" data-toggle="dropdown" aria-expanded="false">

                                    </ul>
                                </li>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group">
                                <label>标题</label>
                                <form:input path="title" cssClass="form-control input-sm required" title="标题"/>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="form-group">
                                <label>个人</label>
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
                                <form:input path="location" cssClass="form-control input-sm required" title="地点"/>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label>人物</label>
                                <form:input path="person" cssClass="form-control input-sm required" title="人物"/>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label>描述</label>
                                <form:input path="description" cssClass="form-control input-sm" title="描述"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <%@include file="/WEB-INF/jsp/editor.jsp"%>
                            <form:textarea path="content" cssClass="hidden"/>
                        </div>
                    </div>
                </form:form>
            </div>
            <div class="box-footer">
                <a onclick="saveContent()" class="btn btn-primary">保存</a>
            </div>
        </div>
    </section>
</div>

<%@include file="/WEB-INF/jspi/jsrender.jspi"%>
<script type="text/x-jsrender" id="li">
{{for result}}
<li><a href="#" onclick="addFolder('{{:#data.dictName}}')">{{:#data.dictName}}</a></li>
{{/for}}
</script>
<script>
    $(function () {
        if(${not empty articleDTO.content}){
            $('#edit').froalaEditor('html.insert', '${articleDTO.content}', true);
        }
    });
    function saveContent() {
        if($("#example select").val() == ""){
            alert("folder 不能为空！");
            return;
        }
        if(!$("#example").formValidate()){
            return false;
        }
        $("#content").val($('#edit').froalaEditor('html.get', true));

        $(window).unbind('beforeunload');

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
    $(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
</script>
