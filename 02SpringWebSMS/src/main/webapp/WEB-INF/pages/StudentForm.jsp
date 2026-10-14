<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow mx-auto" style="max-width: 600px;">

        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Student Details</h4>
        </div>

        <div class="card-body">

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <c:out value="${error}"/>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}${formAction}"
                  method="post">

                <div class="mb-3">
                    <label class="form-label">Name</label>
                    <input type="text" name="name"
                           class="form-control"
                           value="<c:out value='${student.name}'/>"
                           required>
                    <input type="hidden" name="studentId"
                           class="form-control"
                           value="<c:out value='${student.studentId}'/>"
                           >
                </div>

                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email"
                           class="form-control"
                           value="<c:out value='${student.email}'/>"
                           required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mobile</label>
                    <input type="text" name="mobile"
                           class="form-control"
                           value="<c:out value='${student.mobile}'/>"
                           required>
                </div>

                <div class="mb-3">
                    <label class="form-label">City</label>
                    <input type="text" name="city"
                           class="form-control"
                           value="<c:out value='${student.city}'/>"
                           required>
                </div>

                <button type="submit" class="btn btn-success">
                    Save Student
                </button>

                <a href="${pageContext.request.contextPath}/student/list"
                   class="btn btn-secondary">
                    Cancel
                </a>

            </form>

        </div>
    </div>
</div>

</body>
</html>