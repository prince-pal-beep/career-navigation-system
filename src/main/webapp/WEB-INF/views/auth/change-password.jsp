<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Change Password" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="row justify-content-center"><div class="col-md-5">
  <h3>Change Password</h3>
  <form method="post" action="${ctx}/change-password">
    <div class="mb-3"><label class="form-label">Current password</label><input type="password" name="current" class="form-control" required></div>
    <div class="mb-3"><label class="form-label">New password</label><input type="password" name="newPassword" class="form-control" required minlength="6"></div>
    <div class="mb-3"><label class="form-label">Confirm new password</label><input type="password" name="confirm" class="form-control" required></div>
    <button class="btn btn-primary">Update</button>
  </form>
</div></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
