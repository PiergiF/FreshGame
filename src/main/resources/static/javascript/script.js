//gestione tag creazione/modifica articolo
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
        }
        /* per poter rimuovere tutti i tag, anche il primo
        else if(formContainer.childElementCount === 1){
            formContainer.parentElement.remove();
        }
        */
        //deve esserci almeno un tag
        else{
            alert("È necessario avere almeno un tag.");
        }
    }

    function addReviewValue(select){
        if(select.value === 'REVIEWS'){
            const form =  select.parentElement.parentElement.parentElement.parentElement;

            const createButton = document.getElementById('createArticleButton');
            
            const divReviewValue = document.createElement('div');
            divReviewValue.className = 'form-group';
            divReviewValue.id = 'divReviewValue';

            const labelReviewValue = document.createElement('label');
            labelReviewValue.className = 'text-white';
            labelReviewValue.textContent = 'Voto Recensione:';

            const inputReviewValue = document.createElement('input');
            inputReviewValue.type = 'number';
            inputReviewValue.min = '0';
            inputReviewValue.max = '10'
            inputReviewValue.step = '0.1';
            inputReviewValue.name = 'reviewValue';
            inputReviewValue.className = 'form-control';

            divReviewValue.appendChild(labelReviewValue);
            divReviewValue.appendChild(inputReviewValue);

            form.removeChild(createButton);

            form.appendChild(divReviewValue);
            form.appendChild(createButton);
        }
    }


//gestione genere aggiunta/modifica gioco
    function addGenreField() {
        const spanGenres = document.getElementById('genreReference');
        let htmlForm = spanGenres.cloneNode(true);
        document.getElementById('genresContainer').appendChild(htmlForm);
    }

    function removeGenreField(removeButton){
        const formContainer = document.getElementById('genresContainer');
        //if(!(formContainer.childElementCount == 1)){
        if(formContainer.childElementCount > 1){
            formContainer.removeChild(removeButton.parentElement);
        }
        /* per poter rimuovere tutti i tag, anche il primo
        else if(formContainer.childElementCount === 1){
            formContainer.parentElement.remove();
        }
        */
        //deve esserci almeno un tag
        else{
            alert("È necessario avere almeno un genere.");
        }
    }

    function addPlatformField() {
        const spanPlatforms = document.getElementById('platformReference');
        let htmlForm = spanPlatforms.cloneNode(true);
        document.getElementById('platformsContainer').appendChild(htmlForm);
    }

    function removePlatformField(removeButton){
        const formContainer = document.getElementById('platformsContainer');
        //if(!(formContainer.childElementCount == 1)){
        if(formContainer.childElementCount > 1){
            formContainer.removeChild(removeButton.parentElement);
        }
        /* per poter rimuovere tutti i tag, anche il primo
        else if(formContainer.childElementCount === 1){
            formContainer.parentElement.remove();
        }
        */
        //deve esserci almeno un tag
        else{
            alert("È necessario avere almeno una piattaforma.");
        }
    }



function updateRegistrationForm(radioInput){
    
    var listClass = document.getElementsByClassName("inputRegistration");
    
    var registrationButton = document.getElementById('registrationButton');

    var chooseRoleRegistration = document.getElementById('chooseRoleRegistration');

    const form = radioInput.parentElement.parentElement.parentElement;
    var loginLink = document.getElementById('loginLink');

    if(radioInput.value === 'READER'){
        for(i=0; i< listClass.length;i++){
            listClass[i].required = false;
        }
        form.removeChild(document.getElementById('inputImageRegistration'));
        form.removeChild(document.getElementById('divBio'));
        form.removeChild(chooseRoleRegistration);
        form.removeChild(registrationButton);
        form.removeChild(loginLink);
        //form.removeChild(form.lastChild);
        form.appendChild(chooseRoleRegistration);
        form.appendChild(registrationButton);
        form.appendChild(loginLink);
    }
    else if(radioInput.value === 'JOURNALIST'){
        for(i=0; i< listClass.length;i++){
            listClass[i].required = true;
        }
        if(document.getElementById('inputImageRegistration') == null){
            form.removeChild(chooseRoleRegistration);
            form.removeChild(registrationButton);
            form.removeChild(loginLink);

            const divImage = document.createElement('div');

            divImage.id = 'inputImageRegistration';
            divImage.className = 'mb-3';
            
            const labelImage = document.createElement('label');
            labelImage.for = 'image';
            labelImage.className = 'text-white';
            labelImage.textContent = 'Foto:';

            const inputImage = document.createElement('input');
            inputImage.type = 'file';
            inputImage.id = 'image';
            inputImage.name = 'image';
            //inputImage.className = 'form-control-file';
            inputImage.required = true;

            divImage.appendChild(labelImage);
            divImage.appendChild(inputImage);

            const divBio = document.createElement('div');
            divBio.className = 'form-group';
            divBio.id = 'divBio';

            const labelBio = document.createElement('label');
            labelBio.for = 'bio';
            labelBio.id = 'labelBio'
            labelBio.className = 'text-white';
            labelBio.textContent = 'Bio:';

            const textareaBio = document.createElement('textarea');
            textareaBio.id = 'bio';
            textareaBio.name = 'bio';
            textareaBio.className ='form-control';

            divBio.appendChild(labelBio);
            divBio.appendChild(textareaBio);


            
            form.enctype = 'multipart/form-data';

            form.appendChild(divImage);
            form.appendChild(divBio);
            form.appendChild(chooseRoleRegistration);
            form.appendChild(registrationButton);
            form.appendChild(loginLink);
        }
    }
}

function updateRating(value) {
    document.getElementById('reader-rating').textContent = parseFloat(value).toFixed(1);
}

/*
function addReviewComment(addButton){
    const divTextReview = addButton.parentElement;
    addButton.remove();

    divTextReview.appendChild();
}
*/

function submitRating(value){
    document.getElementById("reviewForm").submit();
}


// Settings
    function changePasswordClick(divInputPassword){ 
        const divPassword = divInputPassword.parentElement;
        divPassword.lastElementChild.value = 'true';
        let changePasswordBox = document.getElementById('password');
        changePasswordBox.type = 'password';
        changePasswordBox.className = 'form-control';
        divPassword.removeChild(divInputPassword);
    }

    function changeUsernameClick(divInputUsername){
        alert("sarai disconnesso");
        const divUsername = divInputUsername.parentElement;
        divUsername.lastElementChild.value = 'true';
        let changeUsernameBox = document.getElementById('username');
        changeUsernameBox.type = 'text';
        changeUsernameBox.className = 'form-control';
        divUsername.removeChild(divInputUsername);
    }