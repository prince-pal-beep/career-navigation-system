<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Home" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<section class="hero p-5 mb-4 text-center">
  <h1 class="display-5 fw-bold">AI-Powered Career Navigation System</h1>
  <p class="lead">Discover the right career, find your skill gaps, follow a learning roadmap and land the job.</p>
  <a class="btn btn-light btn-lg me-2" href="${ctx}/register">Get Started</a>
  <a class="btn btn-outline-light btn-lg" href="${ctx}/login">Login</a>
</section>
<div class="row g-3 text-center">
  <div class="col-md-3"><div class="card card-hover h-100"><div class="card-body"><h5>Career Recommendation</h5><p class="small text-muted">Careers ranked by how well your skills and interests fit.</p></div></div></div>
  <div class="col-md-3"><div class="card card-hover h-100"><div class="card-body"><h5>Skill Gap Analysis</h5><p class="small text-muted">See exactly which skills you still need for a target career.</p></div></div></div>
  <div class="col-md-3"><div class="card card-hover h-100"><div class="card-body"><h5>Learning Roadmap</h5><p class="small text-muted">Step-by-step courses and resources for each missing skill.</p></div></div></div>
  <div class="col-md-3"><div class="card card-hover h-100"><div class="card-body"><h5>Job Matching</h5><p class="small text-muted">Apply to jobs and track your application status.</p></div></div></div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
