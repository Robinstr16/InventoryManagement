function brandValid(categoryId,brandId,errorId,btnId,brandNameToCheck){
     let categoryInput;
     if(brandNameToCheck===""){
        const categoryInputElement = document.getElementById(categoryId);
        categoryInput = categoryInputElement.value;
     }else{
        categoryInput = categoryId;
     }
     const brandInputElement = document.getElementById(brandId);
     const errorElement = document.getElementById(errorId);
     const saveBtnElement = document.getElementById(btnId);
     errorElement.textContent='';
     const enteredBrandName = brandInputElement.value;

     const xhr = new XMLHttpRequest();
     xhr.open('GET','/getBrandNames',true);
     xhr.setRequestHeader('Content-Type','application/json');
     xhr.onload = function() {
     if(xhr.status>=200&&xhr.status<300){
          const allBrandNames = JSON.parse(xhr.response);
          brandInputElement.style.border="";
          brandInputElement.style.boxShadow="";
          saveBtnElement.disabled=false;
          if(allBrandNames.includes(categoryInput+" "+brandInputElement.value)&&enteredBrandName!==brandNameToCheck){
                errorElement.textContent="Brand must be unique";
                brandInputElement.style.borderColor="red";
                brandInputElement.style.boxShadow="0 0 8px 3px rgba(255, 0, 0, 0.8)";
                saveBtnElement.disabled=true;
          }
     }
     };
     xhr.send();
}

function addBrandValid(){
   brandValid("bCategoryName","brandName","brandNameError","bSaveBtn","");
}