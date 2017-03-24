<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspi/kindeditor.jspi"%>
<%@include file="/WEB-INF/jspi/fileupload.jspi"%>
<style>
    #dropzone {
        background: #cccccc;
        width: 150px;
        height: 50px;
        line-height: 50px;
        text-align: center;
        font-weight: bold;
    }
    #dropzone.in {
        width: 600px;
        height: 200px;
        line-height: 200px;
        font-size: larger;
    }
    #dropzone.hover {
        background: lawngreen;
    }
    #dropzone.fade {
        -webkit-transition: all 0.3s ease-out;
        -moz-transition: all 0.3s ease-out;
        -ms-transition: all 0.3s ease-out;
        -o-transition: all 0.3s ease-out;
        transition: all 0.3s ease-out;
        opacity: 1;
    }
</style>
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
                <input id="fileupload" type="file" name="files[]" data-url="${ctx}/file/fileupload.json" multiple>

                <div id="dropzone">Drop files here</div>

                <div id="progress">
                    <div style="width: 0%;"></div>
                </div>

                <table id="uploaded-files">
                    <tr>
                        <th>File Name</th>
                        <th>File Size</th>
                        <th>File Type</th>
                        <th>Download</th>
                    </tr>
                </table>
            </div>
            <div class="box-footer"></div>
        </div>

    </section>
</div>
<script>
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',

            done: function (e, data) {
                $("tr:has(td)").remove();
                $.each(data.result.result, function (index, file) {

                    $("#uploaded-files").append(
                        $('<tr/>')
                            .append($('<td/>').text(file.fileName))
                            .append($('<td/>').text(file.fileSize))
                            .append($('<td/>').text(file.fileType))
                            .append($('<td/>').html("<a href='${ctx}/file/get/"+index+"'>Click</a>"))
                    )//end $("#uploaded-files").append()
                });
            },

            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css(
                    'width',
                    progress + '%'
                );
            },

            dropZone: $('#dropzone')
        });
    });
</script>
