<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jspi/kindeditor.jspi"%>
<%@include file="/WEB-INF/jspi/main.jspi"%>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文章管理
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
                <form name="example" id="example" method="post" action="${ctx}/article/save">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="row">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label>文章名</label>
                                        <input type="text" name="name" class="form-control input-sm required" title="文章名"/>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label>文件夹</label>
                                        <%--<input type="text" name="folder" class="form-control input-sm required" title="文件夹"/>--%>
                                        <select name="folder" class="form-control input-sm required" title="文件夹">

                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <h4>
                                        <label for="isPrivate">个人</label>
                                        <input id="isPrivate" name="isPrivate" title="个人" checked="checked" class="required" type="checkbox">
                                    </h4>
                                </div>
                            </div>
                                <div class="form-group">
                                    <label>标题</label>
                                    <input type="text" name="title" class="form-control input-sm required" title="标题"/>
                                </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>地点</label>
                                        <input type="text" name="location" class="form-control input-sm required" title="地点"/>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>人物</label>
                                        <input type="text" name="person" class="form-control input-sm required" title="人物"/>
                                    </div>
                                </div>
                            </div>

                                <div class="form-group">
                                    <label>描述</label>
                                    <input type="text" name="person" class="form-control input-sm" title="描述"/>
                                </div>
                        </div>
                        <div class="col-sm-8">
                            <textarea name="content" cols="100" rows="8" style="width: 100%; height:200px;visibility:hidden;"></textarea>
                            <br />
                            <button type="button" onclick="savecontent()">提交内容</button> (提交快捷键: Ctrl + Enter)
                        </div>
                    </div>
                </form>
            </div>
            <div class="box-footer"></div>
        </div>

    </section>
</div>

<script>
    KindEditor.ready(function(K) {
        var editor1 = K.create('textarea[name="content"]', {
            cssPath : '${ctx}/kindeditor/plugins/code/prettify.css',
            uploadJson : '${ctx}/file/upload',
            fileManagerJson : '${ctx}/file/preview',
            allowFileManager : true
//            afterCreate : function() {
//                var self = this;
//                K.ctrl(document, 13, function() {
//                    self.sync();
//                    document.forms['example'].submit();
//                });
//                K.ctrl(self.edit.doc, 13, function() {
//                    self.sync();
//                    document.forms['example'].submit();
//                });
//            }
        });
        prettyPrint();
    });
</script>

<script>
    function savecontent() {
        alert("first");
        if(!$("#example").formValidate()){
            alert("i am here");
            return false;
        }
        alert("outer");
    }
    $(function () {
        $("select[name=folder]").select2({
            ajax: {
                url: "${ctx}/helper/getInfo.json",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        query: params.term, // search term
                        table: 'ARTICLE',
                        column: 'FOLDER',
                    };
                },
                processResults: function (data) {
                    // parse the results into the format expected by Select2
                    // since we are using custom formatting functions we do not need to
                    // alter the remote JSON data, except to indicate that infinite
                    // scrolling can be used

                    return {
                        results: data.result
                    };
                },
                cache: true
            },
//            escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
//            minimumInputLength: 1,
//            templateResult: formatRepo, // omitted for brevity, see the source of this page
            templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
        });
    })
    function formatRepoSelection() {
        
    }
    
</script>
