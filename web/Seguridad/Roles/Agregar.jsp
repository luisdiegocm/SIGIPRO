<%-- 
    Document   : Agregar
    Created on : Jan 11, 2015, 11:44:07 AM
    Author     : Amed
--%>

<%@page import="com.icp.sigipro.seguridad.dao.RolDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.seguridad.modelos.Rol"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li>
                            <a href="/SIGIPRO/Seguridad/Roles/">Roles</a>
                        </li>
                        <li class="active"> Agregar Rol</li>

                    </ul>
                </div>
                <div class="col-md-8 ">
                    <div class="top-content">

                    </div>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-legal"></i> Agregar Nuevo Rol </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <form id="formAgregarRol" class="form-horizontal" autocomplete="off" role="form" action="Agregar" method="post">
                                <div class="row">
                                    <div class="col-md-6">
                                        <input id="rolesUsuario" hidden="true" name="listarolesUsuario" value="">
                                        <input id="permisosRol" hidden="true" name="listaPermisosRol" value="">
                                        <label for="nombreRol" class="control-label">*Nombre del Rol</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <input type="text" maxlength="45" placeholder="Nombre del Rol" class="form-control" name="nombreRol" required
                                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                                           oninput="setCustomValidity('')" > 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="descripcion" class="control-label">*Descripci�n</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <input type="text" maxlength="200" placeholder="Descripci�n" class="form-control" name="descripcionRol" required
                                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                                           oninput="setCustomValidity('')">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <!-- Esta parte es la de los usuarios de un rol -->
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-group"></i> Usuarios asociados al rol</h3>
                                        <div class="btn-group widget-header-toolbar">
                                            <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarRolUsuario">Agregar</a>
                                        </div>
                                    </div>
                                    <div class="widget-content">
                                        <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                                            <thead>
                                                <tr>
                                                    <th>Nombre Usuario</th>
                                                    <th>Fecha Activaci�n</th>
                                                    <th>Fecha Desactivaci�n</th>
                                                    <th>Editar/Eliminar</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${rolesUsuario}" var="rolUsuario">
                                                    <tr id="${rolUsuario.getID()}">
                                                        <td>${rolUsuario.getNombreUsuario()}</td>
                                                        <td>${rolUsuario.getFechaActivacion()}</td>
                                                        <td>${rolUsuario.getFechaDesactivacion()}</td>
                                                        <td>
                                                            <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarRolUsuario(${rolUsuario.getID()})"   >Editar</button>
                                                            <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarRolUsuario(${rolUsuario.getID()})" >Eliminar</button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!-- Esta parte es la de los suarios del rol -->
                                
                                <!-- Esta parte es la de los permisos de un rol -->
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-check"></i> Permisos asociados al rol</h3>
                                    </div>
                                    <div class="widget-content">
                                        <div class="row">
                                            <c:forEach items="${secciones}" var="seccion" begin="0" end="2">
                                                <div class="col-md-4">
                                                    <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                        <div class="widget-header">
                                                            <h3> ${seccion.getNombre_seccion()}</h3>
                                                            <div class="widget-header-toolbar">
                                                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <c:forEach items="${seccion.getPermisos_seccion()}" var="permiso">
                                                                <div class="col-md-6">
                                                                    <label class="fancy-checkbox">
                                                                        <input type="checkbox" value="${permiso.getID()}" name="permisos">
                                                                        <span>${permiso.getNombrePermiso()}</span>
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="row">
                                            <c:forEach items="${secciones}" var="seccion" begin="3" end="5">
                                                <div class="col-md-4">
                                                    <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                        <div class="widget-header">
                                                            <h3> ${seccion.getNombre_seccion()}</h3>
                                                            <div class="widget-header-toolbar">
                                                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <c:forEach items="${seccion.getPermisos_seccion()}" var="permiso">
                                                                <div class="col-md-6">
                                                                    <label class="fancy-checkbox">
                                                                        <input type="checkbox" value="${permiso.getID()}" name="permisos">
                                                                        <span>${permiso.getNombrePermiso()}</span>
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="row">
                                            <c:forEach items="${secciones}" var="seccion" begin="6" end="8">
                                                <div class="col-md-4">
                                                    <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                        <div class="widget-header">
                                                            <h3> ${seccion.getNombre_seccion()}</h3>
                                                            <div class="widget-header-toolbar">
                                                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <c:forEach items="${seccion.getPermisos_seccion()}" var="permiso">
                                                                <div class="col-md-6">
                                                                    <label class="fancy-checkbox">
                                                                        <input type="checkbox" value="${permiso.getID()}" name="permisos">
                                                                        <span>${permiso.getNombrePermiso()}</span>
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="row">
                                            <c:forEach items="${secciones}" var="seccion" begin="9">
                                                <div class="col-md-4">
                                                    <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                        <div class="widget-header">
                                                            <h3> ${seccion.getNombre_seccion()}</h3>
                                                            <div class="widget-header-toolbar">
                                                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <c:forEach items="${seccion.getPermisos_seccion()}" var="permiso">
                                                                <div class="col-md-6">
                                                                    <label class="fancy-checkbox">
                                                                        <input type="checkbox" value="${permiso.getID()}" name="permisos">
                                                                        <span>${permiso.getNombrePermiso()}</span>
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <!-- Esta parte es la de los permisos de un rol -->

                                <p>
                                    Los campos marcados con * son requeridos.
                                </p>  

                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <button type="button" class="btn btn-primary" onclick="confirmacionAgregarRol()"><i class="fa fa-check-circle"></i> Agregar Rol</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

        <!-- Los modales de Editar Usuarios empiezan ac� -->      
        <t:modal idModal="modalAgregarRolUsuario" titulo="Agregar Usuario">

            <jsp:attribute name="form">

                <form class="form-horizontal" id="formAgregarRolUsuario">
                    <input type="text" name="rol"  hidden="true">
                    <label for="idrol" class="control-label">*Usuario</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group" id='inputGroupSeleccionRol'>
                                <select id="seleccionRol" class="select2" style='background-color: #fff;' name="idrol" required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${usuariosRestantes}" var="rol">
                                        <option value=${rol.getID()}>${rol.getNombreCompleto()} (${rol.getNombreUsuario()}) </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <label for="fechaActivacion" class="control-label">*Fecha de Activaci�n</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group" style="display:table;">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaActivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                    <div title="Fecha de Desactivaci�n: Si desea un rol permanente, introduzca la misma fecha de activaci�n">
                        <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivaci�n</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group" style="display:table;">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                    <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaDesactivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                           onchange="setCustomValidity('')">
                                </div>
                                <p id='mensajeFechasModalAgregar' style='color:red;'><p>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarRol()"><i class="fa fa-check-circle"></i> Agregar Usuario</button>
                        </div>
                    </div>
                </form>

            </jsp:attribute>

        </t:modal>

        <t:modal idModal="modalEditarRolUsuario" titulo="Editar Usuario">

            <jsp:attribute name="form">
                <form class="form-horizontal" id="formEditarRolUsuario">
                    <input type="text" id="idRolUsuarioEditar"     name="idRolEditar"      hidden="true">
                    <input type="text" name="rol"  hidden="true">
                    <label for="fechaActivacion" class="control-label">*Fecha de Activaci�n</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group"  style="display:table;">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaActivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                    <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivaci�n</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group"  style="display:table;">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaDesactivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </div>
                            <p id='mensajeFechasModalEditar' style='color:red;'><p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button id="btn-editarRol" type="button" class="btn btn-primary" onclick="confirmarEdicion()"><i class="fa fa-check-circle"></i> Editar Rol</button>
                        </div>
                    </div>
                </form>

            </jsp:attribute>

        </t:modal>

        <!-- Los modales de Editar Usuarios terminan ac� -->

        <!-- Los modales de Permisos -->      
        <t:modal idModal="modalAgregarPermisoRol" titulo="Agregar Permiso">

            <jsp:attribute name="form">

                <form class="form-horizontal" id="formAgregarPermisoRol">
                    <input type="text" name="rol"  hidden="true">
                    <label for="idpermiso" class="control-label">Seleccione un permiso:</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group" id='inputGroupSeleccionPermiso'>
                                <select id="seleccionPermiso" class="select2" name="idpermiso" required
                                        oninvalid="setCustomValidity('Este campo es requerido ')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${permisosRestantes}" var="rol">
                                        <option value=${rol.getID()}>${rol.getNombrePermiso()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarPermiso()"><i class="fa fa-check-circle"></i> Agregar Permiso</button>
                        </div>
                    </div>
                </form>

            </jsp:attribute>

        </t:modal>

        <t:modal idModal="modalErrorFechaDesactivacion" titulo="Error">

            <jsp:attribute name="form">

                <h5>Las fechas de activaci�n y desactivaci�n deben ser iguales o posteriores a la de hoy. Adem�s, la fecha de desactivaci�n debe ser posterior o igual a la fecha de activaci�n. </h5>

                <div class="modal-footer">
                    <button id="exitErrorFechaDesactivacion" type="button" data-dismiss="modal" class="btn btn-primary" ><i class="fa fa-check-circle"></i> Confirmar</button>
                </div>

            </jsp:attribute>

        </t:modal>
        <!-- Los modales de Editar Roles terminan ac� -->

    </jsp:attribute>

</t:plantilla_general>

