<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Sign Up" scope="request"/>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="row justify-content-center"><div class="col-md-6">
  <div class="card shadow-sm"><div class="card-body p-4">
    <h3 class="mb-3">Create account</h3>
    <form method="post" action="${ctx}/register">
      <div class="mb-3"><label class="form-label">I am a</label>
        <select name="role" id="role" class="form-select">
          <option value="CANDIDATE" ${param.role != 'EMPLOYER' ? 'selected' : ''}>Candidate (student / job seeker)</option>
          <option value="EMPLOYER"  ${param.role == 'EMPLOYER' ? 'selected' : ''}>Employer</option>
        </select></div>
      <div class="mb-3"><label class="form-label">Full name</label><input name="name" class="form-control" required maxlength="100" value="<c:out value='${param.name}'/>"></div>
      <div class="mb-3"><label class="form-label">Email</label><input type="email" name="email" class="form-control" required maxlength="100" value="<c:out value='${param.email}'/>"></div>
      <div class="row">
        <div class="col-md-6 mb-3"><label class="form-label">Password</label><input type="password" name="password" class="form-control" required minlength="6"></div>
        <div class="col-md-6 mb-3"><label class="form-label">Confirm password</label><input type="password" name="confirm" class="form-control" required></div>
      </div>
      <div class="candidate-only">
        <div class="row">
          <div class="col-md-8 mb-3"><label class="form-label">Qualification</label><input name="qualification" class="form-control" placeholder="e.g. BCA" value="<c:out value='${param.qualification}'/>"></div>
          <div class="col-md-4 mb-3"><label class="form-label">Experience (yrs)</label><input type="number" min="0" name="experience" class="form-control" value="0"></div>
        </div>
      </div>
      <div class="employer-only" style="display:none">
        <div class="mb-3"><label class="form-label">Company name</label><input name="companyName" class="form-control" maxlength="150" value="<c:out value='${param.companyName}'/>"></div>
        <div class="mb-3"><label class="form-label">Contact number</label><input name="contactNo" class="form-control" maxlength="20" value="<c:out value='${param.contactNo}'/>"></div>
      </div>
      <button class="btn btn-primary w-100">Register</button>
    </form>
  </div></div>
</div></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
