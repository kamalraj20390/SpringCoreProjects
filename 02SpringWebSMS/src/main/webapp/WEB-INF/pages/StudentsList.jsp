<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Students List</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body class="bg-light">

	<div class="container mt-5">

		<div class="card shadow">
			<div
				class="card-header bg-primary text-white d-flex
                    justify-content-between align-items-center">

				<h4 class="mb-0">Students Management</h4>

				<a href="${pageContext.request.contextPath}/student/add"
					class="btn btn-light btn-sm"> + Add Student </a>
			</div>

			<div class="card-body">

				<!-- Registration Message -->
				<c:if test="${not empty success}">
					<div class="alert alert-success">
						<c:out value="${success}" />
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>

				<!-- Edit Message -->
				<c:if test="${not empty updateMsg}">
					<div class="alert alert-info">
						<c:out value="${updateMsg}" />
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>

				<!-- Delete Message -->
				<c:if test="${not empty deleteMsg}">
					<div class="alert alert-danger">
						<c:out value="${deleteMsg}" />
						<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
					</div>
				</c:if>

				<!-- Error Message -->
				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<c:out value="${error}" />
					</div>
				</c:if>

				<!-- Students Table -->
				<div class="table-responsive">

					<table
						class="table table-bordered table-striped
                              table-hover align-middle">

						<thead class="table-dark">
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Email</th>
								<th>Mobile</th>
								<th>City</th>
								<th class="text-center">Actions</th>
							</tr>
						</thead>

						<tbody>

							<c:choose>
								<c:when test="${not empty students}">

									<c:forEach var="student" items="${students}">

										<tr>
											<td><c:out value="${student.studentId}" /></td>

											<td><c:out value="${student.name}" /></td>

											<td><c:out value="${student.email}" /></td>

											<td><c:out value="${student.mobile}" /></td>

											<td><c:out value="${student.city}" /></td>

											<td class="text-center">
												<!-- View --> <a
												href="${pageContext.request.contextPath}/student/view/${student.studentId}"
												class="btn btn-info btn-sm text-white"> View </a> <!-- Edit -->
												<a
												href="${pageContext.request.contextPath}/student/edit/${student.studentId}"
												class="btn btn-warning btn-sm"> Edit </a> <!-- Delete -->
												<form
													action="${pageContext.request.contextPath}/student/delete/${student.studentId}"
													method="post" class="d-inline"
													onsubmit="return confirm('Are you sure you want to delete this student?');">

													<button type="submit" class="btn btn-danger btn-sm">
														Delete</button>

												</form>

											</td>
										</tr>

									</c:forEach>

								</c:when>

								<c:otherwise>
									<tr>
										<td colspan="6" class="text-center text-muted">No
											students found. Click Add Student to create a record.</td>
									</tr>
								</c:otherwise>
							</c:choose>

						</tbody>

					</table>

				</div>

				<div class="text-muted">Total Students: ${students.size()}</div>

			</div>
		</div>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">
</script>

</body>
</html>