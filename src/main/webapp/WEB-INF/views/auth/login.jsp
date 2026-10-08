<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Login" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="row justify-content-center"><div class="col-md-5">
  <div class="card shadow-sm"><div class="card-body p-4">
    <h3 class="mb-3">Login</h3>
    <form method="post" action="${ctx}/login">
      <div class="mb-3"><label class="form-label">Email</label><input type="email" name="email" class="form-control" required></div>
      <div class="mb-3"><label class="form-label">Password</label><input type="password" name="password" class="form-control" required></div>
      <button class="btn btn-primary w-100">Login</button>
    </form>
    <p class="mt-3 mb-0 small">New here? <a href="${ctx}/register">Create an account</a></p>
  </div></div>
</div></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
