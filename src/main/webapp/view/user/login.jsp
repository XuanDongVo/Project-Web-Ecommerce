<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="../../css/Login.css">
<link rel="stylesheet"
	href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
</head>
<body>
	<section class="container forms">
		<div class="form login">
			<div class="form-content">
				<header>Login</header>
				<div class="form-link">
					<span>Login quickly with your phone number.If you don't have
						account yet, Ecommerce will create one for you</span>
				</div>
					<form onsubmit="handleSingin(event)">
						<div class="field input-field">
							<input type="text" placeholder="Your Phone Number +84" class="input" name = "phoneNumber"
								id="emailOrUsername" maxlength="9"/>
						</div>

						<div class="field button-field">
							<button type="submit" onclick="handleSingin(event)">Next</button>
						</div>
					</form>
			</div>
			<div class="line"></div>
			
			<div class="media-options">
				<a
					href=""
					class="field google"> <i class='bx bxl-google google-icon'></i>
					<span>Login with Google</span>
				</a>

			</div>


		</div>

</body>
</html>