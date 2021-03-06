<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Tutorial Admin</title>
</head>
<body>
<div class="container">
<div class="col-md-6" style="float:none; margin:0 auto;">
		<c:choose>
			<c:when test="${!empty success}">
				<c:choose>
				  <c:when test="${success==true}">
				  <div class="alert alert-success alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  ${message}
				  </div>
				  </c:when>
				   <c:otherwise>
				  <div class="alert alert-warning alert-dismissible" role="alert">
				  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				  ${message}
				  </div>
				  </c:otherwise>
				  </c:choose>
			</c:when>
		</c:choose>
		<a href="./" class="btn btn-link">Back to Tutorials</a>
        <h2>Add Tutorial</h2>
        <form:form action="./new" commandName="tutorial" method="POST" enctype="utf8" role="form">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
						
						<div class="form-group">
							<label class="control-label" for="tutorial-title">Title:</label>
							<form:input path="title" cssClass="form-control" />
							<form:errors path="title" cssClass="help-block" />
						</div>
						
						<div class="form-group">
							<label class="control-label" for="tutorial-description">Description:</label>
							<form:input path="description" cssClass="form-control" />
							<form:errors path="description" cssClass="help-block" />
						</div>
						
						<div class="form-group">
							<label class="control-label" for="tutorial-url">Url:</label>
							<form:input path="url" cssClass="form-control" />
							<form:errors path="url" cssClass="help-block" />
						</div>
						<button type="submit" class="btn btn-default btn-primary">
							Add
						</button>
		</form:form>
	</div>
	</div>
</body>
</html>