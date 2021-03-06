<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : jespinozac95
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-cotizacion" method="post" action="Cotizacion">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_cotizacion" value="${cotizacion.getId_cotizacion()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Cotizacion?">Cotizaciones</a>
            </li>
            <li class="active">Cotización ${cotizacion.getId_cotizacion()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-list-alt"></i> Cotización ${cotizacion.getId_cotizacion()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Ventas/Cotizacion?accion=archivo&id_cotizacion=${cotizacion.getId_cotizacion()}">Descargar Cotización</a>
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/Cotizacion?accion=editar&id_cotizacion=${cotizacion.getId_cotizacion()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar esta cotización? Las órdenes de compra asociadas se eliminarán. ¿Desea continuar" data-form-id="form-eliminar-cotizacion">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>ID: </strong></td> <center> <td> ${cotizacion.getId_cotizacion()} </td> </center> </tr>
                <tr><td> <strong>Identificador: </strong>  </td> <center> <td> ${cotizacion.getIdentificador()}   </td> </center> </tr>
                <tr><td> <strong>ID Intención: </strong>  </td> <center> 
                    <td> 
                        <a href="/SIGIPRO/Ventas/IntencionVenta?accion=ver&id_intencion=${cotizacion.getIntencion().getId_intencion()}">
                        <div style="height:100%;width:100%">
                            ${cotizacion.getIntencion().getId_intencion()}
                        </div>
                        </a>
                    </td> 
                </center> </tr>
                <tr><td> <strong>Moneda: </strong>  </td> <center> <td> ${cotizacion.getMoneda()}   </td> </center> </tr>
                <tr><td> <strong>Flete: </strong>  </td> <center> 
                    <c:choose>
                            <c:when test="${cotizacion.getMoneda() == 'Colones'}">
                              <td>&#8353;${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Dólares'}">
                              <td>$${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Euros'}">
                              <td>&euro;${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${String.format("%,.2f", cotizacion.getFlete().doubleValue())}</td>
                            </c:otherwise>
                        </c:choose>
                </center> </tr>
                <tr><td> <strong>Total: </strong>  </td> <center> 
                        <c:choose>
                            <c:when test="${cotizacion.getMoneda() == 'Colones'}">
                              <td>&#8353;${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Dólares'}">
                              <td>$${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:when test="${cotizacion.getMoneda() == 'Euros'}">
                              <td>&euro;${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${String.format("%,.2f", cotizacion.getTotal().doubleValue())}</td>
                            </c:otherwise>
                        </c:choose>
                      </center> </tr>
              </table>
              <br>
              
            </div>     
          <!-- END WIDGET TICKET TABLE -->
          <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Productos Asociados</h3>
                    <div class="btn-group widget-header-toolbar">
                      
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Producto</th>
                          <th>Cantidad</th>
                          <th>Precio Unitario</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_cotizacion}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getPrecio()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
  </jsp:attribute>
      

</t:plantilla_general>
