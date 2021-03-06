<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inicio" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-8">${mensaje}</div>
        <div class="col-xs-2"></div>
      </div>
      <div class="row">
        <div class="col-xs-1"></div>
        <div class="col-xs-1"></div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-header">
          <h2>INICIO</h2>
          <em>Recordatorios</em>
        </div>
        <div class="main-content">

        <c:forEach items="${eventos}" var="evento">              

            <div class="col-md-12">
              <div class="widget widget-hide-header widget-reminder">
                <div class="widget-header hide">
                  <h3>Recordatorio</h3>
                </div>
                <div class="widget-content">
                  <div class="today-reminder">
                    <h4 class="reminder-title">${evento.getTitle()}</h4>
                    <p class="reminder-time"><i class="fa fa-clock-o"></i> ${evento.getStart_Date()}</p>
                    <p class="reminder-place"> </p>
                    <em class="reminder-notes"> ${evento.getDescripcion()}</em>
                    <i class="fa fa-bell"></i>

                  </div>
                </div>
              </div>
            </div>

          </c:forEach>
          <!-- Ac� va contenido importante, notificaciones, etc. -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
    <!-- /content-wrapper -->
  </jsp:attribute>

</t:plantilla_general>