<!DOCTYPE html>
<!-- Created By CodingNepal -->
<html lang="en" dir="ltr">
   <head>
      <meta charset="utf-8">
      <title>Login Form Design | CodeLab</title>
      <link rel="stylesheet" href="static/css/signupstyle.css">
   </head>
   <body>
      <div class="wrapper">
         <div class="title">
            SignUp Form
         </div>
         <form action="signup" method="post">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="field">
               <input type="text" id="email" name="email" required>
               <label>Email Address</label>
            </div>
            <div class="field">
               <input type="password" id="password" name="password" required>
               <label>Password</label>
            </div>
            <div class="field">
               <input type="text" id="role" name="role" required>
               <label>Role</label>
            </div>
            <div class="field">
               <input type="text" id="name" name="name" required>
               <label>Full Name</label>
            </div>
          <!--  <div class="field">
               <input type="number" id="cNumber" name="cNumber" required>
               <label>Contact Number</label>
            </div> -->
            <div class="field">
               <input type="submit" value="Sign Up">
            </div>
            <div class="signup-link">
               Already have a account? <a href="/login">login</a>
            </div>
         </form>
      </div>
   </body>
</html>