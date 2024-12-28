<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>

            .label-orderUnit-container {
                text-align: center;
            }

            .label-orderUnit {
                padding: 10px;
                width: 300px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 8px; /* Set border radius to 0.5px */
                margin-bottom: 10px;
                transition: border 0.3s, box-shadow 0.3s; /* Add transition for smoother effect */

            }

          .label-orderUnit.invalid {
            border: 1px solid red;
                box-shadow: 0 0 20px 5px rgba(255, 0, 0, 0.8); /* Red box shadow with maximum blur for invalid input */
            }
        </style>
</head>
<body>
    <div class="container">
    <h1>Inventory Management System</h1>
    <%@ include file="common/navigation2.jspf" %><br>
    <div class="card">
    <div class="card-header">
       <div class="row">
          <div class="col-11">
          <span class="navbar-brand mb-0 h2">Order List</span>
          </div>
          <div class="col-1">
          <form action="/Order"method="post">
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
                <h1 class="modal-title fs-5" id="staticBackdropLabel">Add Order</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                  <div class="mb-3">
                       <label for="receiverName" class="col-form-label">Enter Receiver Name</label>
                       <input type="text" name="receiverName" class="form-control" id="receiverName" required>
                  </div>
                  <div class="mb-3">
                      <label for="orderDate" class="col-form-label">Order Date:</label>
                      <input type="date" name="orderDate" class="form-control" id="orderDate" required>
                  </div>
                  <div class="mb-3">
                    <label for="receiverAddress" class="form-label">Enter Receiver Address</label>
                    <textarea class="form-control" name="receiverAddress" id="receiverAddress" rows="3" required></textarea>
                  </div>

                  <select class="form-select" aria-label="Default select example" name="productName" id="productName" required>
                    <<option value="" disabled selected>Select Product</option>
                    <c:forEach items="${allProducts}" var="product">
                    <option data-value="${product.productQty}" id="opt" value="${product.productName}">${product.productName}</option>
                    </c:forEach>
                  </select>
                  <div class="label-orderUnit-container">
                     <label>Enter order units</label>
                     <input type="number" class="label-orderUnit" name="orderUnits" oninput="validateOrder()" id="orderUnits" required>
                  </div>
                  <div class="mb-3">
                     <label for="paymentStatus" class="col-form-label">Payment Status</label>
                     <select class="form-select" aria-label="Default select example" name="paymentStatus" id="paymentStatus" required>
                     <option value="Cash">Cash</option>
                     <option value="Debit card">Debit card</option>
                     <option value="Credit card">Credit card</option>
                     <option value="UPI Payment">UPI Payment</option>
                     </select>
                     <!-- <input type="text" name="paymentStatus" class="form-control" id="paymentStatus" required> -->
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button class="btn btn-success" type="submit">Save</button>
              </div>
              </form>
            </div>
          </div>
        </div>
     <div class="card-body">
     <table id="orderTable">
             <thead>
                 <tr>
                     <th>ID</th>
                     <th>Customer Name</th>
                     <th>Order By</th>
                     <th>Product Name</th>
                     <th>Amount</th>
                     <th>Ordered Units</th>
                     <th>Order Date</th>
                     <th>View</th>
                     <th>Edit</th>
                     <th>Delete</th>
                 </tr>
             </thead>
             <%!
               int rowCount=1;
             %>
             <tbody>
                 <c:forEach items="${allOrders}" var="order">
                     <tr>
                        <td><%=rowCount++ %></td>
                        <td>${order.receiverName}</td>
                        <td>${order.orderBy}</td>
                        <td>${order.productDetails}</td>
                        <td>${order.amount}</td>
                        <td>${order.orderUnits}</td>
                        <td>${order.orderDate}</td>
                        <td><a href="viewPdf" class="btn btn-primary">View</a></td>
                        <td><a href="" class="btn btn-warning">Update</a></td>
                        <td><a href="deleteOrder?orderId=${order.orderId}" class="btn btn-danger">Delete</a></td>
                     </tr>
                 </c:forEach>
                 <% rowCount=1; %>
             </tbody>
     </table>
     </div>
    </div>
    <script>
    $(document).ready(function() {
            $('#orderTable').DataTable({
                paging: true,
                pageLength: 10 // Number of rows per page
            });
    });
     function getDataValue(selectElement){
           var selectedOption = selectElement.options[selectElement.selectedIndex];
           return selectedOption.getAttribute("data-value");
     }

     function validateOrder(){
           var orderUnit = document.getElementById("orderUnits");
           var productName = document.getElementById("productName");
           var dataValue = getDataValue(productName);


           console.log("data"+dataValue);
           var inteVal = parseInt(dataValue);
           console.log("order unit"+orderUnit.value);
           console.log("inteVal "+inteVal);
          if (orderUnit.value>inteVal){
              orderUnit.classList.add("invalid");
          }else{
              orderUnit.classList.remove("invalid");
          }
        }
    </script>
    <script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
</body>
</html>