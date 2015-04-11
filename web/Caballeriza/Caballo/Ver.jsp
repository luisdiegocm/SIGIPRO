<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Caballo?">Caballos</a>
                        </li>
                        <li class="active"> ${caballo.getNumero_microchip()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${caballo.getNumero_microchip()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=evento&id_caballo=${caballo.getId_caballo()}">Eventos Clínicos</a>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=inoculo&id_caballo=${caballo.getId_caballo()}">Inóculos</a>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=sangriap&id_caballo=${caballo.getId_caballo()}">Sangrías de Prueba</a>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=sangria&id_caballo=${caballo.getId_caballo()}">Sangrías</a>
                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 50}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-primary btn-sm boton-accion imagen-Modal" data-id='${caballo.getId_caballo()}' data-toggle="modal" data-target="#modalAgregarImagen">Imagen</a>
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=editar&id_caballo=${caballo.getId_caballo()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre:</strong> <td>${caballo.getNombre()} </td></tr>
                                <tr><td> <strong>Numero de Microchip:</strong> <td>${caballo.getNumero_microchip()} </td></tr>
                                <tr><td> <strong>Grupo del Caballo:</strong> <td>
                                        <c:set var="val" value=""/>
                                        <c:choose> 
                                            <c:when test="${caballo.getGrupo_de_caballos().getNombre() == null}">
                                                No tiene grupo
                                            </c:when>
                                            <c:otherwise>
                                                ${caballo.getGrupo_de_caballos().getNombre()}
                                            </c:otherwise>
                                        </c:choose>
                                    </td></tr>                
                                <tr><td> <strong>Fecha de Nacimiento:</strong> <td>${caballo.getFecha_nacimientoAsString()} </td></tr>
                                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${caballo.getFecha_ingresoAsString()} </td></tr>
                                <tr><td> <strong>Sexo:</strong> <td>${caballo.getSexo()} </td></tr>
                                <tr><td> <strong>Color:</strong> <td>${caballo.getColor()} </td></tr>
                                <tr><td> <strong>Otras Señas:</strong> <td>${caballo.getOtras_sennas()}</td></tr>
                                <tr><td> <strong>Estado:</strong> <td>${caballo.getEstado()}</td></tr>
                                <tr><td> <strong>Imagen:</strong> <td>
                                    <c:if test="${!imagenCaballo.equals('')}">
                                        <img src="${imagenCaballo}" height="200">
                                    </c:if>
                                    </td></tr> 
                            </table>
                        </div>                            
                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalVerEvento" titulo="Ver Observaciones del Evento">
    <jsp:attribute name="form">
        <div class="widget-content">
            <table>
                <tr><td> <strong>Observaciones:</strong><div id ="observacionesModal"></div> <td></td></tr>
            </table>
        </div>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            </div>
        </div>


    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarEvento" titulo="Agregar Eventos">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarEventos" autocomplete="off" method="post" action="Caballeriza">
                <input hidden="true" name="accion" value="Evento">
                <input hidden="true" id='id_serpiente' name='id_caballo' value="">
                <label for="tipo-evento" class="control-label">*Seleccione El Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="eventoModal" class="select2" name="eventoModal"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${listaEventosRestantes}" var="evento">
                                    <option value="${evento.getId_evento()}|${evento.getDescripcion().toString()}"> ID: ${evento.getId_evento()} -- Fecha: ${evento.getFechaAsString()} -- Tipo: ${evento.getTipo_evento().getNombre()}</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">Descripción del Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <BR>
                            <textarea disabled='true' rows="8" cols="50" maxlength="500" placeholder="Descripción Del Evento" class="form-control" name="observaciones" id="observaciones"></textarea>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button id="btn-agregarCaballoEvento" type="button" class="btn btn-primary" onclick="agregarCaballoEvento()><i class="fa fa-check-circle"></i> Agregar Evento</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>
        
<t:modal idModal="modalAgregarImagen" titulo="Agregar Imagen">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarEventos" enctype='multipart/form-data' autocomplete="off" method="post" action="Caballo">
                <input hidden="true" name="accion" value="agregarimagen">
                <input hidden="true" id='id_serpiente_imagen' name='id_caballo_imagen'>
                    <div class="row">
                    <div class="col-md-12">
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-photo"></i> Imagen</h3>
                            </div>
                            <div class="widget-content">
                                <label for="imagen" class="control-label">Seleccione una imagen</label>
                                <div class="form-group">
                                  <div class="col-sm-12">
                                    <div class="input-group">                
                                      <input type="file" name="imagen" accept="image/*" required onchange="previewFile()" />
                                      <div><img name='imagenSubida' id="imagenSubida" src='' height="200" alt=""></div>
                                    </div>
                                  </div>
                                </div>
                                <c:if test="${!imagenCaballo.equals('')}">
                                    Imagen Actual: <img src="${imagenCaballo}" height="100">
                                </c:if>
                                
                            </div>
                        </div>
                    </div>
                </div>            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Imagen</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>