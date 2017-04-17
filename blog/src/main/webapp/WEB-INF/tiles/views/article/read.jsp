<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文章列表
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">list</li>
        </ol>
    </section>
    <section class="content">

        <div class="box box-primary">
            <div class="box-header">
                <div class="btn-group">
                    <a href="${ctx}/article/classifyByFolder?attribute=${result.attribute}"><button type="button" class="btn btn-info btn-flat">${result.attribute}</button></a>
                    <a href="${ctx}/article/list?attribute=${result.attribute}&name=${result.name}"><button type="button" id="show" class="btn btn-info btn-flat">${result.name}</button></a>
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
                        <th class="text-center">NO</th>
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
                        <c:forEach items="${result.articleDTOs}" var="dto" varStatus="item">
                            <tr>
                                <td class="text-center">${item.index+1}</td>
                                <td class="text-center">${dto.name}</td>
                                <td class="text-center">${dto.location}</td>
                                <td class="text-center">${dto.person}</td>
                                <td class="text-center">${dto.folder}</td>
                                <td class="text-center">${dto.description}</td>
                                <td class="text-center">${dto.title}</td>
                                <td class="text-center">
                                    <span class="fa fa-pencil" style="cursor: pointer; margin-right: 5px;" title="修改" onclick="window.location.href='${ctx}/article/modify?id=${dto.id}'"></span>
                                    <span class="fa fa-eye" style="cursor: pointer; margin-right: 5px;" title="预览" onclick="preview(${dto.id})"></span>
                                    <span class="fa fa-times" style="cursor: pointer; margin-right: 5px;" title="删除" onclick="deleteArticle(${dto.id})"></span>
                                </td>
                                <%-- fixme 最好加上创建时间 --%>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->
        </div>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h3 class="modal-title text-center" id="title"></h3>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-sm-4">
                                <h4>location</h4>
                                <p id="location"></p>
                            </div>
                            <div class="col-sm-4">
                                <h4>folder</h4>
                                <p id="folder"></p>
                            </div>
                            <div class="col-sm-4">
                                <h4>person</h4>
                                <p id="person"></p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-4">
                                <h4>person</h4>
                                <p id="name"></p>
                            </div>
                            <div class="col-sm-8">
                                <h4>description</h4>
                                <p id="description"></p>
                            </div>
                        </div>

                        <hr>

                        <div id="content" style="word-wrap: break-word; word-break: normal;"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


    </section>
</div>
<script>
    function deleteArticle(id) {
        $.ajax({
            url: "${ctx}/article/delete.json?id="+id,
            success: function (data) {
                if(data.status == 'success'){
                    alert("删除成功");
                    $("#show").click();
                }
            }
        })
    }
    function preview(id) {
        $.ajax({
            url: "${ctx}/article/preview.json?id="+id,
            success: function (data) {
                if(data.status == 'success'){
                    $("#title").text(data.result.title);
                    $("#location").text(data.result.location);
                    $("#folder").text(data.result.folder);
                    $("#person").text(data.result.person);
                    $("#name").text(data.result.name);
                    $("#description").text(data.result.description);
                    $("#content").text(data.result.content);
                    $("#myModal").modal("show");
                }
            }
        })
    }
</script>