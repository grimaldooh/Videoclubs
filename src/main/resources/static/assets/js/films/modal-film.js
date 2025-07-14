class FilmDetailsModal extends HTMLElement {
  constructor() {
    super();
    this.film;
  }

  connectedCallback() {
    this.render();
    this.loadData();
    new bootstrap.Modal(this.querySelector('.modal'), {}).show()
  }

  static get observedAttributes(){
      return ['film'];
  }

  attributeChangedCallback(nombreAttr, oldValue, newValue){
      switch(nombreAttr){
          case "film":
              this.film = newValue;
          break;
      }
  }

  render() {
    this.innerHTML = `
    <div class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-xl">
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="modal-title" id="exampleModalLabel">Detalle de la Pel&iacute;cula</span>
                        <button aria-label="Close" class="modal-x" data-dismiss="modal" type="button">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-5">
                                <img id="det_poster"
                                     src="https://imagenes.elpais.com/resizer/n48DlQR-crZegLmg09HVeig_Qf0=/414x0/filters:focal(1133x620:1143x630)/cloudfront-eu-central-1.images.arcpublishing.com/prisa/ZZIDTAAXOZEQXPZ2BP3VGO7KIY.jpg"
                                     width="100%">
                            </div>
                            <div class="col-7">
                                <div class="row">
                                    <div class="col-12">
                                        <h2 id="det_title"></h2>
                                    </div>
                                </div>
                                <div class="row mb-4">
                                    <div class="col-12">
                                        <div class="badge badge-success text-wrap p-2 mr-3"><i class="bi bi-translate"></i>
                                            <span id="det_language"></span></div>
                                        <div class="badge badge-secondary text-wrap p-2 mr-1"><span id="det_rating"></span>
                                        </div>
                                        <div class="badge badge-dark text-wrap p-2 mr-1"><i class="bi bi-clock"></i> <span
                                                id="det_length"></span></div>
                                        <div class="badge badge-primary text-wrap p-2"><i
                                                class="bi bi-camera-reels-fill"></i> <span id="det_category"></span></div>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <div class="col-12">
                                        <p id="det_description"></p>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-12"><b>Reparto:</b></div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <p class="font-italic" id="det_reparto"></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="offset-7 col-3 border-bottom">
                                        <b>Precio de renta:</b>
                                    </div>
                                    <div class="col-2 border-bottom">
                                        <p id="det_rentalRate"></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">

                        </div>
                    </div>
                    <div class="modal-footer">
                        <a about="_blank" class="btn red-btn" href="../films/pdf/${this.film}">
                            <i class="bi bi-file-earmark-pdf-fill">Exportar</i>
                        </a>
                        <button class="btn btn-outline-success" data-dismiss="modal" type="button">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
    <br />
    `;

    const removeButton = this.querySelectorAll('button').forEach(i => {
        i.addEventListener('click', () => {
          this.remove();
        });
    });

  }

  capitalize(value) {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

  loadData() {
    const select = this.querySelector('select');
    return fetch(`../films/${this.film}`).then(i=>i.json()).then(i=>{
        this.querySelector("#det_title").textContent = i.title;

            this.querySelector("#det_language").textContent = i.language.name.trim();
            this.querySelector("#det_rating").textContent = i.rating;
            this.querySelector("#det_length").textContent = i.length + " min";

            this.querySelector("#det_category").textContent = i.filmCategories.map(filmCategory => filmCategory.category.name).join(", ");

            this.querySelector("#det_reparto").textContent = i.filmActors.map(filmActor => this.capitalize(filmActor.actor.name)).join(", ");

            this.querySelector("#det_description").textContent = i.description;
            this.querySelector("#det_rentalRate").textContent = "$" + i.rentalRate;

            let img = this.querySelector("#det_poster");
            img.src = `../films/poster/${this.film}`
                    img.onerror = function() {
                        this.src = "https://imagenes.elpais.com/resizer/n48DlQR-crZegLmg09HVeig_Qf0=/414x0/filters:focal(1133x620:1143x630)/cloudfront-eu-central-1.images.arcpublishing.com/prisa/ZZIDTAAXOZEQXPZ2BP3VGO7KIY.jpg";
                    };
    });
  }
}

customElements.define('modal-film', FilmDetailsModal);