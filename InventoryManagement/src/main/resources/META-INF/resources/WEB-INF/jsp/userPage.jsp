<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Page</title>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet" >
    <link href="static/css/jquery.datatables.min.css" rel="stylesheet">
    <link href="static/css/table.css" rel="stylesheet">
    <script src="webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="static/js/jquery.datatables.min.js" type="text/javascript"></script>
</head>
<body>
    <div class="container">
    <h1>Inventory Management System</h1>
    <%@ include file="common/navigation2.jspf" %><br>
    <div class="card">
    <div class="card-header">
       <div class="row">
         <div class="col-12">
           <span class="navbar-brand mb-0 h2">User List</span>
         </div>
       </div>
    </div>
    </form>
    <div class="card-body">
    <table id="userTable">
       <thead>
          <tr>
             <th>ID</th>
             <th>User Name</th>
             <th>Email Id</th>
             <th>Role</th>
             <th>Delete</th>
          </tr>
       </thead>
       <tbody>
          <%!
             int rowCount=1;
          %>
          <c:forEach items="${allUsers}" var="user">
          <tr>
             <td><%=rowCount++ %></td>
             <td>${user.username}</td>
             <td>${user.email}</td>
             <td>${user.role}</td>
            <!-- <td><button type="button" class="btn btn-warning" data-bs-toggle="modal" data-id="${user}" data-bs-target="#updateProductBtn">Update</button> -->
             <td><a href="deleteUser?userId=${user.id}" class="btn btn-danger">Delete</td>
          </tr>
          </c:forEach>
          <% rowCount=1; %>
       </tbody>
    </table>
   </div>
   </div>
    <script>
      $(document).ready(function() {
          $('#userTable').DataTable({
                paging: true,
                pageLength: 10 // Number of rows per page
           });
      });
    </script>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    <script src="static/js/product.js?v=12" type="text/javascript"></script>
</body>
</html>