const  _tableConfig = {
    language: {
        processing: "Tratamiento en curso...",
        search: "Buscar&nbsp;:",
        lengthMenu: "Mostrar _MENU_ registros",
        info: "Mostrando del registro _START_ al _END_ de un total de _TOTAL_ registros",
        infoEmpty: "No existen registros.",
        infoFiltered: "(filtrado de _MAX_ elementos en total)",
        infoPostFix: "",
        loadingRecords: "Cargando...",
        zeroRecords: "No se encontraron datos con tu busqueda",
        emptyTable: "No hay registros disponibles en la tabla.",
        paginate: {
            first: "Primero",
            previous: "Anterior",
            next: "Siguiente",
            last: "&Uacute;ltimo"
        },
        aria: {
            sortAscending: ": active para ordenar la columna en orden ascendente",
            sortDescending: ": active para ordenar la columna en orden descendente"
        }
    },

}

const _icon = (icon)=> `<i class="fas ${icon}"></i>`
const _tooltipAttr = (title)=>` aria-hidden="true" data-toggle="tooltip" title="${title}" `
const _tooltipInfo = (title)=>`<i class="fa fa-exclamation-circle text-primary" ${_tooltipAttr(title) }></i>`

const notificaError = (msg)=>Lobibox.notify("error", {msg})
const notificaExito = (msg)=>Lobibox.notify("success", {msg})
const notificaAdvertencia = (msg)=>Lobibox.notify("warning", {msg})
const notificaAviso = (msg)=>Lobibox.notify("info", {msg})

const inputText = (name, id, value, label, disabled, placeholder, help, cls = '')=> `<div class="form-group">
${label ? `<label for="${id || name}">${label}</label>` : ''}
<input type="text" class="form-control ${cls}" id="${id || name}" ${value ? `value="${value}"` : ``} ${placeholder ? `placeholder="${placeholder}"` : ``} ${disabled ? `disabled` : ``} >
${help ? `<small id="${id || name}Help" class="form-text text-muted">${help}</small>` : ''}
</div>`

const inputNumber = (name, id, value, label, disabled, placeholder, help, cls = '')=> `<div class="form-group">
${label ? `<label for="${id || name}">${label}</label>` : ''}
<input type="number" class="form-control ${cls}" id="${id || name}" ${value ? `value="${value}"` : ``} ${placeholder ? `placeholder="${placeholder}"` : ``} ${disabled ? `disabled` : ``} >
${help ? `<small id="${id || name}Help" class="form-text text-muted">${help}</small>` : ''}
</div>`

const _select = (name, id, options, value, disabled) =>`
<select class="form-control" id="${id || name}" ${disabled ? `disabled` : ``} >
    ${options.reduce((anterior, actual)=>{
        return `${anterior}<option value="${actual.value}" ${actual.value == value && 'selected'}>${actual.label}</option>`
    },``)}
</select>`

const inputSelect = (name, id, options = [], value, label, disabled, help)=> `<div class="form-group">
${label ? `<label for="${id || name}">${label}</label>` : ''}
<select class="form-control" id="${id || name}" ${disabled ? `disabled` : ``} >
    ${options.reduce((anterior, actual)=>{
        return `${anterior}<option value="${actual.value}" ${actual.value == value && 'selected'}>${actual.label}</option>`
    },``)}
</select>
${help ? `<small id="${id || name}Help" class="form-text text-muted">${help}</small>` : ''}
</div>`

const inputTimePicker = ()=> `<div class="form-group">
<label for="fecha_devolucion_modal">Fecha devoluci&oacute;on</label>
<input type='text' class="form-control" id='fecha_devolucion_modal' placeholder="aaaa-mm-dd"  aria-describedby="fecha_devolucion_modal" />
</div>`

const inputHidden = (name, id, value)=> `<input type="hidden" id="${id || name}" ${value ? `value="${value}"` : ``}  >`

const _select2 = ({id, url, mapResultData = null, onChange = ()=>null}) => $(`${id}`).select2({
    theme: 'bootstrap-5',
    language: "es",
    ajax: {
        url: `${url}`,
        delay: 250,
        minimumInputLength: 1,
        data: function (params) {
            return {search: `${params.term || ""}`.trim()};
        },
        processResults: function (data) {
            return { results: mapResultData?.(data) || data};
        }
    }
}).on("change", function(e){
    onChange?.($(this).val())
});

const disabledButton = (btn) => btn?.["prop"]?.("disabled", true)
const enabledButton = (btn) => btn?.["prop"]?.("disabled", false)
