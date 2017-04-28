<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            500 Error
        </h1>
    </section>
    <!-- Main content -->
    <section class="content">
        <div class="error-page">
            <h2 class="headline text-red">500</h2>

            <div class="error-content">
                <h3><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h3>
                <p>
                    We will work on fixing that right away.
                    Meanwhile, you may <a href="${ctx}">return to Home</a>.
                </p>
            </div>
        </div>
    </section>
</div>