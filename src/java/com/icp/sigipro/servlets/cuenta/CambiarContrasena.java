/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.cuenta;

import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "CambiarContrasena", urlPatterns = {"/Cuenta/CambiarContrasena"})
public class CambiarContrasena extends HttpServlet
{

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
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet CambiarContrasena</title>");      
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet CambiarContrasena at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
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
          throws ServletException, IOException
  {
    processRequest(request, response);
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
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();
    request.setCharacterEncoding("UTF-8");
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    try {
      String nombreUsuario = request.getParameter("usuarioCaducado");
      String contrasenna = request.getParameter("contrasenna");

      UsuarioDAO u = new UsuarioDAO();
      boolean cambio = u.cambiarContrasena(nombreUsuario, contrasenna);

      if (cambio) 
      {
        request.setAttribute("mensaje",helper.mensajeDeExito("Su contraseña ha sido restablecida. Inicie sesión con la nueva contraseña."));
        request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
      }
      else {
        request.setAttribute("mensaje", helper.mensajeDeError("No se pudo restablecer la contraseña."));
        request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
      }
      
      //request.getRequestDispatcher("/Cuenta/IniciarSesion.jsp").forward(request, response);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    finally {
      out.close();
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

}
