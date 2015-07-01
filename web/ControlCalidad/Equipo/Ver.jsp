<%-- 
    Document   : Ver
    Created on : Jun 30, 2015, 8:58:03 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Equipo?">Equipos</a>
                        </li>
                        <li class="active"> ${equipo.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> ${equipo.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:set var="contienePermisoEliminar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 520}">
                                        <c:set var="contienePermisoEliminar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEliminar}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el tipo de reactivo" data-href="/SIGIPRO/ControlCalidad/Equipo?accion=eliminar&id_equipo=${equipo.getId_equipo()}">Eliminar</a>
                                </c:if>

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 520}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/ControlCalidad/Equipo?accion=editar&id_equipo=${equipo.getId_equipo()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del Equipo: </strong></td> <td>${equipo.getNombre()} </td></tr>
                                <tr><td> <strong>Tipo de Equipo: </strong></td> <td>${equipo.getTipo_equipo().getNombre()} </td></tr>
                                <tr><td> <strong>Descripción: </strong> <td>${equipo.getDescripcion()} </td></tr>
                            </table>
                            <br>
                        </div>
                        <div class="col-md-12">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-download"></i> Certificados</h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:set var="contienePermisoEditar" value="false" />
                                        <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                            <c:if test="${permiso == 1 || permiso == 520}">
                                                <c:set var="contienePermisoEditar" value="true" />
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${contienePermisoEditar}">
                                            <a class="btn btn-primary btn-sm boton-accion certificado-Modal" data-id='${equipo.getId_equipo()}' data-toggle="modal" data-target="#modalCertificado">Agregar Certificado</a>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Fecha del Certificado</th>
                                                <th>Certificado</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${certificados}" var="certificado">
                                                <tr id ="${certificado.getId_certificado_equipo()}"> 
                                                    <td>
                                                        ${certificado.getFecha_certificado()}
                                                    </td>
                                                    <td>
                                                        <a href="/SIGIPRO/ControlCalidad/Equipo?accion=certificado&id_certificado_equipo=${certificado.getId_certificado_equipo()}&nombre=${equipo.getNombre()}">Descargar Certificado</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
        <script src="/SIGIPRO/recursos/js/sigipro/Equipo.js"></script>
    </jsp:attribute>
</t:plantilla_general>


<t:modal idModal="modalCertificado" titulo="Agregar Certificado">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" enctype='multipart/form-data' id="agregarCertificado" autocomplete="off" method="post" action="Equipo">
                <input hidden="true" name="accion" value="Certificado">
                <input hidden="true" id='id_equipo_certificado' name='id_equipo_certificado'>
                <%--
                <label for="fecha" class="control-label">*Fecha de Certificado</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerCertificado" class="form-control sigiproDatePicker" name="fecha_certificado" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                   onchange="setCustomValidity('')">
                        </div>
                    </div>
                </div> --%>
                <label for="observaciones" class="control-label">*Certificado</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="file" id="certificado" name="certificado"  accept="application/pdf,image/jpeg,image/gif,image/png" required
                                   oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                   onchange="setCustomValidity('')"/>
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Certificado</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>
