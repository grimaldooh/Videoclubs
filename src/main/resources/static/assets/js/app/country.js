$(document).ready(function () {
    console.log("hola")
	$('#agregarPais').on('click', function () {
		$('#modal-registrar-pais').modal(
				{
					backdrop: "static",
					keyboard: false,
					show: true,
				}
                
		);
                console.log("agregandoPais")
        
    });

    inicializarTabla()
	
	
	$('#botonRegistroPais').on('click', function(){
        console.log("agregandoPais2")
        

//		console.log($('#form-registro-cliente').serialize());
        $.ajax({type:'post', 
        	url:'../paises/register', 
        	data:$('#form-registro-pais').serialize(), 
        	success:function(data){
        	console.log("REGISTRO")
        	if(data==="ok") {
                console.log("REGISTRO2")
        		$('#modal-registrar-pais').modal("hide");
        		notificaExito("Se ha registrado el cliente");
                location.reload()
        	} else if(data === "duplicado") {
                notificaAdvertencia("Esta duplicado");

            }else{
                notificaAdvertencia("Error al registrar");
            }
        	
        	}
        });
	});
	
	$("#modal-registrar-pais").on("hidden.bs.modal", function() {

		 document.getElementById("form-registro-pais").reset(); 

	});

});

const inicializarTabla= ()=>{
    console.log("table")
     let table = null;
     table = $('#tablePaises').DataTable({..._tableConfig, 
         ajax: 'paises/list',
         
         columns: [
             { data: 'countryId'},
             { data: 'name'},
             { data: 'lastUpdate'},
             { data: null},


             
         ],
         columnDefs: [
             {
                 targets: 0,
                 data: 'countryId',
                 render: function(data, type, {countryId}){
                  return	`${countryId}`
                 }
             },
             {
                 targets: 1,
                 data: 'name',
                 render: function(data, type, {name}){
                  return	`${name} `
                 }
             },
             {
                 targets: 2,
                 data: 'lastUpdate',
                 render: function(data, type, {lastUpdate}){
                  return	`${lastUpdate} `
                 }
             },
            
             {
                 targets: 3,
                 data: null,
                 render: function(data, type, {countryId, name, }){
                  return	`
                         <button type="button" class="btn btn-primary consultarPais accionesRow" countryId=${countryId} onClick="consultarPais(${countryId})" ><i class="fas fa-search"></i></button>
                         
                     </div>`
                 }
             },
         ]
     });

    $(document).on("click", "button#consultarPais", function(e){
         e.preventDefault();
         console.log("Entre")
         const btn = $(this)
         btn.prop("disabled", true)
         mostrarModal(btn);
     });
 }

 function consultarPais(idPais){
	const modal = $("#modal-detalles-pais")
	 $.ajax({type:'get', 
     	url:"../paises/detallesCountry/"+idPais, 
     	success:function(data){
     		console.log(data)
     		pais=data.data;
     		modal.find(".modal-header span#title-modal").html(`Detalles pais`)
			document.getElementById("paisName").value = pais.name!="null" ? pais.name : ""
			
			modal.modal("show")
     	}
     });
    }

 




