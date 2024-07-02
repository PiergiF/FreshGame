/*
// JQUERY
$(function () {
    $('select').change(function() {
      var used = new Set;
      $('select').each(function () {
        var reset = false;
        $('option', this).each(function () {
          var hide = used.has($(this).text());
          if (hide && $(this).is(':selected')) reset = true;
          $(this).prop('hidden', hide);
        });
        if (reset) $('option:not([hidden]):first', this).prop('selected', true);  
        used.add($('option:selected', this).text());
      });
    }).trigger('change'); // run at load
  });

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

            //const selectTags = document.getElementById('tags');
            const span = removeButton.parentElement;
            const selectTags = span.firstElementChild;
            alert(span);
            alert(selectTags);
            const selectValue = selectTags.value;
            alert(selectValue);
            const divReviewValue = document.getElementById('divReviewValue');
            const isNull = document.getElementById('pippo');
            alert(divReviewValue);
            alert(isNull);
            if(divReviewValue != null){
                alert('AOOOOOO');
            }

            const bool = selectValue === 'REVIEWS' &&  divReviewValue != null;
            alert (bool);
            
            if( selectValue === 'REVIEWS' &&  divReviewValue != null){
                alert('AOOOO ENTRA');
                //const form =  selectTags.parentElement.parentElement.parentElement.parentElement;
                const form = document.getElementById('formNewArticle');
                alert(form);
                form.removeChild(divReviewValue);
                //const form =  selectTags.parentElement.parentElement.parentElement.parentElement;
                //alert(form);
                //form.removeButton(divReviewValue);
                
                //document.removeChild(divReviewValue);
            }
        }*/
        /* per poter rimuovere tutti i tag, anche il primo
        else if(formContainer.childElementCount === 1){
            formContainer.parentElement.remove();
        }
         interno*/
        //deve esserci almeno un tag
        /*else{
            alert("È necessario avere almeno un tag.");
        }
    }

    function addReviewValue(select){
        if(select.value === 'REVIEWS' && document.getElementById('reviewValue') == null){
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
            inputReviewValue.id = 'reviewValue';
            inputReviewValue.className = 'form-control';

            divReviewValue.appendChild(labelReviewValue);
            divReviewValue.appendChild(inputReviewValue);

            form.removeChild(createButton);

            form.appendChild(divReviewValue);
            form.appendChild(createButton);
        }
        if(select.value != 'REVIEWS'){
            alert("ao");
        }
    }
*/


//gestione tag creazione/modifica articolo

    $(function () {
        // Definisci la funzione per gestire le opzioni duplicate
        function updateSelectOptions() {
            var used = new Set;
            $('select').each(function () {
                var reset = false;
                $('option', this).each(function () {
                    var optionText = $(this).text();
                    var optionValue = $(this).val();
                    var isDisabled = $(this).prop('disabled');

                    // Non nascondere l'opzione se il valore è vuoto o è disabilitata
                    var hide = used.has(optionText) && optionValue !== "" && !isDisabled;

                    //var hide = used.has($(this).text());
                    if (hide && $(this).is(':selected')) reset = true;
                    $(this).prop('hidden', hide);
                });
                if (reset) $('option:not([hidden]):first', this).prop('selected', true);
                used.add($('option:selected', this).text());
            });
        }

        // Esegui la funzione all'inizio
        updateSelectOptions();

        // Associa l'evento change a tutti i select
        $(document).on('change', 'select', function() {
            updateSelectOptions();
        });

        var count = 1;

        // Funzioni per aggiungere e rimuovere i tag
        window.addTagField = function() {

            

            if(count < 3){

                const spanTags = document.getElementById('tagReference');
                let htmlForm = spanTags.cloneNode(true);

                // Reimposta il valore selezionato e mostra l'opzione "Seleziona il tag"
                let selectElement = htmlForm.querySelector('select');
                selectElement.selectedIndex = 0;

                document.getElementById('tagsContainer').appendChild(htmlForm);
                
                count ++;

                updateSelectOptions(); // Aggiorna le opzioni quando aggiungi un nuovo select
            }else{
                alert('Hai finito le opzioni selezionabili');
            }
            
            /*
            const spanTags = document.getElementById('tagReference');
            let htmlForm = spanTags.cloneNode(true);

            // Reimposta il valore selezionato e mostra l'opzione "Seleziona il tag"
            let selectElement = htmlForm.querySelector('select');
            selectElement.selectedIndex = 0;

            document.getElementById('tagsContainer').appendChild(htmlForm);
            */

            /*
            const selectTemplate = `
                <select name="tags" class="form-control d-inline-block" style="width: auto;" onchange="addReviewValue(this)" required>
                    <option value="" selected disabled>Seleziona il tag</option>
                    <option th:each="tag : ${tags}" th:value="${tag}" th:text="#{ ${tag} }">_tag_</option>
                </select>
                <button type="button" class="btn btn-danger" onclick="removeTagField(this)">Rimuovi tag</button>
            `;

            const span = document.createElement('span');
            span.className = 'mb-3';
            span.innerHTML = selectTemplate;

            document.getElementById('tagsContainer').appendChild(span);
            */

            //updateSelectOptions(); // Aggiorna le opzioni quando aggiungi un nuovo select
        };

        window.removeTagField = function(removeButton){
            const formContainer = document.getElementById('tagsContainer');
            if(formContainer.childElementCount > 1){
                formContainer.removeChild(removeButton.parentElement);
                
                count --;

                const span = removeButton.parentElement;
                const selectTags = span.firstElementChild;
                const selectValue = selectTags.value;
                const divReviewValue = document.getElementById('divReviewValue');
                
                if( selectValue === 'REVIEWS' &&  divReviewValue != null){
                    const form = document.getElementById('formArticleData');
                    form.removeChild(divReviewValue);
                }

                updateSelectOptions(); // Aggiorna le opzioni quando rimuovi un select
            } else {
                alert("È necessario avere almeno un tag.");
            }
        };

        window.addReviewValue = function(select){
            if(select.value === 'REVIEWS' && document.getElementById('reviewValue') == null){
                const form =  select.parentElement.parentElement.parentElement.parentElement;

                const createButton = document.getElementById('confirmArticleButton');
                
                const divReviewValue = document.createElement('div');
                divReviewValue.className = 'form-group';
                divReviewValue.id = 'divReviewValue';

                const labelReviewValue = document.createElement('label');
                labelReviewValue.className = 'text-white';
                labelReviewValue.textContent = 'Voto Recensione:';

                const inputReviewValue = document.createElement('input');
                inputReviewValue.type = 'number';
                inputReviewValue.min = '0';
                inputReviewValue.max = '10';
                inputReviewValue.step = '0.1';
                inputReviewValue.name = 'reviewValue';
                inputReviewValue.id = 'reviewValue';
                inputReviewValue.className = 'form-control';

                divReviewValue.appendChild(labelReviewValue);
                divReviewValue.appendChild(inputReviewValue);

                form.removeChild(createButton);

                form.appendChild(divReviewValue);
                form.appendChild(createButton);
            }
        };
    });




    function removeImageFromArticle(button, index){

        const inputHidden = document.createElement('input');
        inputHidden.type = 'hidden';
        inputHidden.name = 'removeImageIndexes';
        inputHidden.value = index;

        button.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.appendChild(inputHidden);

        //button.parentElement.remove();
        const carouselItem = button.closest('.carousel-item' + index);
        //const removeImage = courosel.closest(index);
        carouselItem.remove();

        // Rimuovi il corrispondente indicatore
        const indicator = document.querySelector(`.carousel-indicators [data-slide-to="${index}"]`);
        if (indicator) {
            indicator.remove();
        }

        // Trova il carosello e i suoi elementi
        const carousel = document.querySelector('#imageCarousel .carousel-inner');
        const items = carousel.querySelectorAll('.carousel-item');

        // Se ci sono elementi rimasti nel carosello
        if (items.length > 0) {
            // Se l'elemento rimosso era l'elemento attivo
            if (carouselItem.classList.contains('active')) {
                // Rendi attivo il primo elemento rimasto
                items[0].classList.add('active');
            }
        } else {
            // Se non ci sono elementi rimasti, disabilita i controlli del carosello
            document.querySelector('.carousel-control-prev').style.display = 'none';
            document.querySelector('.carousel-control-next').style.display = 'none';
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