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
                <h3 class="box-title">${result.attribute}----------${result.name==""?'空':result.name}</h3>

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
                    <tbody>
                        <tr>
                            <th>ID</th>
                            <th>NAME</th>
                            <th>LOCATION</th>
                            <th>PERSON</th>
                            <th>FOLDER</th>
                            <th>DESCRIPTION</th>
                        </tr>
                        <c:forEach items="${result.articleDTOs}" var="dto">
                            <tr>
                                <td>${dto.id}</td>
                                <td>${dto.name}</td>
                                <td>${dto.location}</td>
                                <td>${dto.person}</td>
                                <td>${dto.folder}</td>
                                <td>${dto.description}</td>
                                <%-- fixme 最好加上创建时间 --%>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- /.box-body -->
        </div>

    </section>
</div>