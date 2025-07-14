const input = document.querySelector('#buscadorPeliculas');

document.querySelectorAll('.btnDetalle').forEach(item => {
	item.addEventListener('click', function() {
		let url = "../films/";
		fetch(url + this.id).then(i=>i.json()).then(i=>{
			cargarModalDetalle(i);
		});
	});
});

const cargarModalDetalle = (pelicula) => {
	document.getElementById("det_title").textContent = pelicula.title;
	
	document.getElementById("det_language").textContent = pelicula.language.name.trim();
	document.getElementById("det_rating").textContent = pelicula.rating;
	document.getElementById("det_length").textContent = pelicula.length + " min";
	
	var array = [];
	pelicula.filmCategories.forEach(filmCategory => array.push(filmCategory.category.name));
	document.getElementById("det_category").textContent = array.join(", ");
	
	array = [];
	pelicula.filmActors.forEach(filmActor => array.push(primeraLetraMayuscula(filmActor.actor.name)));
	document.getElementById("det_reparto").textContent = array.join(", ")
	
	document.getElementById("det_description").textContent = pelicula.description;
	document.getElementById("det_rentalRate").textContent = "$" + pelicula.rentalRate;

	let img = document.getElementById("det_poster");
	img.src = `../films/poster/${pelicula.filmId}`
    		img.onerror = function() {
                this.src = "https://imagenes.elpais.com/resizer/n48DlQR-crZegLmg09HVeig_Qf0=/414x0/filters:focal(1133x620:1143x630)/cloudfront-eu-central-1.images.arcpublishing.com/prisa/ZZIDTAAXOZEQXPZ2BP3VGO7KIY.jpg";
           };

};

const recargarMosaico = (peliculas) => {
	const divMosaico = document.querySelector('#mosaicoPeliculas');
	divMosaico.innerHTML = "";
	peliculas.data.forEach(pelicula => {
		const divCard = document.createElement("div");
		divCard.className = "card m-2";
		divCard.style = "width: 18rem;"
		
		const img = document.createElement("img")
		img.src = `../films/poster/${pelicula.filmId}`
		img.onerror = function() {
            this.src = "https://imagenes.elpais.com/resizer/n48DlQR-crZegLmg09HVeig_Qf0=/414x0/filters:focal(1133x620:1143x630)/cloudfront-eu-central-1.images.arcpublishing.com/prisa/ZZIDTAAXOZEQXPZ2BP3VGO7KIY.jpg";
        };
		img.classList.add("card-img-top");
		img.classList.add("buscador-poster");
		img.alt = "...";
		
		divCard.appendChild(img);
		
		const divCardBody = document.createElement("div");
		divCardBody.className = "card-body";
		
		const divCardTitle = document.createElement("div");
		divCardTitle.className = "card-title";
		
		const divRowTitle = document.createElement("div");
		divRowTitle.className = "row";
		
		const divColTitle = document.createElement("div");
		divColTitle.classList = "col-12 text-center card-titulo";
		
		const spanTitle = document.createElement("span");
		spanTitle.className = "font-weight-bold";
		spanTitle.textContent = pelicula.title;
		
		divColTitle.appendChild(spanTitle);
		divRowTitle.appendChild(divColTitle);
		
		divCardTitle.appendChild(divRowTitle);
		
		divCardBody.appendChild(divCardTitle);
		divCardBody.appendChild(document.createElement("hr"));
		
		const divCardText = document.createElement("div");
		divCardText.className = "card-text";

		const divRowGenero = document.createElement("div");
		divRowGenero.className = "row";
		
		const divRow4Genero = document.createElement("div");
		divRow4Genero.className = "col-4";
		
		const bGenero = document.createElement("b");
		bGenero.innerHTML = "G&eacute;nero";
		
		divRow4Genero.appendChild(bGenero);
		
		const divRow8Genero = document.createElement("div");
		divRow8Genero.className = "col-8 card-category";
		
		const spanGenero = document.createElement("span");
		spanGenero.className = "float-left";
		spanGenero.textContent = pelicula.category;
		
		divRow8Genero.appendChild(spanGenero);
		
		divRowGenero.appendChild(divRow4Genero);
		divRowGenero.appendChild(divRow8Genero);
		
		divCardText.appendChild(divRowGenero);
		divCardText.appendChild(document.createElement("hr"));

		const divRowReparto = document.createElement("div");
		divRowReparto.className = "row";
		
		const divRow4Reparto = document.createElement("div");
		divRow4Reparto.className = "col-4";
		
		const bReparto = document.createElement("b");
		bReparto.innerHTML = "Reparto";
		
		divRow4Reparto.appendChild(bReparto);
		
		const divRow8Reparto = document.createElement("div");
		divRow8Reparto.className = "col-8 card-reparto";
		
		const spanReparto = document.createElement("span");
		spanReparto.className = "float-left";
		spanReparto.textContent = pelicula.actor;
		
		divRow8Reparto.appendChild(spanReparto);
		
		divRowReparto.appendChild(divRow4Reparto);
		divRowReparto.appendChild(divRow8Reparto);
		
		divCardText.appendChild(divRowReparto);
		divCardText.appendChild(document.createElement("hr"));
		
		const divRowCopias = document.createElement("div");
		divRowCopias.className = "row";
		
		const divRow4Copias1 = document.createElement("div");
		divRow4Copias1.className = "col-4";
		
		const bCopias = document.createElement("b");
		bCopias.innerHTML = "Copias";
		
		divRow4Copias1.appendChild(bCopias);
		divRowCopias.appendChild(divRow4Copias1);
		
		const divRow4Copias2 = document.createElement("div");
		divRow4Copias2.className = "col-4";
		
		const spanCopias = document.createElement("span");
		spanCopias.className = "float-left";
		spanCopias.textContent = pelicula.copies;
		
		divRow4Copias2.appendChild(spanCopias);
		divRowCopias.appendChild(divRow4Copias2);
		
		const divRow4Copias3 = document.createElement("div");
		divRow4Copias3.className = "col-4 text-center";
		
		const buttonCopias = document.createElement("button");
		buttonCopias.type = "button";
		buttonCopias.className = "btn btn-outline-success rounded-circle btnDetalle";
		buttonCopias.id = pelicula.filmId
		buttonCopias.setAttribute("data-toggle", "modal");
		buttonCopias.setAttribute("data-target", "#detalleModal");
		buttonCopias.addEventListener('click', function() {
			let url = "../films/";
			fetch(url + this.id).then(i=>i.json()).then(i=>{
				cargarModalDetalle(i);
			});
		});
		
		const iCopias = document.createElement("i");
		iCopias.className = "bi bi-eye-fill";
		
		buttonCopias.appendChild(iCopias);
		divRow4Copias3.appendChild(buttonCopias);
		divRowCopias.appendChild(divRow4Copias3);
		
		divCardText.appendChild(divRowCopias);
		
		divCardBody.appendChild(divCardText);
		
		divCard.appendChild(divCardBody);
		
		divMosaico.appendChild(divCard);
	});
}

function renderPagination(pageNumber, totalPages) {
  const paginationContainer = $('#paginationContainer');

  // Limpiar el contenedor antes de renderizar la paginación
  paginationContainer.empty();

  paginationContainer.append('<div class="dataTables_paginate paging_simple_numbers" id="paginate"></div>');

  const paginArea =document.getElementById("paginate");

    paginArea.appendChild(paginationLink("Anterior", "0", pageNumber - 1, ["previous"]));

    let pages = generarPaginado(pageNumber+1, totalPages);

    let parentSpan = document.createElement("span");

    for (let i = 0; i < pages.length; i++) {
        if(pages[i] == -1) {
            let dots = document.createElement("span");
            dots.classList.add("ellipsis");
            dots.innerText = "...";
            parentSpan.appendChild(dots);
        } else {
            parentSpan.appendChild(paginationLink(`${pages[i]}`, `${i}`, (pages[i] - 1), ((pages[i] == pageNumber + 1) ? ["current"] : [])));
        }
    }
    paginArea.appendChild(parentSpan);
    paginArea.appendChild(paginationLink("Siguiente", `${pages.length + 2}`, (pageNumber + 1 > totalPages -1 ? -1 : pageNumber + 1), ["next"]));

}

function buscarPeliculas (page = 0) {
	fetch("../films/buscar?" + new URLSearchParams({
	    texto: input.value, page: page,
	})).then(i=>i.json()).then(i=>{
		recargarMosaico(i);
		renderPagination(i.page, i.totalPages);
	})
};

function buscar () {
	buscarPeliculas();
};

input.addEventListener('blur', buscar);

function primeraLetraMayuscula(oracion) {
	const arr = oracion.toLowerCase().split(" ");
	for(var i = 0; i < arr.length; i++) {
		arr[i] = arr[i].charAt(0).toUpperCase() + arr[i].slice(1);
	}
	
	return arr.join(" ");
}

buscarPeliculas();

paginationLink = (text, index, to, css) => {
    const link = document.createElement("a");
    link.classList.add("paginate_button");
    if (css.length > 0)
        link.classList.add(css);
    if (to < 0) {
        link.classList.add("disabled");
    } else {
        link.onclick = function () {
            buscarPeliculas(to);
        };
    }
    link.setAttribute("aria-controls", "tabla");
    link.setAttribute("data-dt-idx", index);
    link.setAttribute("tabindex", "0");
    //link.setAttribute("id", "tabla_previous");
    link.textContent = text;

    return link;
}

function generarPaginado(paginaActual, totalPaginas) {
    const paginado = [];
    const size = Math.min(7, totalPaginas) - 1;

    paginado.push(1);
    if (totalPaginas > 1) {
        paginado[size] = totalPaginas;

        if (paginaActual > 3) {
            paginado[1] = -1;
        }

        if (paginaActual < totalPaginas - 3) {
            paginado[size - 1] = -1;
        }

        if (paginado[1] == -1 && paginado[size - 1] == -1) {
            paginado[2] = paginaActual - 1;
            paginado[3] = paginaActual;
            paginado[4] = paginaActual + 1;
        } else if (!paginado[1]) {
            let i = 1;
            do {
                paginado[i] = paginado[i-1] + 1;
                i++;
            } while (!paginado[i]);
        } else if (!paginado[size - 1]) {
            let i = size - 1;
            do {
                paginado[i] = paginado[i+1] - 1;
                i--;
            } while (!paginado[i]);
        }
    }

    return paginado;
}