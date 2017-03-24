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
                                <div class="form-group">
                                    <label>标题</label>
                                    <input type="text" name="title" class="form-control input-sm required" title="标题"/>
                                </div>
                                <div class="form-group">
                                    <label>地点</label>
                                    <input type="text" name="location" class="form-control input-sm required" title="地点"/>
                                </div>
                                <div class="form-group">
                                    <label>人物</label>
                                    <input type="text" name="person" class="form-control input-sm required" title="人物"/>
                                </div>
                        </div>
                        <div class="col-sm-8">
                            <textarea name="content" cols="100" rows="8" style="width: 100%; height:200px;visibility:hidden;"></textarea>
                            <br />
                            <button onclick="savecontent()">提交内容</button> (提交快捷键: Ctrl + Enter)
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
            allowFileManager : true,
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
</script>