<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="framework" uri="/framework-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            文章分类
        </h1>
        <ol class="breadcrumb">
            <li><a href="${ctx}"><i class="fa fa-home"></i>主页</a></li>
            <li>article</li>
            <li class="active">classify</li>
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
                    <a href="${ctx}/article/classifyByFolder?attribute=folder"><button type="button" class="btn <c:if test="${attribute == 'folder'}">btn-warning</c:if> btn-info btn-flat"><i class="fa fa-folder"></i>文件夹</button></a>
                    <a href="${ctx}/article/classifyByFolder?attribute=location"><button type="button" class="btn <c:if test="${attribute == 'location'}">btn-warning</c:if> btn-info btn-flat"><i class="fa fa-location-arrow"></i>地点</button></a>
                    <a href="${ctx}/article/classifyByFolder?attribute=person"><button type="button" class="btn <c:if test="${attribute == 'person'}">btn-warning</c:if> btn-info btn-flat"><i class="fa fa-user-circle"></i>人物</button></a>
                </div>
            </div><!-- box-footer -->
            <div class="box-body">
                <div class="row">
                    <c:forEach items="${result}" var="folder">
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <div class="small-box bg-aqua">
                                <div class="inner">
                                    <h3>${fn:length(folder.articleDTOs)}</h3>

                                    <h2 class="text-center">${folder.name}</h2>
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

        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">文章概览</h3>
                <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                </div>
            </div>
            <div class="box-body">
                <c:forEach items="${articleDTOs}" var="article">
                    <div class="post">
                    <div class="user-block">
                        <!-- fixme 这里需要添加用户头像相关的方法 -->
                        <img class="img-circle img-bordered-sm" src="" alt="user image">
                        <span class="username">
                          <a href="#">Jonathan Burke Jr.</a>
                          <a href="#" class="pull-right btn-box-tool"><i class="fa fa-times"></i></a>
                        </span>
                        <span class="description">Shared publicly - 7:30 PM today</span>
                    </div>
                    <!-- /.user-block -->
                    <p>
                       ${article.description}
                    </p>
                    <ul class="list-inline">
                        <!-- fixme 加上评论相关方法 -->
                        <li><a href="${ctx}/article/read?id=${article.id}" class="link-black text-sm"><i class="fa fa-share margin-r-5"></i> Show more</a></li>
                        <li><a href="#" class="link-black text-sm"><i class="fa fa-thumbs-o-up margin-r-5"></i> Like</a></li>
                        <li class="pull-right">
                            <a href="#" class="link-black text-sm"><i class="fa fa-comments-o margin-r-5"></i> Comments
                                (5)</a></li>
                    </ul>
                    <form class="form-horizontal">
                        <div class="form-group margin-bottom-none">
                            <div class="col-sm-9">
                                <input class="form-control input-sm" placeholder="Type a comment" type="text">
                            </div>
                            <div class="col-sm-3">
                                <button type="submit" class="btn btn-danger pull-right btn-block btn-sm">Send</button>
                            </div>
                        </div>
                    </form>
                </div>
                </c:forEach>
            </div>
            <div class="box-footer clearfix">
                <framework:pagination />
            </div>
        </div>
    </section>
</div>