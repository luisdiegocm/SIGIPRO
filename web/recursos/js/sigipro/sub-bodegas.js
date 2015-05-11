// Variables globales de tablas
tIngresos = null;
tEgresos = null;
tVer = null;

T_INGRESOS_SELECTOR = "#ingresos-sub-bodegas";
T_EGRESOS_SELECTOR = "#egresos-sub-bodegas";
T_VER_SELECTOR = "#ver-sub-bodegas";

SELECCION_INGRESOS = "#seleccion-usuario-ingreso";
SELECCION_EGRESOS = "#seleccion-usuario-egreso";
SELECCION_VER = "#seleccion-usuario-ver";

validez_form_consumir = false;

$(document).ready(function () {

    $("#subbodegaForm").submit(function () {
        llenarCampoAsociacion('i', T_INGRESOS_SELECTOR, $("#ids-ingresos"));
        llenarCampoAsociacion('e', T_EGRESOS_SELECTOR, $("#ids-egresos"));
        llenarCampoAsociacion('v', T_VER_SELECTOR, $("#ids-ver"));
        $(this).submit();
    });

    eliminarOpciones(T_INGRESOS_SELECTOR, SELECCION_INGRESOS);
    eliminarOpciones(T_EGRESOS_SELECTOR, SELECCION_EGRESOS);
    eliminarOpciones(T_VER_SELECTOR, SELECCION_VER);

    var configuracion = {
        "paging": false,
        "ordering": false,
        "bFilter": false,
        "info": false
    };

    tIngresos = $(T_INGRESOS_SELECTOR).DataTable(configuracion);
    tEgresos = $(T_EGRESOS_SELECTOR).DataTable(configuracion);
    tVer = $(T_VER_SELECTOR).DataTable(configuracion);

    // Ingresar artículos 
    var select_producto = $("#seleccionProducto");
    select_producto.change(function () {
        var opcion = $(this).find(":selected");
        var campoVencimiento = $("#campo-fecha-vencimiento");

        if (opcion.data("perecedero")) {
            campoVencimiento.show();
            campoVencimiento.find('#fechaVencimiento').prop('required', true);
            $("#label-fecha-vencimiento").show();
        } else {
            campoVencimiento.hide();
            campoVencimiento.find('#fechaVencimiento').prop('required', false);
            $("#label-fecha-vencimiento").hide();
        }
    });

    // Consumir artículos
    var select_producto_consumir = $("#seleccion-consumir-producto");
    select_producto_consumir.change(function () {
        var opcion = $(this).find(":selected");
        var campoVencimiento = $("#campo-fecha-vencimiento");

        if (opcion.data("fecha-vecimiento")) {
            campoVencimiento.val(opcion.data("fecha-vecimiento"));
        } else {
            campoVencimiento.val("");
        }
    });

    select_producto.change();

    $('#cantidad').keyup(function () {
        var regex = /^[0-9]+$/;
        if ($(this).val().match(regex)) {
            var cantidad = parseInt($(this).val());
            if (parseInt($('#seleccion-consumir-producto :selected').data('cantidad-disponible')) < cantidad) {
                $('#error-cantidad').text('Debe ser menor que la cantidad de inventario disponible.');
                validez_form_consumir = false;
            } else {
                $('#error-cantidad').text('');
                validez_form_consumir = true;
            }
        } else {
            $('#error-cantidad').text('Debe ser un número');
            validez_form_consumir = false;
        }
    });

    $('#form-consumir-inventario-sub-bodega').submit(function () {
        return validez_form_consumir;
    });
});

// -- Ingresos -- //

function agregarUsuarioIngresos() {
    $(T_INGRESOS_SELECTOR).dataTable().fnClearTable();
    var select = $(SELECCION_INGRESOS);
    var usuariosSeleccionados = select.val();
    for (var i = 0; i < usuariosSeleccionados.length; i++) {
        var elemento = select.find("option[value=" + usuariosSeleccionados[i] + "]");
        
        var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioIngreso(" + elemento.val() + ")>");
        botonEliminar.text("Eliminar");
        
        var nuevaFila = tIngresos.row.add([elemento.text(), botonEliminar[0].outerHTML]).draw().node();
        $(nuevaFila).attr("id", "ingreso-" + elemento.val());
    }
}

function eliminarUsuarioIngreso(id) {
    var fila = tIngresos.row('#ingreso-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();
    
    eliminarDeSelect(id, SELECCION_INGRESOS);
}

// -- Egresos -- //

function agregarUsuarioEgresos() {
    $(T_EGRESOS_SELECTOR).dataTable().fnClearTable();
    var select = $(SELECCION_EGRESOS);
    var usuariosSeleccionados = select.val();
    for (var i = 0; i < usuariosSeleccionados.length; i++) {
        var elemento = select.find("option[value=" + usuariosSeleccionados[i] + "]");
        
        var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioEgreso(" + elemento.val() + ")>");
        botonEliminar.text("Eliminar");
        
        var nuevaFila = tEgresos.row.add([elemento.text(), botonEliminar[0].outerHTML]).draw().node();
        $(nuevaFila).attr("id", "egreso-" + elemento.val());
    }
}

function eliminarUsuarioEgreso(id) {
    var fila = tEgresos.row('#egreso-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    eliminarDeSelect(id, SELECCION_EGRESOS);
}

// -- Ver -- //

function agregarUsuarioVer() {
    $(T_VER_SELECTOR).dataTable().fnClearTable();
    var select = $(SELECCION_VER);
    var usuariosSeleccionados = select.val();
    for (var i = 0; i < usuariosSeleccionados.length; i++) {
        var elemento = select.find("option[value=" + usuariosSeleccionados[i] + "]");
        
        var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioVer(" + elemento.val() + ")>");
        botonEliminar.text("Eliminar");
        
        var nuevaFila = tVer.row.add([elemento.text(), botonEliminar[0].outerHTML]).draw().node();
        $(nuevaFila).attr("id", "ver-" + elemento.val());
    }
}

function eliminarUsuarioVer(id) {
    var fila = tVer.row('#ver-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    eliminarDeSelect(id, SELECCION_VER);
}

// -- Compartido -- //

function llenarCampoAsociacion(string_pivote, tabla, campo_escondido) {
    var asociacionCodificada = "";
    var pivote = "#" + string_pivote + "#";
    $(tabla).find("tr[id]").each(function () {
        var asociacion = pivote + $(this).attr('id').split("-")[1];
        asociacionCodificada += asociacion;
    });
    campo_escondido.val(asociacionCodificada);
}

function eliminarOpciones(selector_tabla, selector_seleccion) {
    var elemento_seleccion = $(selector_seleccion);
    $(selector_tabla).find('tr[id]').each(function () {
        var id = $(this).attr('id').split('-')[1];
        elemento_seleccion.find('option[value=' + id + ']').remove();
    });
}

function eliminarDeSelect(id, selector_select) {
    var select = $(selector_select);
    var valor = select.val();
    var indice = valor.indexOf(id.toString());
    valor.splice(indice, 1);
    select.select2("val", valor);
}