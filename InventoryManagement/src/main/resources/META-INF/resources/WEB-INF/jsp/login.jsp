<!DOCTYPE html>
<!-- Created By CodingNepal -->
<html lang="en" dir="ltr">
   <head>
      <meta charset="utf-8">
      <title>Login Form Design | CodeLab</title>
      <link rel="stylesheet" href="static/css/loginstyle.css">
   </head>
   <body>
      <div class="wrapper">
         <div class="title">
            Login Form
         </div>
         <form action="${pageContext.request.contextPath}/login" method="post">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="field">
               <input type="text" id="username" name="username" required>
               <label>Email Address</label>
            </div>
            <div class="field">
               <input type="password" id="password" name="password" required>
               <label>Password</label>
            </div>
            <div class="content">
               <div class="checkbox">
                  <input type="checkbox" id="remember-me">
                  <label for="remember-me">Remember me</label>
               </div>
               <div class="pass-link">
                  <a href="#">Forgot password?</a>
               </div>
            </div>
            <div class="field">
               <input type="submit" value="Login">
            </div>
            <div class="signup-link">
               Not a member? <a href="/signup">Signup now</a>
            </div>
         </form>
      </div>
   </body>
</html>