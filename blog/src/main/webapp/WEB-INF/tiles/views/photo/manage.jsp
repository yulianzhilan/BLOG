<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="framework" uri="/framework-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="/WEB-INF/jspi/fileupload.jspi"%>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            照片管理
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}/login/home"><i class="fa fa-home"></i>主页</a></li>
            <li>photo</li>
            <li class="active">manage</li>
        </ol>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">照片上传</h3>
                <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                </div>
            </div>
            <div class="box-body">
                <div class="row">
                    <form method="POST" name="fileUpload" id="fileUpload" enctype="multipart/form-data">
                        <div class="col-sm-4">
                            <label>照片上传</label>
                            <input id="file" name="uploadFile" type="file" class="btn btn-sm btn-primary form-control">
                            <div id="fileError" class="help-block"></div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<div class="modal fade bs-example-modal-md" tabindex="-1" role="dialog" id="myModal">
    <img src="" id="updatedImg"/>
</div>

<script>
    $(function () {
        //文件上传初始化
        $("#file").fileinput({
            showPreview : false,
            allowedFileExtensions : [ "gif", "jpg", "jpeg", "png", "bmp"], //限制文件类型
            elErrorContainer : "#fileError"
        });
    });

    // 文件上传提交
    $("#fileUpload").submit(function(event) {
        event.preventDefault(); //阻止当前提交事件

        $('#fileUpload').ajaxSubmit({
            url: '${ctx}/photo/upload.json',
            dataType: 'json',
            success: function (data) {
                $("#updatedImg").attr("src",data.url);
                $("#myModal").modal("show");
            },
            error: function () {
                alert("导入失败！");
            }
        });
    });

</script>
