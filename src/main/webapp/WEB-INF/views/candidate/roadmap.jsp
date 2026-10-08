<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Learning Roadmap" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>Learning Roadmap</h2>
<form method="get" class="row g-2 mb-4">
  <div class="col-md-6">
    <select name="careerId" class="form-select" onchange="this.form.submit()">
      <option value="">-- choose a target career --</option>
      <c:forEach var="c" items="${careers}"><option value="${c.careerId}" ${c.careerId == selectedCareerId ? 'selected' : ''}><c:out value="${c.careerName}"/></option></c:forEach>
    </select>
  </div>
  <div class="col-auto"><button class="btn btn-primary">Generate</button></div>
</form>
<c:if test="${not empty result}">
  <c:choose>
    <c:when test="${empty steps}"><div class="alert alert-success">You already meet every required skill for <b><c:out value="${result.career.careerName}"/></b>. Start applying!</div></c:when>
    <c:otherwise>
      <p class="text-muted">Skills are ordered by the biggest gap first.</p>
      <ol class="list-group list-group-numbered">
        <c:forEach var="st" items="${steps}">
          <li class="list-group-item">
            <div class="ms-2">
              <b><c:out value="${st.row.skillName}"/></b>
              <span class="text-muted small">- from level ${st.row.current} to ${st.row.required}</span>
              <ul class="mb-0 mt-1">
                <c:forEach var="r" items="${st.resources}">
                  <li><span class="badge bg-info text-dark"><c:out value="${r.resourceType}"/></span>
                    <c:choose>
                      <c:when test="${not empty r.link}"><a href="<c:out value='${r.link}'/>" target="_blank" rel="noopener"><c:out value="${r.title}"/></a></c:when>
                      <c:otherwise><c:out value="${r.title}"/></c:otherwise>
                    </c:choose></li>
                </c:forEach>
                <c:if test="${empty st.resources}"><li class="text-muted">No resources added yet - ask the admin to add some.</li></c:if>
              </ul>
            </div>
          </li>
        </c:forEach>
      </ol>
    </c:otherwise>
  </c:choose>
</c:if>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
