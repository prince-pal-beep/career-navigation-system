<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Manage Careers" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Manage Careers</h2>
<div class="card mb-4"><div class="card-body">
  <h5>Add a career</h5>
  <form method="post" action="${ctx}/admin/careers">
    <div class="row">
      <div class="col-md-6 mb-2"><input name="careerName" class="form-control" placeholder="Career name" required maxlength="150"></div>
      <div class="col-md-6 mb-2"><input name="category" class="form-control" placeholder="Category" maxlength="100"></div>
      <div class="col-12 mb-2"><textarea name="description" rows="2" class="form-control" placeholder="Description"></textarea></div>
    </div>
    <p class="small text-muted mb-1">Required level per skill (0 = not required, 5 = expert):</p>
    <div class="row">
      <c:forEach var="s" items="${skills}">
        <div class="col-md-3 col-6 mb-1"><div class="input-group input-group-sm">
          <span class="input-group-text flex-grow-1"><c:out value="${s.skillName}"/></span>
          <select name="imp_${s.skillId}" class="form-select" style="max-width:65px"><c:forEach begin="0" end="5" var="i"><option>${i}</option></c:forEach></select>
        </div></div>
      </c:forEach>
    </div>
    <button class="btn btn-primary mt-2">Add career</button>
  </form>
</div></div>

<table class="table table-striped align-middle">
  <thead><tr><th>Career</th><th>Category</th><th>Required skills</th><th></th></tr></thead>
  <tbody>
  <c:forEach var="c" items="${careers}">
    <tr><td><b><c:out value="${c.careerName}"/></b></td><td><c:out value="${c.category}"/></td>
      <td><c:forEach var="s" items="${c.skills}"><span class="badge bg-light text-dark border me-1"><c:out value="${s.skillName}"/> (${s.level})</span></c:forEach></td>
      <td><form method="post" action="${ctx}/admin/careers" data-confirm="Delete this career?"><input type="hidden" name="action" value="delete"><input type="hidden" name="careerId" value="${c.careerId}"><button class="btn btn-sm btn-danger">Delete</button></form></td></tr>
  </c:forEach>
  </tbody>
</table>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
