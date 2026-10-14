<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Student</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow mx-auto" style="max-width: 650px;">

        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Student Details</h4>
        </div>

        <div class="card-body">

            <div class="text-center mb-4">
                <div class="rounded-circle bg-primary text-white d-inline-flex
                            justify-content-center align-items-center"
                     style="width: 90px; height: 90px; font-size: 35px;">
                    <c:out value="${student.name.substring(0,1)}"/>
                </div>

                <h4 class="mt-3">
                    <c:out value="${student.name}"/>
                </h4>

                <span class="badge bg-secondary">
                    Student ID: <c:out value="${student.studentId}"/>
                </span>
            </div>

            <table class="table table-bordered">

                <tr>
                    <th class="table-light" style="width: 35%;">Student ID</th>
                    <td><c:out value="${student.studentId}"/></td>
                </tr>

                <tr>
                    <th class="table-light">Name</th>
                    <td><c:out value="${student.name}"/></td>
                </tr>

                <tr>
                    <th class="table-light">Email</th>
                    <td><c:out value="${student.email}"/></td>
                </tr>

                <tr>
                    <th class="table-light">Mobile</th>
                    <td><c:out value="${student.mobile}"/></td>
                </tr>

                <tr>
                    <th class="table-light">City</th>
                    <td><c:out value="${student.city}"/></td>
                </tr>

            </table>

            <div class="d-flex justify-content-between mt-4">

                <a href="${pageContext.request.contextPath}/student/list"
                   class="btn btn-secondary">
                    Back to List
                </a>

                <div>
                    <a href="${pageContext.request.contextPath}/student/edit/${student.studentId}"
                       class="btn btn-warning">
                        Edit
                    </a>

                    <form action="${pageContext.request.contextPath}/student/delete/${student.studentId}"
                          method="post"
                          class="d-inline"
                          onsubmit="return confirm('Are you sure you want to delete this student?');">

                        <button type="submit" class="btn btn-danger">
                            Delete
                        </button>

                    </form>
                </div>

            </div>

        </div>
    </div>

</div>

</body>
</html>