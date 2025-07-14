$(document).ready(function () {

	var table = $('#tabla').DataTable({..._tableConfig,
        ajax: 'films/list',
        columnDefs: [
            {
                targets: -1,
                data: null,
                defaultContent: '<button class="btn blue-btn accionesRow"><i class="fa fa-search"></i></button>',
            },
        ]
	});
	$('#tabla tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        detalles(data[3]);
    });
});


function detalles(filmId) {
	$.get(`${location.origin}/films/${filmId}`, function (data, status) {
		console.log(data);
		console.log(status); 

		let categorias1 = "";

		$("#detalleNumPelicula").html(data.filmId);
		$("#detalleTitulo").html(data.title);
		$("#detallePrecio").html("$" + data.rentalRate);
		$("#detalleLanzamiento").html(data.releaseYear);
		$("#detalleDuracion").html(data.length + " min");

		data.filmCategories.forEach(c => categorias1 += c.category.name + ", ");
		categorias1 = categorias1.slice(0, categorias1.length - 2)
		$("#detalleCategorias").html(categorias1);
		$("#detalleLenguaje").html(data.language.name.trim());
		$("#detalleClasificacion").html(data.rating);
		$("#detalleDescripcion").html(data.description);

		document.getElementById('poster').src = `../films/poster/${data.filmId}`

		let lista = document.getElementById("detalleActores");
		lista.innerHTML = "";

		for (let i = 0; i < data.filmActors.length; i++) {
			let li = document.createElement("li");
			li.innerHTML = data.filmActors[i].actor.name;
			lista.appendChild(li);
		}

        let sf = document.getElementById("specialfeatures");
        sf.innerHTML = "";

        for (let i = 0; i < data.specialFeatures.length; i++) {
            let li = document.createElement("li");
            li.innerHTML = data.specialFeatures[i];
            sf.appendChild(li);
        }

		$("#modalDetalles").modal("show");

	});
}

$("#botonExportarPDF").on("click", function () {
	let filmId = $("#detalleNumPelicula").html();

	var win = window.open($('meta[name=urlBase]').attr("content") + "../films/pdf/" + filmId, '_blank');
	if (win) {
		//Browser has allowed it to be opened
		win.focus();
	} else {
		//Browser has blocked it
		alert('Permita las ventanas emergentes para este sitio web.');
	}

});