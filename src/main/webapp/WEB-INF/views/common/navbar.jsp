<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="role" value="${sessionScope.user.role}"/>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <a class="navbar-brand fw-bold" href="${ctx}/index.jsp">CareerNav AI</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="nav">
      <ul class="navbar-nav me-auto">
        <c:choose>
          <c:when test="${role == 'CANDIDATE'}">
            <li class="nav-item"><a class="nav-link" href="${ctx}/candidate/profile">Profile</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/candidate/recommendations">Careers</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/candidate/skill-gap">Skill Gap</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/candidate/roadmap">Roadmap</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/candidate/jobs">Jobs</a></li>
          </c:when>
          <c:when test="${role == 'EMPLOYER'}">
            <li class="nav-item"><a class="nav-link" href="${ctx}/employer/dashboard">Dashboard</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/employer/post-job">Post Job</a></li>
          </c:when>
          <c:when test="${role == 'ADMIN'}">
            <li class="nav-item"><a class="nav-link" href="${ctx}/admin/dashboard">Dashboard</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/admin/careers">Careers</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/admin/skills">Skills</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/admin/dashboard?view=reports">Reports</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><a class="nav-link" href="${ctx}/index.jsp">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/about.jsp">About</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/contact.jsp">Contact</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
      <ul class="navbar-nav">
        <c:choose>
          <c:when test="${not empty sessionScope.user}">
            <li class="nav-item"><span class="navbar-text me-3"><c:out value="${sessionScope.user.name}"/></span></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/change-password">Change Password</a></li>
            <li class="nav-item"><a class="nav-link" href="${ctx}/logout">Logout</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><a class="nav-link" href="${ctx}/login">Login</a></li>
            <li class="nav-item"><a class="btn btn-light btn-sm ms-2" href="${ctx}/register">Sign Up</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>
