<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Logint</title>
<link rel="stylesheet" href="./css/Login.css">
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
				<form action="sendOtp" method="POST">
					<form onsubmit="handleSingin(event)">
						<div class="field input-field">
							<input type="text" placeholder="Your Phone Number +84" class="input" name = "phoneNumber"
								id="emailOrUsername" maxlength="9"/>
						</div>

						<div class="field button-field">
							<button type="submit" onclick="handleSingin(event)">Next</button>
						</div>
					</form>
				</form>
			</div>
			<div class="line"></div>
			
			
				<div class="media-options">
					<a href="#" class="field facebook"> <i
						class='bx bxl-facebook facebook-icon'></i> <span>Login with
							Facebook</span>
					</a>
				</div>


			<div class="media-options">
				<a
					href="https://accounts.google.com/o/oauth2/auth?scope=profile%20https://www.googleapis.com/auth/userinfo.email&redirect_uri=http://localhost:8080/Ecommerce_Web/login-google&response_type=code&client_id=524025208769-5qnkl0hima275idgajn63hhdo7i3hfeb.apps.googleusercontent.com&approval_prompt=force"
					class="field google"> <i class='bx bxl-google google-icon'></i>
					<span>Login with Google</span>
				</a>

			</div>


		</div>

		<!-- <script>
			function togglePasswordVisibility(passwordId, icon) {
				const passwordField = document.getElementById(passwordId);
				if (passwordField.type === "password") {
					passwordField.type = "text";
					icon.classList.replace('bx-hide', 'bx-show');
				} else {
					passwordField.type = "password";
					icon.classList.replace('bx-show', 'bx-hide');
				}
			}
		</script> -->
</body>
</html>