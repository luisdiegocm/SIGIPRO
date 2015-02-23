/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.InventarioDAO;
import com.icp.sigipro.bodegas.dao.PrestamoDAO;
import com.icp.sigipro.bodegas.dao.SolicitudDAO;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.Prestamo;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorPrestamos", urlPatterns = {"/Bodegas/Prestamos"})
public class ControladorPrestamos extends SIGIPROServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      String redireccion = "";
      String accion = request.getParameter("accion");
      PrestamoDAO dao = new PrestamoDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {24, 25, 1};
      int usuario_solicitante;
      UsuarioDAO usrDAO = new UsuarioDAO();
      boolean boolAdmin = false;
      if (verificarPermiso(25, listaPermisos)) {
        usuario_solicitante = 0;
        boolAdmin = true;
      } else {
        String nombre_usr = (String) sesion.getAttribute("usuario");
        usuario_solicitante = usrDAO.obtenerIDUsuario(nombre_usr);
      }

      if (accion != null) {
        validarPermisos(permisos, listaPermisos);
        if (accion.equalsIgnoreCase("ver")) {
          redireccion = "Prestamos/Ver.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Prestamo prestamo = dao.obtenerPrestamo(id_solicitud);
          request.setAttribute("prestamo", prestamo);
        } else if (accion.equalsIgnoreCase("agregar")) {
          redireccion = "Prestamos/Agregar.jsp";
          Prestamo prestamo = new Prestamo();
          InventarioDAO inventarioDAO = new InventarioDAO();
          Usuario usr = usrDAO.obtenerUsuario(usrDAO.obtenerIDUsuario((String) sesion.getAttribute("usuario")));
          List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usr.getIdSeccion(),0);
          request.setAttribute("inventarios", inventarios);
          request.setAttribute("prestamo", prestamo);
          request.setAttribute("accion", "Agregar");
        } else if (accion.equalsIgnoreCase("eliminar")) {
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          dao.eliminarPrestamo(id_solicitud);
          redireccion = "Prestamos/index.jsp";
          List<Prestamo> prestamos = dao.obtenerPrestamos(usuario_solicitante);
          request.setAttribute("listaPrestamos", prestamos);
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud de préstamo eliminada correctamente"));

        } else if (accion.equalsIgnoreCase("editar")) {
          redireccion = "Prestamos/Editar.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          Prestamo prestamo = dao.obtenerPrestamo(id_solicitud);
          InventarioDAO inventarioDAO = new InventarioDAO();
          Usuario usr = usrDAO.obtenerUsuario(usrDAO.obtenerIDUsuario((String) sesion.getAttribute("usuario")));
          List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usr.getIdSeccion(),0);
          request.setAttribute("inventarios", inventarios);
          request.setAttribute("prestamo", prestamo);
          request.setAttribute("accion", "Editar");
        } else if (accion.equalsIgnoreCase("reponer")) {
          redireccion = "Prestamos/index.jsp";
          int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
          SolicitudDAO soldao = new SolicitudDAO();
          Solicitud prestamo = soldao.obtenerSolicitud(id_solicitud);
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          prestamo.setEstado("Prestamo Repuesto");
          boolean resultado;
          resultado = soldao.editarSolicitud(prestamo);
          if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Préstamo marcado como 'Repuesto'"));
          } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
          }
          List<Prestamo> prestamos = dao.obtenerPrestamos(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaPrestamos", prestamos);
        } else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "Prestamos/index.jsp";
          List<Prestamo> prestamos = dao.obtenerPrestamos(usuario_solicitante);
          request.setAttribute("booladmin", boolAdmin);
          request.setAttribute("listaPrestamos", prestamos);
        }
      } else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "Prestamos/index.jsp";
        List<Prestamo> prestamos = dao.obtenerPrestamos(usuario_solicitante);
        request.setAttribute("booladmin", boolAdmin);
        request.setAttribute("listaPrestamos", prestamos);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    } catch (AuthenticationException ex) {
      RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
      vista.forward(request, response);
    }
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String redireccion;
    SolicitudDAO soldao = new SolicitudDAO();
    PrestamoDAO dao = new PrestamoDAO();
    UsuarioDAO usrDAO = new UsuarioDAO();
    InventarioDAO invDAO = new InventarioDAO();
    boolean boolAdmin = false;
    int usuario_solicitante;
    HttpSession sesion = request.getSession();
    List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
    if (verificarPermiso(25, listaPermisos)) {
      boolAdmin = true;
      usuario_solicitante = 0;
    } else {
      String nombre_usr = (String) sesion.getAttribute("usuario");
      usuario_solicitante = usrDAO.obtenerIDUsuario(nombre_usr);
    }
    request.setCharacterEncoding("UTF-8");
    boolean resultado = false;
    boolean resultado_prestamo = false;
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    Solicitud solicitud = new Solicitud();
    Prestamo prestamo = new Prestamo();
    Integer id_inventario;
    try {
      id_inventario = Integer.parseInt(request.getParameter("seleccioninventario"));
    } catch (java.lang.NumberFormatException e) {
      id_inventario = 0;
    }
    Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
    String estado = request.getParameter("estado");
    String fch_sol = request.getParameter("fecha_solicitud");

    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    java.util.Date fecha_solicitud;
    java.sql.Date fecha_solicitudSQL;
    try {
      fecha_solicitud = formatoFecha.parse(fch_sol);
      fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
      solicitud.setFecha_solicitud(fecha_solicitudSQL);
    } catch (ParseException ex) {
      Logger.getLogger(ControladorSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
    }

    solicitud.setId_inventario(id_inventario);
    solicitud.setCantidad(cantidad);
    solicitud.setEstado(estado);
    
    Inventario inv = invDAO.obtenerInventario(id_inventario);
    prestamo.setId_seccion_presta(inv.getId_seccion());

    String id = request.getParameter("id_solicitud");

    if (id.isEmpty() || id.equals("0")) {
      java.util.Date hoy = new java.util.Date();
      Date hoysql = new Date(hoy.getTime());
      String nombre_usr = (String) sesion.getAttribute("usuario");
      int usuario_solicitante2 = usrDAO.obtenerIDUsuario(nombre_usr);
      solicitud.setId_usuario(usuario_solicitante2);
      solicitud.setFecha_solicitud(hoysql);
      solicitud.setEstado("Pendiente Prestamo");
      resultado = soldao.insertarSolicitud_Prestamo(solicitud, prestamo);
      redireccion = "Prestamos/Agregar.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud de préstamo ingresada correctamente"));
    } else {
      Integer id_us = Integer.parseInt(request.getParameter("id_usuario"));
      solicitud.setId_usuario(id_us);
      solicitud.setId_solicitud(Integer.parseInt(id));
      prestamo.setId_solicitud(Integer.parseInt(id));
      resultado = soldao.editarSolicitud(solicitud);
      redireccion = "Prestamos/Editar.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud de préstamo editada correctamente"));
    }

    if (resultado) {
      redireccion = "Prestamos/index.jsp";
      request.setAttribute("booladmin", boolAdmin);
      List<Prestamo> prestamos = dao.obtenerPrestamos(usuario_solicitante);
      request.setAttribute("listaPrestamos", prestamos);
    } else {
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }

    request.setAttribute("solicitud", solicitud);
    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);

  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

  protected int getPermiso() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
