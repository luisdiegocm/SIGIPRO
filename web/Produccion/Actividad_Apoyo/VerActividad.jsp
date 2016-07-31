<%-- 
    Document   : index
    Created on : Jun 29, 2015, 4:39:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producci�n" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producci�n</li>
                        <li>
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo">Categor�as de Actividades de Apoyo</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=indexactividades&id_categoria_aa=${actividad.getCategoria().getId_categoria_aa()}">Actividades de Apoyo</a>
                        </li>
                        <li class="active">
                            Actividades de Apoyo Realizadas de ${actividad.getNombre()}
                        </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Actividades de Apoyo Realizadas de ${actividad.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:choose>
                                    <c:when test="${!actividad.isAprobacion_calidad() && !actividad.isAprobacion_regente() && !actividad.isAprobacion_coordinador()}">
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 672)}">
                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='1' data-toggle="modal" data-target="#modalAprobarActividad">[Calidad] Aprobar</a>
                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Calidad' data-toggle="modal" data-target="#modalRechazarActividad">[Calidad] Rechazar</a>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${actividad.isAprobacion_calidad() && (!actividad.isAprobacion_regente() || !actividad.isAprobacion_coordinador()) && !actividad.isAprobacion_direccion()}">
                                        <c:if test="${!actividad.isAprobacion_regente()}">
                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 673)}">
                                                <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='2' data-toggle="modal" data-target="#modalAprobarActividad">[Regente] Aprobar</a>
                                                <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Regente' data-toggle="modal" data-target="#modalRechazarActividad">[Regente] Rechazar</a>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${!actividad.isAprobacion_coordinador()}">
                                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 674)}">
                                                <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='3' data-toggle="modal" data-target="#modalAprobarActividad">[Coordinador] Aprobar</a>
                                                <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Coordinador' data-toggle="modal" data-target="#modalRechazarActividad">[Coordinador] Rechazar</a>
                                            </c:if>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${actividad.isAprobacion_regente() && actividad.isAprobacion_coordinador() && !actividad.isAprobacion_direccion()}">
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 675)}">
                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='4' data-toggle="modal" data-target="#modalAprobarActividad">[Director] Aprobar</a>
                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Director' data-toggle="modal" data-target="#modalRechazarActividad">[Director] Rechazar</a>
                                        </c:if>
                                    </c:when>
                                    <c:when test="${actividad.isAprobacion_direccion() && !actividad.isAprobacion_gestion()}">
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 680)}">
                                            <a class="btn btn-primary btn-sm boton-accion aprobar-Modal" data-id='${actividad.getId_actividad()}' data-actor='5' data-toggle="modal" data-target="#modalAprobarActividad">[Gesti�n] Aprobar</a>
                                            <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${actividad.getId_actividad()}' data-actor='Gesti�n de Calidad' data-toggle="modal" data-target="#modalRechazarActividad">[Gesti�n] Rechazar</a>
                                        </c:if>
                                    </c:when>                                                            
                                    <c:otherwise>
                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 677)}">
                                            <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=realizar&id_actividad=${actividad.getId_actividad()}">Realizar</a>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 670)}">
                                    <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=ver&id_actividad=${actividad.getId_actividad()}">Ver Actividad de Apoyo</a>            
                                </c:if>
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 681)}">
                                    <c:choose>
                                        <c:when test="${actividad.isEstado()}">
                                            <div class="btn-group widget-header-toolbar">
                                                <a class="btn btn-danger btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=retirar&id_actividad=${actividad.getId_actividad()}">Retirar</a>
                                            </div>
                                        </c:when>                                                            
                                        <c:otherwise>
                                            <div class="btn-group widget-header-toolbar">
                                                <a class="btn btn-danger btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=incluir&id_actividad=${actividad.getId_actividad()}">Incluir</a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Respuesta</th>
                                        <th>Versi�n</th>
                                        <th>Fecha</th>
                                        <th>Usuario Realizar</th>
                                        <th>Acci�n</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaRespuestas}" var="respuesta">

                                        <tr id ="${respuesta.getId_respuesta()}">
                                            <td>
                                                <a href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=verrespuesta&id_respuesta=${respuesta.getId_respuesta()}">
                                                    <div style="height:100%;width:100%">
                                                        ${respuesta.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>
                                                ${respuesta.getVersion()}
                                            </td>
                                            <td>
                                                ${respuesta.getFechaAsString()}
                                            </td>
                                            <td>
                                                ${respuesta.getUsuario_realizar().getNombre_completo()}
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${respuesta.getEstado()==2}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 678)}">
                                                            <a class="btn btn-primary btn-sm boton-accion revisar-Modal" data-id='${respuesta.getId_respuesta()}' data-version="${respuesta.getVersion()}" data-toggle="modal" data-target="#modalRevisarRespuesta">Revisar</a>
                                                        </c:if>
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 677)}">
                                                            <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=repetir&id_respuesta=${respuesta.getId_respuesta()}&id_actividad=${actividad.getId_actividad()}">Repetir</a>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${respuesta.getEstado()==3}">
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 679)}">
                                                            <a class="btn btn-primary btn-sm boton-accion aprobarrespuesta-Modal" data-id='${respuesta.getId_respuesta()}' data-version="${respuesta.getVersion()}" data-toggle="modal" data-target="#modalAprobarRespuesta">Aprobar</a>
                                                        </c:if>
                                                        <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 677)}">
                                                            <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=repetir&id_respuesta=${respuesta.getId_respuesta()}&id_actividad=${actividad.getId_actividad()}">Repetir</a>
                                                        </c:if>
                                                    </c:when>
                                                    <c:when test="${respuesta.getEstado()==4}">
                                                        Aprobada
                                                    </c:when>
                                                </c:choose>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Produccion/ActividadApoyo/ActividadApoyo.js"></script>    
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAprobarActividad" titulo="Aprobar Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-aprobar-actividad">
            <form class="form-horizontal" id="aprobarActividad" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="Aprobar">
                <input hidden="true" id='id_actividad' name='id_actividad' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="label" class="control-label">�Est� seguro que desea aprobar la Actividad de Apoyo?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Actividad</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalRevisarRespuesta" titulo="Revisar Respuesta de Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-revisar-respuesta">
            <form class="form-horizontal" id="revisarActividad" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="revisarrespuesta">
                <input hidden="true" id='id_respuesta' name='id_respuesta' value="">
                <input hidden="true" id='version' name='version' value="">
                <label for="label" class="control-label">�Est� seguro que desea indicar que revis� la Respuesta de la Actividad de Apoyo?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Revisar Respuesta</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAprobarRespuesta" titulo="Aprobar Respuesta de Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-aprobar-respuesta">
            <form class="form-horizontal" id="aprobarRespuesta" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="aprobarrespuesta">
                <input hidden="true" id='id_respuesta' name='id_respuesta' value="">
                <input hidden="true" id='version' name='version' value="">
                <label for="label" class="control-label">�Est� seguro que desea indicar que aprob� la Respuesta de la Actividad de Apoyo?</label>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aprobar Respuesta</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalRechazarActividad" titulo="Rechazar Actividad de Apoyo">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-rechazar-actividad">
            <form class="form-horizontal" id="rechazarActividad" autocomplete="off" method="post" action="Actividad_Apoyo">
                <input hidden="true" name="accion" value="Rechazar">
                <input hidden="true" id='id_actividad' name='id_actividad' value="">
                <input hidden="true" id='actor' name='actor' value="">
                <label for="observaciones" class="control-label">�Razones por las cu�les rechaza la actividad?</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <textarea rows="5" cols="50" maxlength="190" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Rechazar Actividad</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal> 