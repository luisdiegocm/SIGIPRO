ARREGLO_PARAMETROS = [];
CONTADOR_PARAM = 1;
DATOS = {};
OPCIONES_OBJETOS = [
    {val: "cat_interno", texto: "Producto Catálogo Interno"}, 
    {val: "secciones", texto: "Sección"},
    {val: "usuarios", texto: "Usuarios"},
    {val: "caballos", texto: "Caballos"},
    {val: "grupos_de_caballos", texto: "Grupos de Caballos"},
    {val: "tipos_eventos", texto: "Eventos Clinicos Caballos"},
    {val: "sangrias_pruebas", texto: "Sangrías de Prueba"}
];

$(document).ready(function () {
    var area_texto = document.getElementById("text-codigo");
    if (area_texto) {
        TEXT_CODIGO = CodeMirror.fromTextArea(area_texto, {
            mode: "text/x-pgsql",
            theme: "neat",
            lineNumbers: true
        });
        TEXT_CODIGO.on("change", function (cm, change) {
            if (change.text[0] === "?") {
                ARREGLO_PARAMETROS.push({ch: change.from.ch, line: change.from.line});
                formarNuevoParametro();
                CONTADOR_PARAM++;
            }
        });
        $("#guardar-reporte").on("click", guardarReporte);
    } else {
        $(".seleccionar-todo").click(function () {
            var elemento = $(this);
            var inputs = elemento.eq(0).parents(".widget-header").siblings(".widget-content").find("input");
            if (elemento.text() === "Marcar Todos") {
                inputs.prop("checked", true);
                elemento.text("Desmarcar todos");
            } else {
                inputs.prop("checked", false);
                elemento.text("Marcar Todos");
            }

        });
    }
});

function guardarReporte(event) {
    event.preventDefault();
    var jsonData = {};

    jsonData["nombre"] = $("#input-nombre").val();
    jsonData["descripcion"] = $("#text-descripcion").val();
    jsonData["codigo"] = TEXT_CODIGO.getValue();
    jsonData["id_seccion"] = $("#seleccion-seccion").val();
    jsonData["url_js"] = $("#input-nombre-js").val();

    $(".fila-param").each(function () {
        $(this).find(":input").each(function () {
            var nombre = $(this).attr("name");
            if (nombre) {
                if (nombre.includes("repite_param")) {
                    if ($(this).is(":checked")) {
                        var valor = $(this).val();
                        jsonData[nombre] = valor;
                    }
                } else {
                    var valor = $(this).val();
                    jsonData[nombre] = valor;
                }
            }
        });
    });

    $.post("/SIGIPRO/Reportes/Reportes?accion=ajaxagregar", jsonData)
            .done(function (data) {
                DATOS = data;
                if (DATOS.message === "Exito") {
                    alert("Reporte guardado con éxito.");
                } else {
                    alert("Reporte no guardado debido a un fallo en la consulta.\nEl mensaje de error es el siguiente: " + DATOS.message);
                }
            });
}

function crearEspacioCampo(texto_label, input, radio1, radio2, input_repeticion) {

    var elemento = crearColumna(4);
    var label = crearLabel(texto_label);
    var form_group = crearDiv("form_group");
    var col12 = crearColumna(12);
    var input_group = crearDiv("input-group");

    input_group.append(input);
    col12.append(input_group);
    form_group.append(col12);
    elemento.append(label);
    elemento.append(form_group);

    if (radio1) {
        col12.append(radio1);
        col12.append(" Repetir Valor");
        col12.append(radio2);
        col12.append(" No repetir");
        col12.append(input_repeticion);
    }

    return elemento;

}

function formarNuevoParametro() {
    var componente_parametros = $("#componente-parametros");

    var fila = crearFila();
    fila.data("num_param", CONTADOR_PARAM);
    fila.addClass("fila-param");

    var columna_parte_nombre = crearParteNombre();
    fila.append(columna_parte_nombre);

    var columna_parte_tipo = crearParteTipo();
    fila.append(columna_parte_tipo);

    componente_parametros.append(fila);
    componente_parametros.find("select").select2();
}

function crearParteTipo() {

    var texto = "Tipo de Dato Parámetro " + CONTADOR_PARAM;
    var opciones = [{val: "fecha", texto: "Fecha"}, {val: "numero", texto: "Número"}, {val: "objeto", texto: "Objeto"}, {val: "objeto_multiple", texto: "Objeto Múltiple"}];

    var input_tipo = crearSelect("tipo_param_" + CONTADOR_PARAM, opciones);

    return crearEspacioCampo(texto, input_tipo);
}

function crearParteNombre() {

    var texto = "Nombre del parámetro " + CONTADOR_PARAM;
    var input_nombre = crearInput("nombre_param_" + CONTADOR_PARAM, "Nombre del parámetro " + CONTADOR_PARAM, "text");
    var radio1 = crearRadio("repite_param_" + CONTADOR_PARAM, "true", false);
    var radio2 = crearRadio("repite_param_" + CONTADOR_PARAM, "false", true);
    var input_repeticiones = crearInput("repeticiones_param_" + CONTADOR_PARAM, "Repeticiones", "text");

    return crearEspacioCampo(texto, input_nombre, radio1, radio2, input_repeticiones);

}

function crearRadio(nombre_param, valor, checked) {
    var radio = crear("input");
    radio.prop("type", "radio");
    radio.prop("name", nombre_param);
    radio.prop("value", valor);
    radio.prop("checked", checked);
    return radio;
}

function crearSelectParcial(name) {
    var select = crear("select");
    select.addClass("select2");
    select.prop("name", name);
    select.prop("style", "background-color: #fff");
    select.prop("required", true);
    select.on("invalid", function () {
        setCustomValidity('Este campo es requerido');
    });
    select.on("input", function () {
        setCustomValidity('');
    });

    var opcion_vacia = crear("option");
    opcion_vacia.val("");
    select.append(opcion_vacia);

    return select;
}

function crearSelect(name, opciones, evento) {

    var select = crearSelectParcial(name);

    var opcion_vacia = crear("option");
    opcion_vacia.val("");
    select.append(opcion_vacia);

    for (i = 0; i < opciones.length; i++) {
        var opcion = crear("option");
        opcion.text(opciones[i].texto);
        opcion.prop("value", opciones[i].val);
        select.append(opcion);
    }

    if(evento === undefined) {
        select.on("change", crearCampoPrueba);
    }


    return select;

}

function crearInput(nombre_param, placeholder, tipo) {
    var input = crear("input");
    input.addClass("form-control");
    input.prop("name", nombre_param);
    input.prop("required", true);
    input.prop("type", tipo);
    input.prop("maxlength", 100);
    input.prop("placeholder", placeholder);
    input.on("invalid", function () {
        this.setCustomValidity('Este campo es requerido');
    });
    input.on("input", function () {
        this.setCustomValidity('');
    });
    return input;
}

function crearDiv(clase) {
    var div = crear("div");
    div.addClass(clase);
    return div;
}

function crearLabel(texto) {
    var label = crear("label");
    label.addClass('control-label');
    label.text(texto);
    return label;
}

function crearColumna(tamano) {
    var col = crearDiv("col-md-" + tamano);
    return col;
}

function crearFila() {
    var fila = crear("div");
    fila.addClass("row");
    return fila;
}

function crear(string) {
    return $(document.createElement(string));
}

crearCampoPrueba = function () {
    var val = $(this).val();
    var fila = $(this).parents(".row.fila-param");
    var num_param = fila.data("num_param");
    
    var fila_por_destruir = fila.find(".col-md-4:eq(2)");
    if (fila_por_destruir.length > 0) {
        fila_por_destruir.remove();
    }
    
    $(this).parent().find("#tipo_param_objeto_" + num_param).remove();

    var nombre_input = "val_param_" + num_param;
    var texto_label = "Valor de prueba parámetro " + num_param;

    if (val === "numero") {
        var input = crearInput(nombre_input, texto_label, "number");
        var input_valor_prueba = crearEspacioCampo(texto_label, input);
        fila.append(input_valor_prueba);
    } else if (val === "fecha") {
        var input = crearInput(nombre_input, texto_label, "text");
        input.prop("pattern", "\d{1,2}/\d{1,2}/\d{4}");
        input.data("date-format", "dd/mm/yyyy");
        var input_valor_prueba = crearEspacioCampo(texto_label, input);
        fila.append(input_valor_prueba);
        input.datepicker().on('changeDate', function () {
            $(this).datepicker('hide');
        });
    } else if (val === "objeto" || val === "objeto_multiple") {
        var opciones = OPCIONES_OBJETOS;
        var div = crear("div");
        div.prop("id", "tipo_param_objeto_" + num_param);
        var select_objeto = crearSelect("tipo_param_objeto_" + num_param, opciones);
        div.append(select_objeto);
        $(this).parent().append(div);
        select_objeto.select2();
        select_objeto.on("change", escogenciaObjeto);
        if (val === "objeto_multiple") {
            select_objeto.data("multiple", true);
        }
    }

};

escogenciaObjeto = function () {

    var val = $(this).val();
    var fila = $(this).parents(".row.fila-param");
    var num_param = fila.data("num_param");

    var nombre_input = "val_param_" + num_param;
    var texto_label = "Valor de prueba parámetro " + num_param;
    
    var fila_por_destruir = fila.find(".col-md-4:eq(2)");
    if (fila_por_destruir.length > 0) {
        fila_por_destruir.remove();
    }
    
    if (val !== "") {
        inicializarSelect(fila, nombre_input, texto_label, $(this).data("multiple"), val);
    }

};

function inicializarSelect(fila, nombre_input, texto_label, multiple, tabla) {

    $.get("/SIGIPRO/Reportes/Reportes?accion=ajaxobjetos&tabla=" + tabla).done(function (data) {
        var select_objeto = crearSelect(nombre_input, data, false);
        var input_valor_prueba = crearEspacioCampo(texto_label, select_objeto);
        fila.append(input_valor_prueba);

        if (multiple) {
            select_objeto.prop("multiple", "multiple");
            select_objeto.select2();
        } else {
            select_objeto.select2();
        }

    });

}