<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Manage Skills" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Manage Skills</h2>
<form method="post" action="${ctx}/admin/skills" class="row g-2 mb-4">
  <div class="col-md-5"><input name="skillName" class="form-control" placeholder="Skill name" required maxlength="100"></div>
  <div class="col-md-3"><select name="skillType" class="form-select"><option>Technical</option><option>Soft</option></select></div>
  <div class="col-auto"><button class="btn btn-primary">Add skill</button></div>
</form>
<table class="table table-striped align-middle">
  <thead><tr><th>#</th><th>Skill</th><th>Type</th><th></th></tr></thead>
  <tbody>
  <c:forEach var="s" items="${skills}">
    <tr><td>${s.skillId}</td><td><c:out value="${s.skillName}"/></td><td>${s.skillType}</td>
      <td><form method="post" action="${ctx}/admin/skills" data-confirm="Delete this skill?"><input type="hidden" name="action" value="delete"><input type="hidden" name="skillId" value="${s.skillId}"><button class="btn btn-sm btn-danger">Delete</button></form></td></tr>
  </c:forEach>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
