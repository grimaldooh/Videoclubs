var tabla = null;
$(document).ready(function() {

    $('#buscarCliente').on("click", function() {
        if($.isNumeric($('#cliente').val())) {
            $('#tabla-container').show();
            if(tabla == null) {
                crearTabla();
            } else {
                tabla.ajax.reload();
            }
        }
    });

    $('#botonCobrar').on("click", function(){
        var pago= parseFloat($('#pago').val());
        var monto= parseFloat($('#amount').val());

        if(pago== "" || pago==0){
            Lobibox.notify("warning",{
                msg: "Ingrese un pago valido",
                icon: false,
                position: 'top right'
            });
        
        }else if(pago<monto){
                Lobibox.notify("warning",{
                    msg: "Ingrese un pago mayor o igual al cobro",
                    icon: false,
                    position: 'top right'
                });
        }
        
        else{
            var cambio = parseFloat(pago-monto);
            if(cambio>0){
                $('#cambio').html("El cambio es de :$"+ cambio.toFixed(2));
            }else{
                $('#cambio').html("");
            }
            $("#modal-confirmar-pago").modal("show");
            $("#title").html("Multa: " + $("#ticket-id").val());
        }
    })

    $("#botonPagar").on("click", function(){
        var ticket =$('#ticket-id').val();
        $.ajax({
            type: 'GET',
            data:{'ticketId':ticket},
            url: "tickets/pagar",

            success: function(data){
                if(data.result){
                    tabla.ajax.reload();
                    $("#modal-confirmar-pago").modal("hide");
                    $("#modal-consulta").modal("hide");

                    var win =window.open($('meta[name=urlBase').attr("content")
                    + 'tickets/export/reciboMulta?ticketId='+ ticket, '_blank');
                    if(win){
                        win.focus();
                    }else{
                        alert('Permite los popups en el sitio');
                    }

                }
                else{
                    Lobibox.notify("warning",{
                        msg: data.msj,
                        icon: false,
                        position: 'top right'
                    });
                }
            }
        })
    })

});



function crearTabla() {
    tabla = $('#tablaMultas').DataTable({..._tableConfig,
        ajax: {
            "type":"GET",
            "url":"tickets/obtener",
            data: function(d) {
                d.customerId = $('#cliente').val();
            }
        },
        columns: [
            {"data":"ticketId"},
            {"data":"rentalId"},
            {"data":"filmTitle"},
            {"data":null},
            {"data":null},
            {"data":null}
        ],
        "columnDefs": [
            {
                "render": function(data, type, json) {
                    return "$" + json.amount;
                },
                "targets":3
            },
            {
                "render": function(data, type, json) {
                    if(json.active) {
                        return '<span class="red-btn" style="pointer-events: none;">Pendiente</span>';
                    } else {
                        return '<span class="green-btn" style="pointer-events: none;">Pagado</span>';
                    }
                },
                "targets":4
            },
            {
                "render": function(data, type, json) {
                    return '<div class="btn-group accionesRow" role="group">'
                    +'<button type ="button" id ="abrirModalConsulta" data-role="view"'
                    +'class ="btn blue-btn" data-original-title="Consultar"'
                    +'data-customer-id="'+ json.customerId + '"'
                    +'data-customer-first-name="' +json.customerFirstName + '"'
                    +'data customer-last-name="' + json.customerLastNanme + '"'
                    +'data-rental-id="' +json.rentalId + '"'
                    +'data-rental-date="' +json.rentalDate + '"'
                    +'data-rental-return="' +json.returnDate + '"'
                    +'data-ticket-id="' +json.ticketId + '"'
                    +'data-amount="' +json.amount + '"'
                    +'data-active="' +json.active + '"'
                    +'data-film="' +json.filmTitle + '"><i class ="fas fa-search" aria-hidden="true"></i></button>'
                    +'<a target ="_blank" '
                    +'href ="./tickets/export/reciboMulta?ticketId='+json.ticketId+ '" '
                    +'class="btn gray-btn exportarPDF" '
                    +'rentalId="' + json.rentalId+'">'
                    +'<i class=" fas fa-file-pdf" aria-hidden="true"></i></a></div>';
                },
                "targets":5
            },
        ],
        "drawCallback" : function(){
            
            var api= this.api();
            api.$("button").off().click(function(e){
                e.preventDefault();
                var data= $(this).data();
                console.log(data)
                switch(data.role){
                    case "view":
                        $('#ticket-id', '#modal-consulta').val(data.ticketId);
                        $('#rental-id', '#modal-consulta').val(data.rentalId);
                        $('#customer-id', '#modal-consulta').val(data.customerId);
                        $('#form_customer', '#modal-consulta').val(data.customerFirstName+ ' '+ data.customerLastName);
                        $('#form_film', '#modal-consulta').val(data.film);
                        $('#form_salida', '#modal-consulta').val(data.rentalDate);
                        $('#form_entrega', '#modal-consulta').val(data.rentalReturn);
                        $('#amount', '#modal-consulta').val(data.amount);
                        $('#monto', '#modal-consulta').val(data.amount);
                        if(data.active){
                            $('#pago-container','#modal-consulta').show();
                            $('#boton-cobrar','#modal-consulta').show();

                        }else{
                            $('#pago-container','#modal-consulta').hide();
                            $('#boton-cobrar','#modal-consulta').hide();
                        }

                        $('#modal-consulta').modal('show');
                        break;
                    default:
                        break;
                }
            });
        }
    });
}