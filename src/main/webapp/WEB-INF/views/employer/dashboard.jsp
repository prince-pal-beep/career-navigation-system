<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Employer Dashboard" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="d-flex justify-content-between align-items-center mb-3">
  <h2 class="mb-0"><c:out value="${sessionScope.employer.companyName}"/> - Dashboard</h2>
  <a class="btn btn-primary" href="${ctx}/employer/post-job">+ Post a job</a>
</div>
<table class="table table-striped align-middle">
  <thead><tr><th>Title</th><th>Location</th><th>Status</th><th>Applicants</th><th></th></tr></thead>
  <tbody>
  <c:forEach var="j" items="${jobs}">
    <tr><td><c:out value="${j.title}"/></td><td><c:out value="${j.location}"/></td>
        <td><span class="badge bg-${j.status == 'APPROVED' ? 'success' : j.status == 'REJECTED' ? 'danger' : 'warning text-dark'}">${j.status}</span></td>
        <td>${j.applicantCount}</td>
        <td><a class="btn btn-sm btn-outline-primary" href="${ctx}/employer/applicants?jobId=${j.jobId}">View applicants</a></td></tr>
  </c:forEach>
  <c:if test="${empty jobs}"><tr><td colspan="5" class="text-muted">No jobs posted yet.</td></tr></c:if>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
