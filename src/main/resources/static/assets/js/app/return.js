var tableReturn;
const btn = document.querySelector('#buscarCliente');
const input = document.querySelector('#inputBusqueda');
const inputCheck = document.querySelector('#consultarDevos');
const buscarPeliculas = () => {
	let url = "return/cargar-tabla?";
	if(input.value !== ""){
		fetch(url + new URLSearchParams({
		    parametroBusqueda: input.value,
		    consulta: inputCheck.checked
		}))
		.then(i=>i.json())
		.then(i=>{
			tableReturn.clear().draw();
	        tableReturn = $('#tablaPeliculasAgregadas').DataTable();
	        tableReturn.rows.add(i).draw();
		})
	}else{
		notificaAdvertencia("Debe indicar el ID del cliente para continuar.");
	}
}
btn.addEventListener('click', buscarPeliculas);

$(document).ready(function () {
	const repository = rentalRepository();
	$("#devolucion").on('click',function (e) {
		$("#modalReturn").modal("show");
	});
	
	tableReturn = $('#tablaPeliculasAgregadas').DataTable({
		columns: [
			{data: 'rentalId'},
			{data: 'rentalDate'},
			{data: 'inventoryId'},
			{data: 'customerId'},
			{data: 'returnDate'},
			{data: 'staffId'}
		],
		columnDefs: [
			{
				targets: 1,
				data: 'rentalDate',
				render: function (data, type, {rentalDate, lastUpdate}) {
					return `${rentalDate}${lastUpdate && rentalDate !== lastUpdate ? ` ${_tooltipInfo(`Actualizado el ${lastUpdate}`)}`:""}`
				}},
			{
				targets: 2,
				data: 'inventoryId',
				render: function (data, type, {inventoryId, tituloPelicula}) {
					return `${inventoryId} - ${tituloPelicula}`
				}},
			{
				targets: 3,
				data: 'customerId',
				render: function (data, type, {customerId, nombreCliente}) {
					return `${customerId} - ${nombreCliente}`
				}},
			{
				targets: 5,
				data: 'staffId',
				render: function (data, type, {staffId, nombreStaff}) {
					return `${staffId} - ${nombreStaff}`
				}},
			{	
				targets: 6,
				data: null,
				render: function (data, type, row) {
					return `<div class="btn-group accionesRow" role="group"">`+
//					`<button type="button" action="consulta" class="btn btn-primary accionesRow" rentalId="${row.rentalId}">${_icon('fa-search')}</button>`+
//					`<button type="button" action="devolucion" class="btn red-btn accionesRow" rentalId="${row.rentalId}" returnDate="${row.returnDate}" ${_tooltipAttr('Devoluci&oacute;n')}>${_icon('fa-sign-in-alt')}</button>`+
					`<a target="_blank" href="rental/export/reciboRenta?rentalId=${row.rentalId}" class="btn btn-secondary exportarPDF" rentalId="${row.rentalId}">${_icon('fa-file-pdf')}</a>`+
				`</div>`}},
		],
	});
 	$('#tablaPeliculasAgregadas tbody').on('click', 'tr', function () {
		if(!inputCheck.checked){
			$(this).toggleClass('selected');
			$("#fecha_devolucion").val("");
			$("input#multaGeneradaMasivo").val("");
		}
    });
	$("body").on("change", "#fecha_devolucion", async function(e){
		var table = $('#tablaPeliculasAgregadas').DataTable();
		let returnDate = $(this).val();
		let registros = table.rows('.selected').data().toArray();
		if(registros.length>0){
			if(returnDate){
				let fechaFin = new Date(returnDate).getTime();
	  	      	var multa = 0.00;
				$.each(registros, async function(index, renta) {
					const diasDuracion = await repository.duracion(renta.rentalId) || {};
					let fechaInicio = new Date(renta.rentalDate).getTime();
					let dias = Math.round( (fechaFin - fechaInicio)/(1000*60*60*24) );
					if(dias>diasDuracion){
						multa = multa+((dias-diasDuracion) * 0.5);
						
					}
					$("input#multaGeneradaMasivo").val(parseFloat(multa).toFixed(2));
				});
			}else{
				notificaAdvertencia("Debe seleccionar una fecha para su devoluci&oacute;n.")
			}
  		}else{
			notificaAdvertencia("Debe seleccionar una pelicula para su devoluci&oacute;n.")
			$(this).val("");
		}
    });
	$("input#consultarDevos").on("change", function(e){
		let consulta = $(this).prop("checked");
		if(consulta){
			$("#fecha_devolucion").hide();
			$("#lbl_fecha_devo").hide();
			$("#multaGeneradaMasivo").hide();
			$("#lbl_multas").hide();
			$("#guardarDevolucionMasiva").hide();
		}else{
			$("#fecha_devolucion").show();
			$("#lbl_fecha_devo").show();
			$("#multaGeneradaMasivo").show();
			$("#lbl_multas").show();
			$("#guardarDevolucionMasiva").show();
		}
	});

	$("body").on("click", "#tablaPeliculasAgregadas button.accionesRow", function(e){
		e.preventDefault();
		$(this).prop("disabled", true)
		const action = $(this).attr("action")
		const rentalId = $(this).attr("rentalId")
//		accionesTabla[action]?.(rentalId, $(this))
	})
	$("body").on("click", "#guardarDevolucionMasiva", async function(e){
		const _btnSuccess = $(this) 
		let costumerId = $("#inputBusqueda").val();
		let multaGenerada = $("#multaGeneradaMasivo").val();
		let returnDate = $("#fecha_devolucion").val();
		var table = $('#tablaPeliculasAgregadas').DataTable();
		let registros = table.rows('.selected').data().toArray();
		if(fecha_devolucion && returnDate && registros.length>0){
					let jso = JSON.stringify(registros.map(({rentalId})=>({rentalId})));
			const _r = await repository.registrarMasivo(jso, returnDate, multaGenerada, costumerId);
			enabledButton(_btnSuccess)
			const success = ()=>{
				notificaExito(_r.message)
				$("#modalReturn").modal("hide");
				tableReturn.clear().draw();
				$("#inputBusqueda").val("");
				$("#fecha_devolucion").val("");
				$("input#multaGeneradaMasivo").val("");
			}
			if(_r)
				return _r.response ?  success() : notificaAdvertencia(_r.message);
		}else{
			notificaAdvertencia("Complete los datos para continuar");
		}
	})

	
	const accionesTabla =  {
//		consulta:mostrarModal,
//		devolucion:mostrarModalDevolucion

	}
});
const rentalRepository = ()=>{

	const duracion = async (rentalId) => {
		return await _getRequestReturn(`obtener-duracion-renta`, {rentalId})
	}
	
	const registrarDevolucion = async (rentalId, returnDate, multaGenerada, customerId) => {
		return await _postRequestReturn(`registrar-devolucion`, {rentalId, returnDate, multaGenerada, customerId})
	}
	const registrarMasivo = async (rental, returnDate, multaGenerada, customerId) => {
		return await _postRequestReturn(`registrar-devolucion-seleccionadas`, {rental, returnDate, multaGenerada, customerId})
	}
	
	const _postRequestReturn = async (url, data) => {
		try {
			const resp = await $.ajax({
				url:`return/${url}`,
				type: 'POST',
				data,
				dataType:'JSON'
			});
			return resp;
		} catch (err) {
			return notificaError("Servicio no disponible");
		}
	};
	
	const _getRequestReturn = async (url, data) => {
		try {
			const resp = await $.ajax({
				url:`return/${url}`,
				type: 'GET',
				data,
				dataType:'JSON'
			});
			return resp;
		} catch (err) {
			return notificaError("Servicio no disponible");
		}
	};
	
	return { duracion, registrarDevolucion, registrarMasivo}
}