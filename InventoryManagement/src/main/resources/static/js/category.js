function categoryValid(inputId,errorId,BtnId,categoryNameToCheck) {

    const inputElement = document.getElementById(inputId);
    const errorElement = document.getElementById(errorId);
    const saveBtnElement = document.getElementById(BtnId);
    errorElement.textContent='';
    const enteredCategoryName = inputElement.value;
    const xhr = new XMLHttpRequest();
    xhr.open('GET', '/getCategoryNames', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {

       if (xhr.status >= 200 && xhr.status < 300) {
           const categoryNames = JSON.parse(xhr.response);
           if(categoryNames.includes(enteredCategoryName)&&enteredCategoryName!==categoryNameToCheck){
                errorElement.textContent = "Category name must be unique!";
                inputElement.style.borderColor = "red";
                inputElement.style.boxShadow = "0 0 8px 3px rgba(255, 0, 0, 0.8)";
                saveBtnElement.disabled = true;
           }else{
                inputElement.style.border = "";
                inputElement.style.boxShadow ="";
                saveBtnElement.disabled = false;
           }
       }
    };
    xhr.send();  // Add this line to actually send the request
}

function addCategoryValid(){
   categoryValid("categoryName","categoryNameError","saveButton","");
}

