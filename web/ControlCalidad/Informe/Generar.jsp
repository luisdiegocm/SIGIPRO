<%-- 
    Document   : Ver
    Created on : Jul 11, 2015, 12:19:00 PM
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
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?">Solicitudes</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Solicitud?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">${solicitud.getNumero_solicitud()} </a>
                        </li>
                        <li class="active"> ${accion} Informe</li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->

                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Informe de Solicitud ${solicitud.getNumero_solicitud()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Número de Solicitud: </strong></td> <td>${solicitud.getNumero_solicitud()} </td></tr>
                                <tr><td> <strong>Usuario Solicitante: </strong> <td>${solicitud.getUsuario_solicitante().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Fecha de Solicitud: </strong> <td>${solicitud.getFecha_solicitudAsString()} </td></tr>
                                <c:if test="${solicitud.getUsuario_recibido()!=null}">
                                    <tr><td> <strong>Usuario Receptor: </strong> <td>${solicitud.getUsuario_recibido().getNombre_completo()} </td></tr>
                                    <tr><td> <strong>Fecha de Recepción: </strong> <td>${solicitud.getFecha_recibidoAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Estado: </strong> <td>${solicitud.getEstado()} </td></tr>
                                <c:if test="${!solicitud.getObservaciones().equals('')}">
                                    <tr><td> <strong>Observaciones: </strong> <td>${solicitud.getObservaciones()} </td></tr>
                                </c:if>
                            </table>
                            <br>
                            <form class="form-horizontal" method="post" action="Informe" id="form-informe">
                                <input type="hidden" value="${accion}" name="accion" />
                                <input type="hidden" value="${solicitud.getId_solicitud()}" name="id_solicitud" />
                                <c:if test="${accion == 'Editar'}">
                                    <input type="hidden" value="${solicitud.getInforme().getId_informe()}" name="id_informe" />
                                    <c:choose>
                                        <c:when test="${tipo == 'sangria'}">
                                            <c:forEach items="${caballos_resultado}" var="resultado_caballo">
                                                <input type="hidden" name="caballos_res_${resultado_caballo.getResultado().getId_resultado()}" value="${resultado_caballo.pasarIdsAString()}" />
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                </c:if>

                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="objeto-relacionado" class="control-label"> Asociar a otro objeto</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <select id="seleccion-objeto" class="select2" name="objeto-relacionado"
                                                            style='background-color: #fff;'>
                                                        <option value=''></option>
                                                        <option value="sangria">
                                                            Sangría
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>                                
                                <c:choose>
                                    <c:when test="${solicitud.tieneTipoAsociacion()}">
                                        <t:editar_solicitud_sangria derecha="false" />
                                    </c:when>
                                    <c:otherwise>
                                        <div id="fila-select-sangria" class="row" hidden="true">
                                            <div class="col-md-6">
                                                <label for="sangria" class="control-label"> Sangría por asociar</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <select id="seleccion-sangria" name="sangria"
                                                                    style='background-color: #fff;'>
                                                                <option value=''></option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="fila-select-dia" class="row" hidden="true">
                                            <div class="col-md-6">
                                                <label for="sangria" class="control-label"> Día por asignar</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <select id="seleccion-dia" name="dia"
                                                                    style='background-color: #fff;'>
                                                                <option value=''></option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="widget widget-table">
                                            <div class="widget-header">
                                                <h3><i class="fa fa-calendar"></i> Resultados Obtenidos</h3>
                                            </div>

                                            <div class="widget-content">
                                                <table id="resultados-obtenidos" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                    <thead> 
                                                        <tr>
                                                            <th class="columna-escondida">Control</th>
                                                            <th>Identificadores de Muestras (Tipo)</th>
                                                            <th>Análisis Solicitado</th>
                                                            <th>Resultado</th>
                                                            <th>Repetición</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${solicitud.getResultados()}" var="resultado">
                                                            <c:if test="${!solicitud.getInforme().tieneResultado(resultado.getId_resultado())}">
                                                                <tr id="${resultado.getId_resultado()}">
                                                                    <td class="columna-escondida"><input type="checkbox" name="resultados" value="${resultado.getId_resultado()}"></td>
                                                                    <td>
                                                                        <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                                            ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td>${resultado.getAgs().getAnalisis().getNombre()}</td>
                                                                    <td>${resultado.getResultado()}</td>
                                                                    <td>${resultado.getRepeticion()}</td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-primary btn-sm boton-accion reportar-resultado">Reportar Resultado</button>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="widget widget-table">
                                            <div class="widget-header">
                                                <h3><i class="fa fa-calendar"></i> Resultados por Reportar</h3>
                                            </div>
                                            <div class="widget-content">
                                                <table id="resultados-por-reportar" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                                    <!-- Columnas -->
                                                    <thead> 
                                                        <tr>
                                                            <th class="columna-escondida">Control</th>
                                                            <th>ID Muestras (Tipo)</th>
                                                            <th>Análisis Solicitado</th>
                                                            <th>Resultado</th>
                                                            <th>Repetición</th>
                                                            <th>Acción</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:if test="${accion == 'Editar'}">
                                                            <c:forEach items="${solicitud.getInforme().getResultados()}" var="resultado">

                                                                <tr id="${resultado.getId_resultado()}">
                                                                    <td class="columna-escondida"><input type="checkbox" name="resultados" value="${resultado.getId_resultado()}" checked='checked' /></td>
                                                                    <td class="">
                                                                        <c:forEach items="${resultado.getAgs().getGrupo().getGrupos_muestras()}" var="muestra">
                                                                            ${muestra.getIdentificador()} (${muestra.getTipo_muestra().getNombre()})<br>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td>${resultado.getAgs().getAnalisis().getNombre()}</td>
                                                                    <td>${resultado.getResultado()}</td>
                                                                    <td>${resultado.getRepeticion()}</td>
                                                                    <td>
                                                                        <button type="button" class="btn btn-danger btn-sm boton-accion eliminar-resultado">Eliminar de informe</button>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <!-- END WIDGET TICKET TABLE -->
                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <c:choose>
                                            <c:when test= "${accion.equals('Editar')}">
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Informe</button>
                                            </c:otherwise>
                                        </c:choose>    
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>

        <div id="flag-asociacion" data-tipo="${tipo}" />

    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/informes.js"></script>
    </jsp:attribute>

</t:plantilla_general>


<t:modal idModal="modal-asociar-caballo" titulo="Asociar Resultado a Caballo(s)">

    <jsp:attribute name="form">

        <form name="form-agregar-agrupaciones" id="form-asociacion-resultados-caballos" class="form-horizontal">

            <label for="ids-caballos" class="control-label">Seleccione los caballos</label>
            <select id="seleccion-caballos" class="select2" style="background-color: #fff" name="ids_caballos" multiple="multiple" required
                    oninvalid="setCustomValidity('El campo de muestras es requerido.')">
                <c:if test="${tipo == 'sangria'}">
                    <c:forEach items="${caballos_sangria}" var="caballo">
                        <option value="${caballo.getId_caballo()}">${caballo.getNombre()} (${caballo.getNumero()})</option>
                    </c:forEach>
                </c:if>
            </select>

            <br/>

            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="funcion_asociar_resultado_caballos()"><i class="fa fa-check-circle"></i> Asociar Resultados</button>
                </div>
            </div>

        </form>

    </jsp:attribute>

</t:modal>