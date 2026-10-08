<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Career Recommendations" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Recommended Careers</h2>
<c:if test="${not hasSkills}">
  <div class="alert alert-warning">Add your skills in your <a href="${ctx}/candidate/profile">profile</a> for accurate recommendations.</div>
</c:if>
<div class="row g-3">
<c:forEach var="r" items="${recommendations}">
  <div class="col-md-6">
    <div class="card card-hover h-100"><div class="card-body">
      <div class="d-flex justify-content-between">
        <h5><c:out value="${r.career.careerName}"/> <small class="text-muted">(<c:out value="${r.career.category}"/>)</small></h5>
        <span class="badge bg-${r.score >= 70 ? 'success' : r.score >= 40 ? 'warning text-dark' : 'secondary'} score-badge">${r.score}%</span>
      </div>
      <div class="progress mb-2" style="height:6px"><div class="progress-bar" style="width:${r.score}%"></div></div>
      <p class="small"><c:out value="${r.career.description}"/></p>
      <p class="small mb-1"><b>You meet:</b>
        <c:forEach var="m" items="${r.matchedSkills}"><span class="badge bg-success me-1"><c:out value="${m}"/></span></c:forEach>
        <c:if test="${empty r.matchedSkills}"><span class="text-muted">none yet</span></c:if></p>
      <p class="small"><b>To improve:</b>
        <c:forEach var="m" items="${r.missingSkills}"><span class="badge bg-danger me-1"><c:out value="${m}"/></span></c:forEach>
        <c:if test="${empty r.missingSkills}"><span class="text-muted">nothing - you're ready!</span></c:if></p>
      <a class="btn btn-sm btn-outline-primary" href="${ctx}/candidate/skill-gap?careerId=${r.career.careerId}">Skill gap</a>
      <a class="btn btn-sm btn-outline-secondary" href="${ctx}/candidate/roadmap?careerId=${r.career.careerId}">Roadmap</a>
    </div></div>
  </div>
</c:forEach>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
