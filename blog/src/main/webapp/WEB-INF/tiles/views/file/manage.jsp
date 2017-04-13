<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspi/kindeditor.jspi"%>
<%@include file="/WEB-INF/jspi/fileupload.jspi"%>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文件管理
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">manage</li>
        </ol>
    </section>
    <section class="content">

        <div class="box box-primary">
            <div class="box-header"></div>
            <div class="box-body">

                <%--<input id="fileupload" type="file" name="files[]" data-url="${ctx}/file/fileupload.json" multiple>--%>
                    <div class="row">
                        <form method="POST" name="fileUpload" id="fileUpload" enctype="multipart/form-data">
                            <div class="col-sm-4">
                                <label>文件上传</label>
                                <input id="file" name="uploadFile" type="file" class="btn btn-sm btn-primary form-control">
                                <div id="fileError" class="help-block"></div>
                            </div>
                        </form>
                    </div>

                <table id="uploaded-files">
                    <tr>
                        <th class="text-center">File Name</th>
                        <th class="text-center">File Size</th>
                        <th class="text-center">File Type</th>
                        <th class="text-center">Download</th>
                    </tr>
                </table>
            </div>
            <div class="box-footer"></div>
        </div>

    </section>
</div>
<script>
    <%--$(function () {--%>
        <%--$('#fileupload').fileupload({--%>
            <%--dataType: 'json',--%>

            <%--done: function (e, data) {--%>
                <%--$("tr:has(td)").remove();--%>
                <%--$.each(data.result.result, function (index, file) {--%>

                    <%--$("#uploaded-files").append(--%>
                        <%--$('<tr/>')--%>
                            <%--.append($('<td/>').text(file.fileName))--%>
                            <%--.append($('<td/>').text(file.fileSize))--%>
                            <%--.append($('<td/>').text(file.fileType))--%>
                            <%--.append($('<td/>').html("<a href='${ctx}/file/get/"+index+"'>Click</a>"))--%>
                    <%--)//end $("#uploaded-files").append()--%>
                <%--});--%>
            <%--},--%>

            <%--progressall: function (e, data) {--%>
                <%--var progress = parseInt(data.loaded / data.total * 100, 10);--%>
                <%--$('#progress .bar').css(--%>
                    <%--'width',--%>
                    <%--progress + '%'--%>
                <%--);--%>
            <%--},--%>

            <%--dropZone: $('#dropzone')--%>
        <%--});--%>
    <%--});--%>

    $(function () {
        //文件上传初始化
        $("#file").fileinput({
            showPreview : false,
//            allowedFileExtensions : [ "xls", "xlsx"], //限制文件类型
            elErrorContainer : "#fileError"
        });
    })

    // 文件上传提交
    $("#fileUpload").submit(function(event) {
        event.preventDefault(); //阻止当前提交事件

        $('#fileUpload').ajaxSubmit({
            url: '${ctx}/file/fileUpload.json',
            dataType: 'json',
            success: function (data) {
                afterUpdate(data);
            },
            error: function () {
                alert("导入失败！");
            }
        });
    });

    function afterUpdate(data) {
        $("tr:has(td)").remove();
        $.each(data.result, function (index, file) {

            $("#uploaded-files").append(
                $('<tr/>')
                    .append($('<td/>').text(file.fileName))
                    .append($('<td/>').text(file.fileSize))
                    .append($('<td/>').text(file.fileType))
                    .append($('<td/>').html("<a href='${ctx}/file/get/"+index+"'>Click</a>"))
            );
        });
    }
</script>
