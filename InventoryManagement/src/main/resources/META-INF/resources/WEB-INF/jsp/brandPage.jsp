<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Brand Page</title>
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
                 <span class="navbar-brand mb-0 h2">Brand List</span>
              </div>
              <div class="col-1">
                 <form action="/Brand"method="post">
                 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                 <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                   Add
                 </button>
              </div>
            </div>
       </div>
<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Brand</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
          <select class="form-select" aria-label="Default select example" id="bCategoryName" name="categoryName" required>
            <option value="" disabled selected>Select Category</option>
            <c:forEach items="${allCategories}" var="category">
            <option value="${category.categoryName}">${category.categoryName}</option>
            </c:forEach>
          </select>
          <div class="mb-3">
               <label for="brandName" class="col-form-label">Brand:</label>
               <input type="text" name="brandName" class="form-control" id="brandName" oninput="addBrandValid()" required>
               <div id="brandNameError" style="color:red;"></div>
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button id="bSaveBtn" class="btn btn-success" type="submit">Save</button>
      </div>
      </form>
    </div>
  </div>
</div>
      <div class="card-body">
      <table id="brandTable">
          <thead>
               <tr>
                  <th>ID</th>
                  <th>Category Name</th>
                  <th>Brand Name</th>
                  <th>Edit</th>
                  <th>Delete</th>
               </tr>
          </thead>
          <%!
            int rowCount=1;
          %>
          <tbody>
             <c:forEach items="${allBrands}" var="brand">
                 <tr>
                    <td><%=rowCount++ %></td>
                    <td>${brand.categoryName}</td>
                    <td>${brand.brandName}</td>
                    <td><button type="button" class="btn btn-warning" data-id="${brand}" data-bs-toggle="modal" data-bs-target="#updateBrandBtn">Update</button></td>
                    <td><a href="deleteBrand?brandId=${brand.brandId}" class="btn btn-danger">Delete</button></td>
                 </tr>
             </c:forEach>
             <% rowCount=1; %>
          </tbody>
      </table>
      </div>
    <!-- Modal -->
    <div class="modal fade" id="updateBrandBtn" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="updateBrandBtnLabel" aria-hidden="true">
       <div class="modal-dialog">
             <form class="modal-content" action="/updateBrand" method="post">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
               <div class="modal-header">
                  <h1 class="modal-title fs-5" id="updateBrandBtnLabel">Update Brand</h1>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
               </div>
               <div class="modal-body">
                 <div class="mb-3">
                   <input type="hidden" id="bUpCategoryName">
                   <label for="upBrandName" class="col-form-label">Brand:</label>
                   <input type="text" name="brandName" class="form-control" id="upBrandName" oninput="upBrandValid()" required>
                   <div id="upBrandNameError" style="color: red;"></div>
                 </div>
               </div>
              <div class="modal-footer">
                 <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                 <button id="upBSaveBtn" class="btn btn-success" type="submit">Save</button>
                 <input type="hidden" name="brandId">
              </div>
             </form>
       </div>
    </div>

    <script>
        $(document).ready(function() {
            $('#brandTable').DataTable({
                paging: true,
                pageLength: 10 // Number of rows per page
            });
        });
    </script>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    <script src="static/js/brand.js" type="text/javascript"></script>
    <script type="application/javascript">
             const modal = document.getElementById("updateBrandBtn");
             let brandName;
             let categoryName;
             modal.addEventListener("show.bs.modal", (event) => {
                 let brand = event.relatedTarget.dataset.id;
                 let target = event.target;
                 const hiddenEl = target.querySelector(".modal-footer input[type='hidden']");
                 const categoryNameInp=document.getElementById("bUpCategoryName");
                 const brandNameInp =document.getElementById("upBrandName");
                 var objectName = "Brands"
                 var brandObject=parseObjectString(brand,objectName);

                 hiddenEl.value = brandObject.brandId;
                 brandName = brandObject.brandName;
                 categoryName = brandObject.categoryName;
                 brandNameInp.value = brandName;

                 function parseBrandString(brandString) {
                 var matches = brandString.match(/brandId=(\d+),\s*brandName=([^\s,]+),\s*categoryId=(\d+),\s*categoryName=([^\s,]+),\s*deleteFlag=(\d+)/);
                    var brandObject = {
                        brandId: parseInt(matches[1]),
                        brandName: matches[2],
                        categoryId: parseInt(matches[3]),
                        categoryName: matches[4],
                        deleteFlag: parseInt(matches[5])
                    };
                   return brandObject;
                 }

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
             function upBrandValid(){
                 console.log("categoryName "+categoryName);
                 brandValid(categoryName,"upBrandName","upBrandNameError","upBSaveBtn",brandName);
             }
    </script>
</body>
</html>