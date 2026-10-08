<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Skill Gap Analysis" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Skill Gap Analysis</h2>
<form method="get" class="row g-2 mb-4">
  <div class="col-md-6">
    <select name="careerId" class="form-select" onchange="this.form.submit()">
      <option value="">-- choose a target career --</option>
      <c:forEach var="c" items="${careers}"><option value="${c.careerId}" ${c.careerId == selectedCareerId ? 'selected' : ''}><c:out value="${c.careerName}"/></option></c:forEach>
    </select>
  </div>
  <div class="col-auto"><button class="btn btn-primary">Analyze</button></div>
</form>
<c:if test="${not empty result}">
  <h5>Readiness for <c:out value="${result.career.careerName}"/>: ${result.readiness}%</h5>
  <div class="progress mb-3"><div class="progress-bar bg-success" style="width:${result.readiness}%"></div></div>
  <table class="table table-bordered align-middle">
    <thead class="table-light"><tr><th>Skill</th><th>Required</th><th>Your level</th><th>Gap</th><th>Status</th></tr></thead>
    <tbody>
    <c:forEach var="r" items="${result.rows}">
      <tr><td><c:out value="${r.skillName}"/></td><td>${r.required}/5</td><td>${r.current}/5</td><td>${r.gap}</td>
          <td><span class="badge bg-${r.missing ? 'danger' : 'success'}">${r.missing ? 'Needs work' : 'OK'}</span></td></tr>
    </c:forEach>
    </tbody>
  </table>
  <a class="btn btn-outline-primary" href="${ctx}/candidate/roadmap?careerId=${selectedCareerId}">Generate learning roadmap</a>
</c:if>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
