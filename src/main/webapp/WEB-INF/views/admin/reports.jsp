<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Reports" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Reports &amp; Analytics</h2>
<div class="row g-3">
  <div class="col-md-4"><div class="card h-100"><div class="card-header">Users by role (total ${userCount})</div><ul class="list-group list-group-flush">
    <c:forEach var="e" items="${usersByRole}"><li class="list-group-item d-flex justify-content-between">${e.key}<span class="badge bg-primary">${e.value}</span></li></c:forEach></ul></div></div>
  <div class="col-md-4"><div class="card h-100"><div class="card-header">Jobs by status (total ${jobCount})</div><ul class="list-group list-group-flush">
    <c:forEach var="e" items="${jobsByStatus}"><li class="list-group-item d-flex justify-content-between">${e.key}<span class="badge bg-primary">${e.value}</span></li></c:forEach></ul></div></div>
  <div class="col-md-4"><div class="card h-100"><div class="card-header">Applications by status (total ${applicationCount})</div><ul class="list-group list-group-flush">
    <c:forEach var="e" items="${applicationsByStatus}"><li class="list-group-item d-flex justify-content-between">${e.key}<span class="badge bg-primary">${e.value}</span></li></c:forEach></ul></div></div>
</div>
<p class="mt-3 text-muted">Careers in database: ${careerCount} &middot; Skills in database: ${skillCount}</p>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
