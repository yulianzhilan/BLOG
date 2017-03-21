<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div class="col_3" id="editNote">
                    <div class="note_title clear_float">
                        <div class="col_li col_left" style="width:99%"><input class="input_text" id="inputNoteTitle" type="text" placeholder="此处输入笔记标题"></div>
                    </div>
                    <div class="note_content" style="margin: 0px; overflow: hidden; outline: none;" tabindex="5003">
                        <textarea id="editorNote" style="width: 100%; height: 845px; display: none;"></textarea>
                    </div>
                </div>

            </div>
            <div class="box-footer"></div>
        </div>

        <div class="box box-primary">
            <div class="box-header">
                <div class="btn-group">
                    <%--<a href="${ctx}/article/readByFolder?attribute=${result.attribute}"><button type="button" class="btn btn-info btn-flat"><i class="fa fa-align-right"></i>${result.attribute}</button></a>--%>
                    <%--<a href="${ctx}/article/list?attribute=${result.attribute}&name=${result.name}" id="show"><button type="button" class="btn btn-info btn-flat"><i class="fa fa-refresh"></i>${result.name}</button></a>--%>
                </div>
                <%--<h3 class="box-title">${result.name}</h3>--%>
                <div class="box-tools">
                    <div class="input-group input-group-sm" style="width: 150px;">
                        <input name="table_search" class="form-control pull-right" placeholder="Search" type="text">

                        <div class="input-group-btn">
                            <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body table-responsive no-padding">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th class="text-center">ID</th>
                        <th class="text-center">NAME</th>
                        <th class="text-center">LOCATION</th>
                        <th class="text-center">PERSON</th>
                        <th class="text-center">FOLDER</th>
                        <th class="text-center" style="width: 30%;">DESCRIPTION</th>
                        <th class="text-center">TITLE</th>
                        <th class="text-center">ACTION</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${result.articleDTOs}" var="dto">
                        <tr>
                            <td class="text-center">${dto.id}</td>
                            <td class="text-center">${dto.name}</td>
                            <td class="text-center">${dto.location}</td>
                            <td class="text-center">${dto.person}</td>
                            <td class="text-center">${dto.folder}</td>
                            <td class="text-center">${dto.description}</td>
                            <td class="text-center">${dto.title}</td>
                            <td class="text-center">
                                <span class="fa fa-pencil" style="cursor: pointer; margin-right: 5px;" title="修改"
                                      onclick="window.location.href='${ctx}'"></span>
                                <span class="fa fa-eye" style="cursor: pointer; margin-right: 5px;" title="预览"
                                      onclick="preview(${dto.id})"></span>
                                <span class="fa fa-times" style="cursor: pointer; margin-right: 5px;" title="删除"
                                      onclick="deleteArticle(${dto.id})"></span>
                            </td>
                                <%-- fixme 最好加上创建时间 --%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->
        </div>
        <%--<script src="http://inote.tmooc.cn/javascripts/Editor/kindeditor-min.js"></script>--%>
        <script src="${ctx}/kindeditor/kindeditor.js"></script>
        <script src="http://inote.tmooc.cn/javascripts/Editor/lang/zh_CN.js"></script>
        <%--<script src="http://inote.tmooc.cn/javascripts/index.js"></script>--%>

        <script>
            //KindEditor函数配置
            var editor = KindEditor.create('#editorNote',{
                items:[
                    'preview', 'code',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', '|' , 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', '|' , 'subscript',
                    'superscript', 'clearhtml', 'quickformat', '|' , 'source',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', '|' ,  'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'table', 'hr',
                    'anchor', 'link', 'unlink', 'fullscreen', '|' , 'image' , 'media' , 'emoticons' , 'print' , '|' , 'baidumap'
                ],
                resizeType:0,
                //cssData:'body{background:red}',
                fullscreenShortcut:true,
                //autoHeightMode : true,
                afterCreate:function(){
                    //this.loadPlugin('autoheight');
                }
            });
        </script>
    </section>
</div>