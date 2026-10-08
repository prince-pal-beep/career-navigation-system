<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="pageTitle" value="Applicants" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<a href="${ctx}/employer/dashboard" class="small">&larr; Back to dashboard</a>
<h2 class="mt-2">Applicants - <c:out value="${job.title}"/></h2>
<table class="table table-striped align-middle">
  <thead><tr><th>Name</th><th>Email</th><th>Qualification</th><th>Exp.</th><th>Applied</th><th>Status</th><th>Update</th></tr></thead>
  <tbody>
  <c:forEach var="a" items="${applicants}">
    <tr><td><c:out value="${a.applicantName}"/></td><td><c:out value="${a.applicantEmail}"/></td>
        <td><c:out value="${a.applicantQualification}"/></td><td>${a.applicantExperience}</td>
        <td><fmt:formatDate value="${a.applicationDate}" pattern="dd MMM yyyy"/></td>
        <td><span class="badge bg-secondary">${a.status}</span></td>
        <td>
          <form method="post" action="${ctx}/employer/applicants" class="d-flex gap-1">
            <input type="hidden" name="applicationId" value="${a.applicationId}">
            <input type="hidden" name="jobId" value="${job.jobId}">
            <select name="status" class="form-select form-select-sm">
              <c:forEach var="s" items="${statuses}"><option value="${s}" ${s == a.status ? 'selected' : ''}>${s}</option></c:forEach>
            </select>
            <button class="btn btn-sm btn-primary">Save</button>
          </form>
        </td></tr>
  </c:forEach>
  <c:if test="${empty applicants}"><tr><td colspan="7" class="text-muted">No applications yet.</td></tr></c:if>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
