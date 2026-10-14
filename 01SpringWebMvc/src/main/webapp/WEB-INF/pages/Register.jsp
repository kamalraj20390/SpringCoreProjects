<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome Page</title>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<header>
		<h1>Welcome to Our Website ${test}</h1>
	</header>
	<main>
		<form action="./info" method="post">
			<label for="name">Full Name:</label> <input type="text" id="name"
				name="name" required> <label for="email">Email:</label> <input
				type="email" id="email" name="email" required> <label
				for="password">Password:</label> <input type="password"
				id="password" name="password" required>
			<button type="submit">Register</button>
		</form>
		<a href="./home">Home</a>
	</main>
	<footer>
		<p>&copy; 2024 All rights reserved.</p>
	</footer>
</body>
</html>