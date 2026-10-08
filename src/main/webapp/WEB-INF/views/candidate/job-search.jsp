<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Jobs" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Job Search</h2>
<form method="get" class="row g-2 mb-4">
  <div class="col-md-5"><input name="q" class="form-control" placeholder="Job title / keyword" value="<c:out value='${q}'/>"></div>
  <div class="col-md-4"><input name="loc" class="form-control" placeholder="Location" value="<c:out value='${loc}'/>"></div>
  <div class="col-auto"><button class="btn btn-primary">Search</button></div>
</form>
<div class="row g-3 mb-5">
  <c:forEach var="j" items="${jobs}">
    <div class="col-md-6"><div class="card card-hover h-100"><div class="card-body">
      <div class="d-flex justify-content-between">
        <h5 class="mb-0"><c:out value="${j.title}"/></h5>
        <span class="badge bg-${j.matchScore >= 70 ? 'success' : j.matchScore >= 40 ? 'warning text-dark' : 'secondary'}">${j.matchScore}% match</span>
      </div>
      <div class="text-muted small mb-2"><c:out value="${j.companyName}"/> &middot; <c:out value="${j.location}"/> &middot; ${j.requiredExperience}+ yrs &middot; <c:out value="${j.salaryRange}"/></div>
      <p class="small"><c:out value="${j.description}"/></p>
      <p class="small"><c:forEach var="s" items="${j.skills}"><span class="badge bg-light text-dark border me-1"><c:out value="${s.skillName}"/></span></c:forEach></p>
      <form method="post" action="${ctx}/candidate/jobs"><input type="hidden" name="jobId" value="${j.jobId}"><button class="btn btn-sm btn-primary">Apply</button></form>
    </div></div></div>
  </c:forEach>
  <c:if test="${empty jobs}"><div class="col-12 text-muted">No jobs found.</div></c:if>
</div>

<h4>My Applications</h4>
<table class="table table-striped">
  <thead><tr><th>Job</th><th>Company</th><th>Applied on</th><th>Status</th></tr></thead>
  <tbody>
  <c:forEach var="a" items="${applications}">
    <tr><td><c:out value="${a.jobTitle}"/></td><td><c:out value="${a.companyName}"/></td>
        <td><fmt:formatDate value="${a.applicationDate}" pattern="dd MMM yyyy"/></td>
        <td><span class="badge bg-${a.status == 'SELECTED' ? 'success' : a.status == 'REJECTED' ? 'danger' : a.status == 'SHORTLISTED' ? 'info text-dark' : 'secondary'}">${a.status}</span></td></tr>
  </c:forEach>
  <c:if test="${empty applications}"><tr><td colspan="4" class="text-muted">You haven't applied to any job yet.</td></tr></c:if>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
