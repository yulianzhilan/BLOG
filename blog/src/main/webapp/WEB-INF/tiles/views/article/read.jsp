<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${ctx}/froala_editor/css/froala_style.min.css"/>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文章阅读
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}/login/home"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">list</li>
        </ol>
    </section>
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <i class="fa fa-globe"></i>${result.name}
                    <small class="pull-right">Date: <fmt:formatDate value="${result.modify}" pattern="yyyy-MM-dd HH:mm:ss"/> </small>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-3 invoice-col">
                author
                <address>
                    <strong>${result.nickName}</strong><br>
                </address>
            </div>
            <!-- /.col -->
            <div class="col-sm-3 invoice-col">
                location
                <address>
                    <strong>${articleDTO.location}</strong><br>
                </address>
            </div>
            <!-- /.col -->
            <div class="col-sm-3 invoice-col">
                person
                <address>
                    <strong>${articleDTO.person}</strong><br>
                </address>
            </div>
            <div class="col-sm-3 invoice-col">
                folder
                <address>
                    <strong>${articleDTO.folder}</strong><br>
                </address>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-sm-12">
                <h3 class="text-center">${articleDTO.title}</h3>
                <div id="content" class="fr-view">
                    ${articleDTO.content}
                </div>
            </div>
        </div>
    </section>
</div>
