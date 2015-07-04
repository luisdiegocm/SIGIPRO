/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.controlador;


import com.icp.sigipro.calendario.dao.EventoDAO;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.calendario.modelos.Evento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorCalendario", urlPatterns = {"/Calendario/Calendario"})
public class ControladorCalendario extends  SIGIPROServlet {

  private final int[] permisos = {5000, 1, 1};
  private EventoDAO dao = new EventoDAO();
  private UsuarioDAO dao_us = new UsuarioDAO();
  private HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

  protected final Class clase = ControladorCalendario.class;
  protected final List<String> accionesGet = new ArrayList<String>()
  {
    {
      add("index");
      add("agregar");
    }
  };
  protected final List<String> accionesPost = new ArrayList<String>()
  {
    {
      add("agregar");
    }
  };
  protected final List<String> opcionesCompartir = new ArrayList<String>()
  {
    {
      add("Secciones");
      add("Usuarios");
      add("Roles");
      add("Todos");
    }
  };
   // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
  protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String redireccion = "Calendario/Agregar.jsp";
    request.setAttribute("accion", "Agregar");

    redireccionar(request, response, redireccion);
  }

  protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String redireccion = "Calendario/index.jsp";
    HttpSession sesion = request.getSession();
    String nombre_usr = (String) sesion.getAttribute("usuario");
    Usuario us = dao_us.obtenerUsuario(nombre_usr);
    String json = dao.getEventos(us);
    request.setAttribute("eventos", json);
    request.setAttribute("opcionesCompartir", opcionesCompartir);
    redireccionar(request, response, redireccion);
  }

  
  // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {

    String redireccion = "Calendario/Agregar.jsp";
   
    redireccionar(request, response, redireccion);
  }
  
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
   private Evento construirObjeto(HttpServletRequest request) throws SIGIPROException {
    Evento evento = new Evento();
    
    return evento;
  }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
  @Override
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
      List<String> lista_acciones;
      if (accionHTTP.equals("get")){
          lista_acciones = accionesGet; 
      } else {
          lista_acciones = accionesPost;
      }
    if (lista_acciones.contains(accion.toLowerCase())) {
      String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
      Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    }
    else {
      Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
      metodo.invoke(this, request, response);
    }
  }

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // </editor-fold>
}
