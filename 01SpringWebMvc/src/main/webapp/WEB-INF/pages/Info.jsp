<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome Page</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<header>
		<h1>Welcome to Our Website</h1>
	</header>
	<main>
		<table>
			<tr>
				<td>Name</td>
				<td>${student.name}</td>
			</tr>
			<tr>
				<td>Email</td>
				<td>${student.email}</td>
			</tr>
			<tr>
				<td>Password</td>
				<td>${student.password}</td>
			</tr>
		</table>

		<c:forEach var="student" items="${students}">

			<table>
				<tr>
					<td>Name</td>
					<td>${student.name}</td>
				</tr>
				<tr>
					<td>Email</td>
					<td>${student.email}</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>${student.password}</td>
				</tr>
			</table>
		</c:forEach>

	</main>
	<footer>
		<p>&copy; 2024 All rights reserved.</p>
	</footer>
</body>
</html>