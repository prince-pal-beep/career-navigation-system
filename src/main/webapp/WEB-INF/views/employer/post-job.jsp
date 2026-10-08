<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Post Job" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Post a Job</h2>
<form method="post" action="${ctx}/employer/post-job">
  <div class="row">
    <div class="col-md-6 mb-3"><label class="form-label">Job title</label><input name="title" class="form-control" required maxlength="150"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Location</label><input name="location" class="form-control" maxlength="100"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Required experience (years)</label><input type="number" min="0" name="requiredExperience" value="0" class="form-control"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Salary range</label><input name="salaryRange" class="form-control" placeholder="e.g. 4-6 LPA" maxlength="50"></div>
    <div class="col-12 mb-3"><label class="form-label">Description</label><textarea name="description" rows="4" class="form-control"></textarea></div>
  </div>
  <label class="form-label">Required skills</label>
  <div class="row mb-3">
    <c:forEach var="s" items="${skills}">
      <div class="col-md-3 col-6"><div class="form-check">
        <input class="form-check-input" type="checkbox" name="skillIds" value="${s.skillId}" id="sk${s.skillId}">
        <label class="form-check-label" for="sk${s.skillId}"><c:out value="${s.skillName}"/></label>
      </div></div>
    </c:forEach>
  </div>
  <button class="btn btn-primary">Submit for approval</button>
</form>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
