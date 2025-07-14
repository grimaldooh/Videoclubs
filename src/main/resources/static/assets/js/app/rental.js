$(document).ready(function(){
    rentalController().inicializarTabla()
	$("#fecha_devolucion").datetimepicker({
		format : 'Y-m-d h:i',
		dateonly : true,
		timepicker : false,
		lang : 'es'
	});
})

const rentalController = () => {
    const repositorio =  rentalRepository()
    let table = null;
    const inicializarTabla= ()=>{
        table = $('#tableRental').DataTable({..._tableConfig, 
            ajax: 'rental/pendientes',
            columns: [
                { data: 'rentalId'},
                { data: 'rentalDate'},
                { data: 'inventoryId'},
                { data: 'customerId'},
                { data: 'returnDate'},
                { data: 'staffId'},
            ],
            columnDefs: [
                {
                    targets: 1,
                    data: 'rentalDate',
                    render: function(data, type, {rentalDate}){
                     return	`${rentalDate}`
                    }
                },
                {
                    targets: 2,
                    data: 'inventoryId',
                    render: function(data, type, {inventoryId, tituloPelicula}){
                     return	`${inventoryId} - ${tituloPelicula}`
                    }
                },
                {
                    targets: 3,
                    data: 'customerId',
                    render: function(data, type, {customerId, nombreCliente}){
                     return	`${customerId} - ${nombreCliente}`
                    }
                },
                {
                    targets: 5,
                    data: 'staffId',
                    render: function(data, type, {staffId, nombreStaff}){
                     return	`${staffId} - ${nombreStaff}`
                    }
                },
                {
                    targets: 6,
                    data: null,
                    render: function(data, type, {rentalId, returnDate, }){
                     return	`<div class="btn-group" role="group">
                            <button type="button" class="btn btn-primary consultarRenta accionesRow" rentalId=${rentalId}><i class="fas fa-search"></i></button>
    						<button type="button" class="btn btn-danger realizarDevolucion accionesRow" rentalId="${rentalId}" returnDate="${returnDate}" ${returnDate && 'disabled'} ${_tooltipAttr(`${returnDate ? 'Devoluci&oacute;n ya realizada' : 'Devoluci&oacute;n'}`)}>${_icon('fa-sign-in-alt')}</button>
                            <a target="_blank" href="./rental/export/reciboRenta?rentalId=${rentalId}" class="btn btn-secondary exportarPDF" rentalId="${rentalId}" ${_tooltipAttr('General PDF')}>${_icon('fa-file-pdf')}</a>
                        </div>`
                    }
                },
            ]
        });

        $(document).on("click", "button#agregarRenta", function(e){
            e.preventDefault();
            const btn = $(this)
            btn.prop("disabled", true)
            mostrarModal(btn);
        }).on("click", "button.consultarRenta", function(e){
            e.preventDefault();
            const btn = $(this)
            btn.prop("disabled", true);
            const rentalId = btn.attr("rentalId")
            mostrarModal(btn, Number(rentalId));
        }).on("click", "button.realizarDevolucion", function(e){
            e.preventDefault();
            const btn = $(this)
            btn.prop("disabled", true);
            const rentalId = btn.attr("rentalId")
            mostrarModalDevolucion(btn, Number(rentalId));
        })
    }
    
    let peliculasSeleccionadas = [];
    let peliculasConsultadas = [];
    const mostrarModal = async (btn, rentalId = 0)=>{
        
        const esUnRegistro = rentalId > 0;
        
        const modal = $("div#modalRegistro");
        

//      modal.find("span#title-modal").text("img<></img><input>")
        modal.find("span#title-modal").html("Registrar Renta")
        modal.off("hidden.bs.modal").on("hidden.bs.modal", function(e){
            console.log(btn)
            btn.prop("disabled", false)
        }).off("shown.bs.modal").on("shown.bs.modal", function(){
            _select2({
               id:  "select#customerSearch",
               url: "rental/customerSearch",
               mapResultData: ({data})=>{
                return data.map(
                    ({customerId, name, email })=>
                    ({
                        id: customerId, 
                        text: `${customerId} - ${name} / ${email}` // 10 - Valencia / valencia@jajjaja.com
                    }))
               },
            })

            _select2({
                id:  "select#filmSearch",
                url: "rental/filmSearch",
                mapResultData: ({data})=>{
                    peliculasConsultadas = data;
                    return data.map(
                    ({inventoryId, title, releaseYear, language }) =>
                        ({
                            id: inventoryId, 
                            text: `${inventoryId} - ${title} [${releaseYear}][${language}]`  // 10 - Titanic [1997][ENG]
                        }))
                },
             })
        }).on("click", "button#btnAgregarInventario",function(e){
            e.preventDefault();
            const _btn =  $(this)
            _btn.prop("disabled", true)
            const idInventario =  $("select#filmSearch option:selected").val()
            const film =  peliculasConsultadas.find(({ inventoryId })=> Number(idInventario) === Number(inventoryId))
            
            if(peliculasSeleccionadas.findIndex(({ inventoryId })=> Number(idInventario) === Number(inventoryId)) > -1)
                return notificaAdvertencia("La pelicula ya se encuentra en la lista") && _btn.prop("disabled", false)

            peliculasSeleccionadas = [...peliculasSeleccionadas, film]
            $("ul#listaPeliculasSeleccionadas").append(`<li class="list-group-item d-flex justify-content-between align-items-center">
                ${film.inventoryId} - ${film.title} $ ${film.rentalRate}
                <button class="btn btn-link eliminarInventario" inventoryId="${film.inventoryId}"><i class="fas fa-minus text-danger"></i></button>
            </li>`)
            
            _btn.prop("disabled", false)
            calcularSubtotal()
        }).on("click","button.eliminarInventario", function(e){
            e.preventDefault();
            const _btn =  $(this)
            _btn.prop("disabled", true)
            const idInventario = _btn.attr("inventoryId");
            peliculasSeleccionadas = peliculasSeleccionadas.filter(({ inventoryId })=> Number(idInventario) !== Number(inventoryId))
            _btn.closest('li').remove()
            calcularSubtotal()
        }).on("change", "input#recibido", function(){
            calcularCambio()
        }).on("click", "button#buttonSuccess", async function(e){
            e.preventDefault();
            const _btn =  $(this)
            _btn.prop("disabled", true)

            const customerId = $("select#customerSearch option:selected").val()
            const inventario  = peliculasSeleccionadas
            const recibido = $("input#recibido").val()
            const devolucion = $("input#devolucion").val()
            if(Number(recibido) > 0 && Number(devolucion) >= 0 ){
                const respuesta = await repositorio.httpPost('registrarRentas', {customerId, inventory: JSON.stringify(inventario)})
                if(respuesta?.response){
                    notificaExito("Se registro la renta correctamente")
                    modal.modal("hide")
                    table.rows.add(respuesta.data).draw()
                }else 
                    notificaError("Error al registrar la renta")
                
            }

            _btn.prop("disabled", false)
        })
        
        const inputsHtml = async () => {

            const paraRegistrar = () => `
                ${inputSelect("customerSearch","customerSearch",[],0, "Buscar cliente",false, 
            "Ingrese un No. de cliente o nombre de cliente")}
            <label for="filmSearch">Peliculas</label>
            <div class="mb-5" id="inventarioPeliculas">
                <div class="input-group">
                    ${_select("filmSearch","filmSearch",[])}
                    <div class="input-group-append">
                        <button class="btn btn-outline-success" id="btnAgregarInventario">
                            <i class="fas fa-plus"></i>
                        </button>
                    </div>
                </div>
                <p class="text-muted">Seleccione una pelicula y presione <i class="fas fa-plus"></i> 
                para agregar a la lista</p>
                <ul class="list-group" id="listaPeliculasSeleccionadas"></ul>
            </div>
            
            ${inputText("subtotal", "subtotal", 0, "Subtotal", true, "0.0")}
            ${inputNumber("recibido", "recibido", 0, "Importe Recibido", false,"0.0", 
            "Importe recibido del cliente", "numeric")}
            ${inputText("devolucion", "devolucion", 0, "Cambio", true, "0.0", 
            "Importe a devolver al cliente", "numeric")}
            
            `
            const unRegistro = ({ nombreCliente, nombreStaff, tituloPelicula }) => `
                ${inputText("cliente", "cliente", nombreCliente, "Nombre cliente", true )}
                ${inputText("staff", "staff", nombreStaff, "Nombre Staff", true )}
                ${inputText("pelicula", "pelicula", tituloPelicula, "Titulo Pelicula", true )}
            `

            const registro = esUnRegistro ? await repositorio.httpGet(rentalId) : {};
            //data, response, message
            return esUnRegistro ?  unRegistro(registro.data) : paraRegistrar(); 
        }

        const calcularSubtotal = ()=>{
            const subtotal = peliculasSeleccionadas.reduce((acumulado, {rentalRate})=>{
                return Number(acumulado) + Number(rentalRate);
            }, 0)
            $("input#subtotal").val(parseFloat(subtotal).toFixed(2))
            calcularCambio()
        }

        const calcularCambio = ()=>{
            const recibido =  $("input#recibido").val()
            if(!recibido)
                return;

            const subtotal =  $("input#subtotal").val()
            const cambio = Number(recibido) -  Number(subtotal) ;
            $("input#devolucion").val(parseFloat(cambio).toFixed(2))
            $("input#devolucion").removeClass("border-danger border-success")
            $("input#devolucion").addClass(`${cambio < 0 ? "border-danger" : "border-success"}`)
        }


        modal.find("div.modal-body").html(await inputsHtml())    
        modal.modal({
            backdrop: false,
            keyboard: false,
            show: true
        })
        
        modal.find("button#buttonSuccess")[esUnRegistro?'hide':"show"]()
        modal.find("button#buttonPDF")[esUnRegistro?'show':"hide"]()
    }
    
	const mostrarModalDevolucion = async (btn, rentalId)=>{
		const returnDate = btn.attr("returnDate")
		const esRegistro = returnDate=="null";
		const modal = $('#modalRegistro')
		const rental = await repositorio.httpGet(rentalId)
		const duracion = await repositorio.httpGetReturn('obtener-duracion-renta', {rentalId})
	
		if(!rental)
			return notificaError("Servicio No disponible");
		
		const inputsForm = ({rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate, nombreCliente, tituloPelicula, nombreStaff}) => {
			const inputsRegistro = () => `${inputText('rentalID', 'rentalID', rentalId, 'ID Renta', true)+
			inputText('rentalDate', 'rentalDate', rentalDate, 'Fecha renta', true)+ 
			inputText('inventory', 'inventory', `${inventoryId} - ${tituloPelicula}`, 'ID inventario - Pelicula', true)+ 
			inputText('customer', 'customer', `${customerId} - ${nombreCliente}`, 'Cliente', true)+ 
			inputText('staff', 'staff', `${staffId} - ${nombreStaff}`, 'Empleado', true)+ 
			inputText('lastUpdate', 'lastUpdate', lastUpdate, 'Ultima actualizaci&oacute;n', true)+
			inputTimePicker()+
			inputText('multaGenerada', 'multaGenerada', '', 'Multa generada', true)}`
			 
			const inputsConsulta = () => `${inputText('rentalID', 'rentalID', rentalId, 'ID Renta', true)+
			inputText('rentalDate', 'rentalDate', rentalDate, 'Fecha renta', true)+ 
			inputText('inventory', 'inventory', `${inventoryId} - ${tituloPelicula}`, 'ID inventario - Pelicula', true)+ 
			inputText('customer', 'customer', `${customerId} - ${nombreCliente}`, 'Cliente', true)+ 
			inputText('staff', 'staff', `${staffId} - ${nombreStaff}`, 'Empleado', true)+ 
			inputText('lastUpdate', 'lastUpdate', lastUpdate, 'Ultima actualizaci&oacute;n', true)+ 
			inputText('returnDate', 'returnDate', returnDate, 'Fecha de regreso', true)}`
			
			return esRegistro ? inputsRegistro() : inputsConsulta()
		}
		const calculaMulta = ({rentalId, rentalDate, inventoryId, customerId, returnDate, staffId, lastUpdate, nombreCliente, tituloPelicula, nombreStaff}, fechaDevolucion)=>{
			let fechaInicio = new Date(rentalDate).getTime();
			let fechaFin    = new Date(fechaDevolucion).getTime();
			let dias = Math.round( (fechaFin - fechaInicio)/(1000*60*60*24) );
			let multa = 0
			if(dias>duracion){
				multa = ((dias-duracion) * 0.5);
			}
			
			$("input#multaGenerada").val(parseFloat(multa).toFixed(2));
			$("input#multaGenerada").removeClass("border-success border-danger")
		}

		$(`button#buttonSuccess`)[esRegistro? "show" : "hide"]()
		$(`button#buttonPDF`)[esRegistro ? "hide" : "show"]()

		modal.modal({
			backdrop : 'static',
			keyboard : false,
			show : true
		});

		modal.find(".modal-header span#title-modal").html(`${esRegistro ? `Registrar Devoluci&oacute;n de Renta #${rentalId}` : `Devoluci&oacute;n de Renta #${rentalId}` }`)
		modal.find(".modal-body").html(`<form id="registroDevolucion">${ inputsForm(rental?.data || {}) }</form>`)
		modal.off('hidden.bs.modal').on('hidden.bs.modal', function () {
			enabledButton(btn)	
		}).off("click", "button#buttonSuccess, button#buttonPDF").on("click", "button#buttonSuccess, button#buttonPDF", async function(e){
			if(esRegistro){
				const _btnSuccess = $(this) 
				const datosRenta = rental?.data || {};
				disabledButton(_btnSuccess)
				const returnDate = $("input#fecha_devolucion_modal").val();
				const multaGenerada = $("input#multaGenerada").val();
				if(datosRenta.customerId && datosRenta.inventoryId && returnDate){
					if(Date.parse(returnDate) < Date.parse(datosRenta.rentalDate))
						return notificaAdvertencia("La fecha para la devoluci&oacute;n debe de ser mayor a la fecha de renta.") && enabledButton(_btnSuccess);	
					let customerId = datosRenta.customerId;
					const _r = await repositorio.httpPostReturn('registrar-devolucion', {rentalId, returnDate, multaGenerada, customerId})
					
					enabledButton(_btnSuccess)
					const success = ()=>{
						notificaExito(_r.message)
						modal.modal("hide")
						const row = btn.parents('tr')
						const _lastData = table.row(row).data();
						table.row(row).data({..._lastData, returnDate: "Hace un momento"}).draw(false);
					}
					
					if(_r)
						return _r.response ?  success() : notificaAdvertencia(_r.message);
				}else
					return notificaAdvertencia("Complete los datos para continuar") && enabledButton(_btnSuccess);
			}else	
				return window.open('./rental/export/reciboDevolucion?rentalId='+rentalId, 'name');
		}).off("shown.bs.modal").on('shown.bs.modal', function () {
			
			$("#fecha_devolucion_modal").datetimepicker({
				format : 'Y-m-d h:i',
				dateonly : true,
				timepicker : false,
				lang : 'es'
			});
		}).off("change","input#fecha_devolucion_modal").on("change","input#fecha_devolucion_modal", function(){
			let fechaDevolucion = $(this).val();
			calculaMulta(rental?.data || {}, fechaDevolucion);
		})

		modal.modal("show")
	}

    return { inicializarTabla  }
}

const rentalRepository =  ()=>{ 
    const httpPost = async (url, data)=>{
        try{
            const resp = await $.ajax(
                {
                    url: `rental/${url}`,
                    type: 'post',
                    data,
                    dataType:'JSON'
                }
            );  
            return resp;
        }catch(error){
            notificaError('Ocurrio un error al consultar los datos')
        }
    }

    const httpGet = async (rentalId)=>{
        try{
            const resp = await $.ajax(
                {
                    url: `rental/rentalId`,
                    type: 'get',
                    data:{rentalId},
                    dataType:'JSON'
                }
            );  
            return resp;
        }catch(error){
            notificaError('Ocurrio un error al consultar los datos')
        }
    }
    
	const httpPostReturn = async (url, data) => {
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
	}
	
	const httpGetReturn = async (url, data) => {
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
	}

    return { httpPost, httpGet, httpPostReturn, httpGetReturn }
}