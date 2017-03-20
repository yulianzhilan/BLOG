<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文章概览
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">read</li>
        </ol>
    </section>
    <section class="content">

        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">文章列表</h3>
                <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                </div><!-- /.box-tools -->
            </div><!-- /.box-header -->
            <div class="box-footer">
                <span>分类方式:</span>
                <div class="btn-group">
                    <a href="${ctx}/article/readByFolder?attribute=folder"><button type="button" class="btn btn-info btn-flat"><i class="fa fa-align-left"></i>文件夹</button></a>
                    <a href="${ctx}/article/readByFolder?attribute=location"><button type="button" class="btn btn-info btn-flat"><i class="fa fa-align-right"></i>地点</button></a>
                    <a href="${ctx}/article/readByFolder?attribute=person"><button type="button" class="btn btn-info btn-flat"><i class="fa fa-align-right"></i>人物</button></a>
                </div>
            </div><!-- box-footer -->
            <div class="box-body">
                <div class="row">
                    <c:forEach items="${result}" var="folder">
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>${folder.articleDTOs.size()}</h3>

                                    <h2 class="text-center">${folder.name==null?'空':folder.name}</h2>
                                </div>
                                <div class="icon">
                                    <i class="ion ion-stats-bars"></i>
                                </div>
                                <a href="${ctx}/article/list?attribute=${folder.attribute}&name=${folder.name}" class="small-box-footer">
                                    More info <i class="fa fa-arrow-circle-right"></i>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div><!-- /.box-body -->
        </div><!-- /.box -->



    </section>
</div>