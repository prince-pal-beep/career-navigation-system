<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="My Profile" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>My Profile</h2>
<form method="post" action="${ctx}/candidate/profile">
  <div class="row">
    <div class="col-md-6 mb-3"><label class="form-label">Name</label><input name="name" class="form-control" required value="<c:out value='${profile.name}'/>"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Email</label><input class="form-control" disabled value="<c:out value='${profile.email}'/>"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Qualification</label><input name="qualification" class="form-control" value="<c:out value='${profile.qualification}'/>"></div>
    <div class="col-md-6 mb-3"><label class="form-label">Experience (years)</label><input type="number" min="0" name="experience" class="form-control" value="${profile.experience}"></div>
    <div class="col-12 mb-3"><label class="form-label">Interests (comma separated)</label>
      <input name="interests" class="form-control" placeholder="e.g. data, security, web" value="<c:out value='${profile.interests}'/>"></div>
  </div>
  <h5 class="mt-2">Skills &amp; proficiency</h5>
  <p class="text-muted small">0 = I don't have this skill, 1 = beginner ... 5 = expert.</p>
  <div class="row">
    <c:forEach var="s" items="${skills}">
      <div class="col-md-4 mb-2">
        <div class="input-group">
          <span class="input-group-text flex-grow-1"><c:out value="${s.skillName}"/></span>
          <select name="skill_${s.skillId}" class="form-select" style="max-width:80px">
            <c:forEach begin="0" end="5" var="i">
              <option value="${i}" ${(empty userLevels[s.skillId] ? 0 : userLevels[s.skillId]) == i ? 'selected' : ''}>${i}</option>
            </c:forEach>
          </select>
        </div>
      </div>
    </c:forEach>
  </div>
  <button class="btn btn-primary mt-3">Save profile</button>
  <a class="btn btn-outline-primary mt-3 ms-2" href="${ctx}/candidate/recommendations">See recommended careers</a>
</form>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
