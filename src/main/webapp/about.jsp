<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="About" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<h2>About the Project</h2>
<p>The AI-Powered Career Navigation System is a web-based platform that helps students, fresh graduates and career switchers choose a career direction, identify missing skills, follow a personalised learning roadmap and find matching jobs.</p>
<ul>
  <li><b>Candidates</b> build a skill profile, get ranked career recommendations, skill gap reports, roadmaps and apply for jobs.</li>
  <li><b>Employers</b> post jobs, review applicants and shortlist or select candidates.</li>
  <li><b>Administrators</b> manage users, the career &amp; skill database, approve job posts and view reports.</li>
</ul>
<p class="text-muted">Built with Java, JSP, Servlets, MySQL, Bootstrap and Apache Tomcat. Submitted for IGNOU BCA project BCSP-064.</p>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
