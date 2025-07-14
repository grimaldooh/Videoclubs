$(document).ready(function () {

	var table = $('#tabla').DataTable({..._tableConfig, 
		processing:true,
		serverSide:true,
        ajax: 'customers/list',
        columnDefs: [
            {
                targets: -1,
                data: null,
                defaultContent: '<button class="btn blue-btn accionesRow"><i class="fa fa-search"></i></button>',
                render: function(data, type, row){
                 return	'<button class="btn blue-btn accionesRow"  idCliente="'+data[0]+'" onClick="consultarCliente('+data[0]+')"><i class="fa fa-search"  ></i></button>'
                	
                	
                }
            },
        ]
	});
	$('#tabla tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
//        detalles(data[3]);
    });
	
	$('#agregarCliente').on('click', function () {
		$('#modal-registrar-cliente').modal(
				{
					backdrop: "static",
					keyboard: false,
					show: true,
				}
		);
    });
	
	$('#selectCountry').select2({
		theme:"bootstrap-5"
	});
	
	$('#selectCity').select2({
		theme:"bootstrap-5"
	});
	
	$('#selectCountry').on("change",function () {
		$('#selectCity').find("option").remove().end().append("");
        $.get("../customers/ciudades/"+ $('#selectCountry').val(),function (data,status){
        	$('#selectCity').select2({
        		theme:"bootstrap-5",
        		data:data,cache:false
        	});
        	
        });
  })
	
	$('#selectCity').on('change', function(){
		$('#idCiudad').val($('#selectCity option:selected').val());
	});		
	$('#selectSucursal').on('change', function(){
		$('#idTienda').val($('#selectSucursal').val());
	});
	$('#botonRegistro').on('click', function(){
//		console.log($('#form-registro-cliente').serialize());
        $.ajax({type:'post', 
        	url:'../customers/register', 
        	data:$('#form-registro-cliente').serialize(), 
        	success:function(data){
//        	console.log(data)
        	if(data==="ok") {
        		$('#modal-registrar-cliente').modal("hide");
        		notificaExito("Se ha registrado el cliente");
        	}
        	
        	}
        });
	});
	
	$("#modal-registrar-cliente").on("hidden.bs.modal", function() {

		 document.getElementById("form-registro-cliente").reset(); 

	});

});

function consultarCliente(idCliente){
	const modal = $("#modal-detalles-customer")
	 $.ajax({type:'get', 
     	url:"../customers/detallesCustomer/"+idCliente, 
     	success:function(data){
     		console.log(data)
     		empleado=data.data.split(",");
     		modal.find(".modal-header span#title-modal").html(`Detalles cliente`)
			document.getElementById("customerName").value = empleado[0]!="null" ? empleado[0] : ""
			document.getElementById("customerLastName").value = empleado[1]!="null" ? empleado[1] : ""
			document.getElementById("customerEmail").value = empleado[2]!="null" ? empleado[2] : ""
			document.getElementById("customerPhone").value = empleado[3]!="null" ? empleado[3] : ""
			document.getElementById("customerCountry").value = empleado[5]!="null" ? empleado[5] : ""
			document.getElementById("customerCity").value = empleado[7]!="null" ? empleado[7] : ""
			document.getElementById("customerAddress").value = empleado[8]!="null" ? empleado[8] : ""
			document.getElementById("customerPostalCode").value = empleado[9] !="null" ? empleado[9] : ""
			document.getElementById("customerStore").value = empleado[10]!="null" ? empleado[10] : ""
			modal.modal("show")
     	}
     });
	
	
	
}


