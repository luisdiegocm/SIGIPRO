<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Catálogo de Producto Terminado" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Catalogo_PT?">Catálogo de Producto Terminado</a>
            </li>
            <li class="active"> Agregar Producto</li>

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
              <h3><i class="fa fa-list-alt"></i> Agregar Nuevo Producto</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario.jsp"></jsp:include>

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
      <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Formulario.js"></script>
    </jsp:attribute>

</t:plantilla_general>
