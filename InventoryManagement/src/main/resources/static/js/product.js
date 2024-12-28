
const modal = document.getElementById("updateProductBtn");
let productName;
modal.addEventListener("show.bs.modal", (event) => {

         let product = event.relatedTarget.dataset.id;
         let target = event.target;
         var objectName = "Products";
         var objectProduct = parseObjectString(product,objectName);
         console.log("objectProduct "+objectProduct);
         const hiddenEl = target.querySelector(".modal-footer input[type='hidden']");
         productNameInp = document.getElementById("upProductName");
         productPriceInp = document.getElementById("upProductPrice");
         productQtyInp = document.getElementById("upProductQty");
         saveBtnElement = document.getElementById("upPSaveBtn");
         const errorElement = document.getElementById("upProductNameError");
         errorElement.textContent='';
         productNameInp.style.border = "";
         productNameInp.style.boxShadow ="";
         saveBtnElement.disabled=false;
         hiddenEl.value = objectProduct.productId;
         productName = objectProduct.productName;
         productNameInp.value = objectProduct.productName;
         productPriceInp.value = objectProduct.productPrice;
         productQtyInp.value = objectProduct.productQty;
});

function upProductValid(){
    console.log("upProductValid  entered !");
    validProductName("upProductName","upProductNameError","upPSaveBtn",productName);
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

function validProductName(nameId,errorId,saveBtnId,productNameToCheck){

    const inputElement = document.getElementById(nameId);
    const errorElement = document.getElementById(errorId);
    const saveBtnElement = document.getElementById(saveBtnId);
    errorElement.textContent='';
    const xhr = new XMLHttpRequest();
    xhr.open('GET','/getProductNames',true);
    xhr.setRequestHeader('Content-Type','application/json');

    xhr.onload = function() {
        if(xhr.status>=200 && xhr.status< 300){
           const productNames = JSON.parse(xhr.response);
           if(productNames.includes(inputElement.value)&&(inputElement.value)!==productNameToCheck){
              errorElement.textContent = "Product Name must be Unique!!";
              inputElement.style.borderColor = "red";
              inputElement.style.boxShadow = "0 0 8px 3px rgba(255, 0, 0, 0.8)";
              saveBtnElement.disabled=true;
           }else{
              inputElement.style.border = "";
              inputElement.style.boxShadow ="";
              saveBtnElement.disabled = false;
           }
        }
    }
    xhr.send();
}


function addProductValid(){
   console.log("entered !");
   validProductName("productName","productNameError","pSaveBtn","");
}