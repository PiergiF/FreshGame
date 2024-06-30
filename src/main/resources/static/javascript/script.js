function addTagField() {
    const spanTags = document.getElementById('tagReference');
    let htmlForm = spanTags.cloneNode(true);
    document.getElementById('tagsContainer').appendChild(htmlForm);
}

function removeTagField(removeButton){
    const formContainer = document.getElementById('tagsContainer');
    //if(!(formContainer.childElementCount == 1)){
    if(formContainer.childElementCount > 1){
        formContainer.removeChild(removeButton.parentElement);
    }else if(formContainer.childElementCount === 1){
        formContainer.parentElement.remove();
    }
    /*
    else{
        alert("È necessario avere almeno un tag.");
    }*/
}

function removeIngredientField(button) {
    const formContainer = document.getElementById('ingredientsContainer');
    //if(!(formContainer.childElementCount == 1)){
    if(formContainer.childElementCount > 1){
        formContainer.removeChild(button.parentElement);
    }
    else{
        alert("È necessario avere almeno un ingrediente.");
    }
}