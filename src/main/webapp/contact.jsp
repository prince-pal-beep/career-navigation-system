<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Contact" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="row justify-content-center"><div class="col-md-6">
  <h2>Contact Us</h2>
  <form method="post" action="${ctx}/contact">
    <div class="mb-3"><label class="form-label">Name</label><input name="name" class="form-control" required maxlength="100"></div>
    <div class="mb-3"><label class="form-label">Email</label><input type="email" name="email" class="form-control" maxlength="100"></div>
    <div class="mb-3"><label class="form-label">Message</label><textarea name="message" rows="4" class="form-control" required></textarea></div>
    <button class="btn btn-primary">Send</button>
  </form>
</div></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
