$('.language').select2({
theme: 'bootstrap-5',
  language: "es",
  ajax: {
    url: '../language/select',
    dataType: 'json'
  }
});

$('.originalLanguage').select2({
theme: 'bootstrap-5',
  language: "es",
  ajax: {
    url: '../language/select',
    dataType: 'json'
  }
});

$('.actor').select2({
theme: 'bootstrap-5',
  language: "es",
  ajax: {
    url: '../actor/select',
    dataType: 'json',
    data: function (params) {
      return {
        search: params.term || ''
      }
    }
  }
});

$('.category').select2({
theme: 'bootstrap-5',
  language: "es",
  ajax: {
    url: '../category/select',
    dataType: 'json'
  }
});

$(".special_features").select2({
theme: 'bootstrap-5',
  language: "es",
  tags: true,
  tokenSeparators: [','],
  createTag: function (params) {
    var term = $.trim(params.term);

    if (term === '') {
      return null;
    }

    return {
      id: term,
      text: term,
      newTag: true // add additional parameters
    }
  }
})

$(function() {
  $('#formCreateFilm').on('submit', function (event) {
        event.preventDefault();

        //const csrfToken = document.querySelector('input[name="_csrf"]').value;

        let data = {
           title: document.getElementById("inputTitle").value,
           description: document.getElementById("inputDescription").value,
           releaseYear: document.getElementById("inputYear").value,
           languageId: document.getElementById("inputLanguage").value,
           originalLanguageId: document.getElementById("inputOriginalLanguage").value,
           rentalDuration: document.getElementById("inputRentalDuration").value,
           length: document.getElementById("inputLength").value,
           rentalRate: document.getElementById("inputRentalRate").value,
           replacementCost: document.getElementById("inputReplacementCost").value,
           rating: document.querySelector('input[name="rating"]:checked').value,
           actorId: $('#inputActors').select2('data').map(i => i.id),
           categoryId: $('#inputCategory').select2('data').map(i => i.id),
           specialFeatures: $('#inputSpecialFeatures').select2('data').map(i => i.id),
           inventory: Array.from(document.querySelectorAll("store-inv-component"))
               .map(i => {
                   let key = i.querySelector('select').value;
                   let value = i.querySelector('input').value;
                   return JSON.parse(`{"${key}": ${value}}`);
               }).reduce((a, c) => { return {...a, ...c} },{}),
           poster: document.getElementById("preview-image").src
        };

          $.ajax(`../films`, {
              method: 'POST',
              headers: {
                      'Content-Type': 'application/json'//,
                      //'X-CSRF-TOKEN': csrfToken
                  },
              data: JSON.stringify(data)
          }).done(function (res) {
            document.getElementById("formCreateFilm").reset();
            $("#inputLanguage").empty();
            $("#inputOriginalLanguage").empty();
            $("#inputActors").empty();
            $("#inputCategory").empty();
            $("#inputSpecialFeatures").empty();
            document.querySelectorAll("store-inv-component").forEach(i => i.remove());
            $('#modalAgregar').modal('hide');
            $('#tabla').DataTable().ajax.reload();
          });
  });
});

function addStoreInv() {
    const storeInv = document.createElement('store-inv-component');
    document.getElementById('stores').appendChild(storeInv);
}