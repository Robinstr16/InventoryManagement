<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}">
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
          <span class="navbar-brand mb-0 h2">Product List</span>
         </div>
         <div class="col-1">
          <form action="/Product" method="post">
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
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Product</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                      <div class="mb-3">
                      <select class="form-select" aria-label="Default select example" id="categoryId" name="categoryId" required>
                           <option value="" disabled selected>Select Category</option>
                           <c:forEach items="${allCategories}" var="category">
                           <option value="${category.categoryId}">${category.categoryName}</option>
                           </c:forEach>
                      </select>
                      </div>
                      <div class="mb-3">
                      <select class="form-select" aria-label="Default select example" name="brandId" id="brandId" required>
                           <option value="" disabled selected>Select Brand</option>
                      </select>
                      </div>
                      <div class="mb-3">
                           <label for="productName" class="col-form-label">Product:</label>
                           <input type="text" name="productName" class="form-control" id="productName" oninput="addProductValid()" required>
                           <div id="productNameError" style="color:red;"></div>
                      </div>
                      <div class="mb-3">
                           <label for="productPrice" class="col-form-label">Enter Product Base rice</label>
                           <input type="number" name="productPrice" class="form-control" id="productPrice" required>
                      </div>
                      <div class="mb-3">
                         <label for="productQty" class="col-form-label">Enter Product Quantity</label>
                         <input type="number" name="productQty" class="form-control" id="productQty" required>
                      </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button id="pSaveBtn" class="btn btn-success" type="submit">Save</button>
                  </div>
                </div>
              </div>
            </div>
          </form>
    <div class="card-body">
    <table id="productTable">
       <thead>
          <tr>
             <th>ID</th>
             <th>Category Name</th>
             <th>Brand Name</th>
             <th>Product Name</th>
             <th>Product Price</th>
             <th>Product Quantity</th>
             <th>Edit</th>
             <th>Delete</th>
          </tr>
       </thead>
       <tbody>
          <%!
             int rowCount=1;
          %>
          <c:forEach items="${allProducts}" var="product">
          <tr>
             <td><%=rowCount++ %></td>
             <td>${product.categoryName}</td>
             <td>${product.brandName}</td>
             <td>${product.productName}</td>
             <td>${product.productPrice}</td>
             <td>${product.productQty} Nos</td>
             <td><button type="button" class="btn btn-warning" data-bs-toggle="modal" data-id="${product}" data-bs-target="#updateProductBtn">Update</button>
             <td><a href="deleteProduct?productId=${product.productId}" class="btn btn-danger">Delete</td>
          </tr>
          </c:forEach>
          <% rowCount=1; %>
       </tbody>
    </table>
   </div>
   </div>
    <!-- Update Product Modal -->
        <div class="modal fade" id="updateProductBtn" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="updateProductLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="updateProductLabel">Update Product</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <form action="/updateProduct" method="post">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="modal-body">
                  <div class="mb-3">
                       <label for="upProductName" class="col-form-label">Product Name:</label>
                       <input type="text" name="upProductName" class="form-control" id="upProductName" oninput="upProductValid()" required>
                       <div id="upProductNameError" style="color:red;"></div>
                  </div>
                  <div class="mb-3">
                       <label for="upProductPrice" class="col-form-label">Enter Product Base rice</label>
                       <input type="number" name="upProductPrice" class="form-control" id="upProductPrice" required>
                  </div>
                  <div class="mb-3">
                     <label for="upProductQty" class="col-form-label">Enter Product Quantity</label>
                     <input type="number" name="upProductQty" class="form-control" id="upProductQty" required>
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button id="upPSaveBtn" class="btn btn-success" type="submit">Save</button>
                <input type="hidden" name="productId">
              </div>
              </form>
            </div>
          </div>
        </div>
    <script>
      $(document).ready(function() {
          $('#productTable').DataTable({
                    paging: true,
                    pageLength: 10 // Number of rows per page
           });
       });
      const selectElement = document.getElementById("categoryId");
      const brandSelectEl = document.getElementById("brandId");
      selectElement.addEventListener("change", function (event) {
      const selectedOption = event.currentTarget.value;
      const xhr = new XMLHttpRequest();
      const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");

      xhr.open('POST', '/getBrand?_csrf='+csrfToken +'&categoryId='+selectedOption, true);
         xhr.setRequestHeader('_csrf', csrfToken);
         xhr.setRequestHeader('Content-Type', 'application/json');

        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 300) {
                while (brandSelectEl.firstChild) {
                    brandSelectEl.removeChild(brandSelectEl.firstChild);
                }
                const brands = JSON.parse(xhr.response);
                brands.forEach((brand) => {
                console.log(brand);
                const option_tag = document.createElement("option");
                option_tag.value = brand.brandId;
                option_tag.innerText = brand.brandName;
                brandSelectEl.append(option_tag);
                });
            } else {
                console.error('Error:', xhr.statusText);
            }
        };
        xhr.send();
        console.log("Selected option: " + selectedOption);
      });

    </script>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
    <script src="static/js/product.js?v=12" type="text/javascript"></script>
</body>
</html>