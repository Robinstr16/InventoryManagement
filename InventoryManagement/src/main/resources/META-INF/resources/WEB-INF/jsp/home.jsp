
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Page</title>
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
    <div class="col-11">
        <span class="navbar-brand mb-0 h2">Category List</span>
    </div>
    <div class="col-1">
       <form action="/Category"method="post">
          <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
             Add
          </button>
   </div>
   </div>
          <!-- Modal -->
          <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Category</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <div class="mb-3">
                     <label for="categoryName" class="col-form-label">Category:</label>
                     <input type="text" name="categoryName" class="form-control" oninput = "addCategoryValid()" id="categoryName" required>
                     <div id="categoryNameError" style="color: red;"></div>
                  </div>
                </div>
                <div class="modal-footer">
                   <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                   <button id ="saveButton" class="btn btn-success" type="submit">Save</button>
                </div>
              </div>
            </div>
          </div>
        </form>
   </div>
   <div class="card-body">
   <table id="categoryTable">
     <thead>
         <tr>
           <th>Id</th>
           <th>Category Name</th>
         <!--  <th>Status</th> -->
           <th>Edit</th>
           <th>Delete</th>
         </tr>
     </thead>
     <tbody>
     <%!
     int rowCount=1;
     %>
       <c:forEach items="${allCategories}" var="category">
         <tr>
           <td><%=rowCount++ %></td>
           <td>${category.categoryName}</td>
           <!-- <td><a href="" class="btn btn-success">Active</a></td> -->
           <td><button type="button" class="btn btn-warning" data-id="${category}" data-bs-toggle="modal" data-bs-target="#updateCategoryBtn">Update</button></td>
           <td><a href="deleteCategory?categoryId=${category.categoryId}" class="btn btn-danger">Delete</td>
         </tr>
       </c:forEach>
       <% rowCount=1; %>
     </tbody>
   </table>
   </div>
     <!-- Modal -->
     <div class="modal fade" id="updateCategoryBtn" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="updateCategoryBtnLabel" aria-hidden="true">
       <div class="modal-dialog">
         <form class="modal-content" action="/updateCategory" method="post">
           <div class="modal-header">
              <h1 class="modal-title fs-5" id="updateCategoryBtnLabel">Update Category</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
           </div>
          <div class="modal-body">
             <div class="mb-3">
                <label for="categoryName" class="col-form-label">Category:</label>
                <input type="text" name="upCategoryName" class="form-control" id="upCategoryName" oninput="upCategoryValid()" required>
                <div id="upCategoryNameError" style="color: red;"></div>
             </div>
          </div>
          <div class="modal-footer">
             <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
             <button id="upSaveBtn" class="btn btn-success" type="submit">Save</button>
             <input type="hidden" name="categoryId">
          </div>
         </form>
     </div>
   </div>
    <script>
    $(document).ready(function() {
       // $('#categoryTable').DataTable({
        //    paging: true,
        //    pageLength: 10 // Number of rows per page
        //});
        new DataTable('#categoryTable');
    });
    </script>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    <script src="static/js/category.js" type="text/javascript"></script>
    <script type="application/javascript">
         const modal = document.getElementById("updateCategoryBtn");
         let categoryName;
         modal.addEventListener("show.bs.modal", (event) => {
         let category = event.relatedTarget.dataset.id;
         let categoryNameInp=document.getElementById("upCategoryName");
         let target = event.target;
         const hiddenEl = target.querySelector(".modal-footer input[type='hidden']");
         var objectName = "category";
         var categoryObject = parseObjectString(category,objectName);
         var categoryId = categoryObject.categoryId;
         categoryName = categoryObject.categoryName;
         hiddenEl.value = categoryId;
         categoryNameInp.value=categoryName;

            function parseObjectString(inputString, objectName) {
                 var pattern = new RegExp(`${objectName}\\(([^)]+)\\)`);
                 var matches = inputString.match(pattern);
                 if (!matches) {
                    // Handle invalid input
                    return null;
                 }

                 var keyValuePairs = matches[1].split(',').map(pair => pair.trim());
                 var resultObject = {};

                 keyValuePairs.forEach(pair => {
                       var [key, value] = pair.split('=').map(part => part.trim());
                       resultObject[key] = isNaN(value) ? value : parseInt(value);
                 });
                 return resultObject;
            }
         });
         function upCategoryValid(){
              categoryValid("upCategoryName","upCategoryNameError","upSaveBtn",categoryName);
         }
    </script>
</body>
</html>