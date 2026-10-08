<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Admin Dashboard" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Admin Dashboard</h2>
<div class="row g-3 mb-4 text-center">
  <div class="col"><div class="card"><div class="card-body"><div class="h3 mb-0">${userCount}</div><small>Users</small></div></div></div>
  <div class="col"><div class="card"><div class="card-body"><div class="h3 mb-0">${careerCount}</div><small>Careers</small></div></div></div>
  <div class="col"><div class="card"><div class="card-body"><div class="h3 mb-0">${skillCount}</div><small>Skills</small></div></div></div>
  <div class="col"><div class="card"><div class="card-body"><div class="h3 mb-0">${jobCount}</div><small>Jobs</small></div></div></div>
  <div class="col"><div class="card"><div class="card-body"><div class="h3 mb-0">${applicationCount}</div><small>Applications</small></div></div></div>
</div>

<h4>Job moderation</h4>
<table class="table table-sm table-striped align-middle">
  <thead><tr><th>Title</th><th>Company</th><th>Location</th><th>Status</th><th>Actions</th></tr></thead>
  <tbody>
  <c:forEach var="j" items="${jobs}">
    <tr><td><c:out value="${j.title}"/></td><td><c:out value="${j.companyName}"/></td><td><c:out value="${j.location}"/></td>
      <td><span class="badge bg-${j.status == 'APPROVED' ? 'success' : j.status == 'REJECTED' ? 'danger' : 'warning text-dark'}">${j.status}</span></td>
      <td class="d-flex gap-1">
        <form method="post" action="${ctx}/admin/dashboard"><input type="hidden" name="action" value="jobStatus"><input type="hidden" name="jobId" value="${j.jobId}"><input type="hidden" name="status" value="APPROVED"><button class="btn btn-sm btn-success">Approve</button></form>
        <form method="post" action="${ctx}/admin/dashboard"><input type="hidden" name="action" value="jobStatus"><input type="hidden" name="jobId" value="${j.jobId}"><input type="hidden" name="status" value="REJECTED"><button class="btn btn-sm btn-warning">Reject</button></form>
        <form method="post" action="${ctx}/admin/dashboard" data-confirm="Delete this job?"><input type="hidden" name="action" value="deleteJob"><input type="hidden" name="jobId" value="${j.jobId}"><button class="btn btn-sm btn-danger">Delete</button></form>
      </td></tr>
  </c:forEach>
  <c:if test="${empty jobs}"><tr><td colspan="5" class="text-muted">No jobs yet.</td></tr></c:if>
  </tbody>
</table>

<h4 class="mt-4">Users</h4>
<table class="table table-sm table-striped align-middle">
  <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th></tr></thead>
  <tbody>
  <c:forEach var="u" items="${users}">
    <tr><td><c:out value="${u.name}"/></td><td><c:out value="${u.email}"/></td><td>${u.role}</td>
      <td><span class="badge bg-${u.status == 'ACTIVE' ? 'success' : 'danger'}">${u.status}</span></td>
      <td class="d-flex gap-1">
        <c:if test="${u.role != 'ADMIN'}">
          <form method="post" action="${ctx}/admin/dashboard"><input type="hidden" name="action" value="userStatus"><input type="hidden" name="userId" value="${u.userId}"><input type="hidden" name="status" value="${u.status == 'ACTIVE' ? 'BLOCKED' : 'ACTIVE'}"><button class="btn btn-sm btn-outline-secondary">${u.status == 'ACTIVE' ? 'Block' : 'Unblock'}</button></form>
          <form method="post" action="${ctx}/admin/dashboard" data-confirm="Delete this user and all their data?"><input type="hidden" name="action" value="deleteUser"><input type="hidden" name="userId" value="${u.userId}"><button class="btn btn-sm btn-danger">Delete</button></form>
        </c:if>
      </td></tr>
  </c:forEach>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
