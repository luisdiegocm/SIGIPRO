/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.calendario.dao;

import com.google.gson.Gson;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.calendario.modelos.Evento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author Amed
 */
public class EventoDAO {

  private Connection conexion;

  public EventoDAO() {
    SingletonBD s = SingletonBD.getSingletonBD();
  }

  public boolean insertarEvento(Evento evento, Integer id_usuario, Boolean Shared, String sharing_type, String[] ids) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion()
              .prepareStatement("insert into calendario.eventos(title, start_date, end_date, description, allDay) values (?, ?, ?, ?, ?) RETURNING id");
      // Parameters start with 1
      consulta.setString(1, evento.getTitle());
      consulta.setTimestamp(2, evento.getStart_date());
      consulta.setTimestamp(3, evento.getEnd_date());
      consulta.setString(4, evento.getDescription());
      consulta.setBoolean(5, evento.getAllDay());

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
        Integer id_evento = resultadoConsulta.getInt("id");
        insertarEvento_Usuario(id_evento, id_usuario);
        if (Shared) {
          if (sharing_type.equals("Usuarios")) {
            for (int i = 0; i < ids.length; i++) {
              if (Integer.parseInt(ids[i]) != id_usuario) {
                insertarEvento_Usuario(id_evento,
                        Integer.parseInt(ids[i]));
              }
            }
          } else if (sharing_type.equals("Secciones")) {
            for (int i = 0; i < ids.length; i++) {
              insertarEvento_Seccion(id_evento,
                      Integer.parseInt(ids[i]));
            }
          } else if (sharing_type.equals("Roles")) {
            for (int i = 0; i < ids.length; i++) {
              insertarEvento_Rol(id_evento,
                      Integer.parseInt(ids[i]));
            }
          } else {
            insertarEvento_Seccion(id_evento, 0);
          }
        }
      }

      resultadoConsulta.close();
      consulta.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("Ocurrió un error al insertar el evento.");
    }
    return resultado;
  }

  public boolean insertarEvento(Evento evento, Boolean Shared, String sharing_type, String[] ids) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion()
              .prepareStatement("insert into calendario.eventos(title, start_date, end_date, description, allDay) values (?, ?, ?, ?, ?) RETURNING id");
      // Parameters start with 1
      consulta.setString(1, evento.getTitle());
      consulta.setTimestamp(2, evento.getStart_date());
      consulta.setTimestamp(3, evento.getEnd_date());
      consulta.setString(4, evento.getDescription());
      consulta.setBoolean(5, evento.getAllDay());

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
        Integer id_evento = resultadoConsulta.getInt("id");
        if (Shared) {
          if (sharing_type.equals("Usuarios")) {
            for (int i = 0; i < ids.length; i++) {
              insertarEvento_Usuario(id_evento,
                      Integer.parseInt(ids[i]));
            }
          } else if (sharing_type.equals("Secciones")) {
            for (int i = 0; i < ids.length; i++) {
              insertarEvento_Seccion(id_evento,
                      Integer.parseInt(ids[i]));
            }
          } else if (sharing_type.equals("Roles")) {
            for (int i = 0; i < ids.length; i++) {
              insertarEvento_Rol(id_evento,
                      Integer.parseInt(ids[i]));
            }
          } else {
            insertarEvento_Seccion(id_evento, 0);
          }
        }
      }

      resultadoConsulta.close();
      consulta.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("Ocurrió un error al insertar el evento.");
    }
    return resultado;
  }

  public boolean insertarEvento_Usuario(Integer id_e, Integer id_u) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion()
              .prepareStatement("insert into calendario.eventos_usuarios(id_usuario, id_evento) values (?, ?) RETURNING id_evento");
      // Parameters start with 1
      consulta.setInt(1, id_u);
      consulta.setInt(2, id_e);

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }

      resultadoConsulta.close();
      consulta.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("Ocurrió un error al compartir el evento.");
    }
    return resultado;
  }

  public boolean insertarEvento_Seccion(Integer id_e, Integer id_s) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion()
              .prepareStatement("insert into calendario.eventos_secciones(id_seccion, id_evento) values (?, ?) RETURNING id_evento");
      // Parameters start with 1
      consulta.setInt(1, id_s);
      consulta.setInt(2, id_e);

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }

      resultadoConsulta.close();
      consulta.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("Ocurrió un error al compartir el evento.");
    }
    return resultado;
  }

  public boolean insertarEvento_Rol(Integer id_e, Integer id_r) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement consulta = getConexion()
              .prepareStatement("insert into calendario.eventos_roles(id_rol, id_evento) values (?, ?) RETURNING id_evento");
      // Parameters start with 1
      consulta.setInt(1, id_r);
      consulta.setInt(2, id_e);

      ResultSet resultadoConsulta = consulta.executeQuery();
      if (resultadoConsulta.next()) {
        resultado = true;
      }

      resultadoConsulta.close();
      consulta.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("Ocurrió un error al compartir el evento.");
    }
    return resultado;
  }
//    public boolean editarEvento(Evento evento)
//    {
//        boolean resultado = false;
//        try {
//            PreparedStatement preparedStatement = getConexion()
//                    .prepareStatement("update calendario.eventos set nombre_evento=?, telefono1=?, telefono2=?, telefono3=?, correo=?"
//                                      + "where id=?");
//            // Parameters start with 1
//            preparedStatement.setString(1, evento.getTitle());
//            preparedStatement.setString(2, evento.getTelefono1());
//            preparedStatement.setString(3, evento.getTelefono2());
//            preparedStatement.setString(4, evento.getTelefono3());
//            preparedStatement.setString(5, evento.getCorreo());
//            preparedStatement.setInt(6, evento.getId_evento());
//
//            if (preparedStatement.executeUpdate() == 1) {
//                resultado = true;
//            }
//            preparedStatement.close();
//            getConexion().close();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return resultado;
//    }

  public boolean eliminarEvento(int id) throws SIGIPROException {
    boolean resultado = false;
    try {
      PreparedStatement preparedStatement = getConexion()
              .prepareStatement("delete from calendario.eventos where id=?");
      preparedStatement.setInt(1, id);
      if (preparedStatement.executeUpdate() == 1) {
        resultado = true;
      }
      preparedStatement.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("El evento no se pudo eliminar.");
    }
    return resultado;
  }

//    public Evento obtenerEvento(int id)
//    {
//        Evento evento = new Evento();
//        try {
//            PreparedStatement preparedStatement = getConexion().
//                    prepareStatement("select * from calendario.eventos where id=?");
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            if (rs.next()) {
//                evento.setId_evento(rs.getInt("id"));
//                evento.setTitle(rs.getString("nombre_evento"));
//                evento.setTelefono1(rs.getString("telefono1"));
//                evento.setTelefono2(rs.getString("telefono2"));
//                evento.setTelefono3(rs.getString("telefono3"));
//                evento.setCorreo(rs.getString("correo"));
//            }
//            rs.close();
//            preparedStatement.close();
//            getConexion().close();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return evento;
//    }
  public List<Evento> obtenerEventos_hoy(Usuario u) throws SIGIPROException {
    List<Evento> eventos = new ArrayList<Evento>();
    try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) *\n"
                      + "FROM calendario.eventos e\n"
                      + "     FULL OUTER JOIN calendario.eventos_usuarios eu\n"
                      + "        ON e.id = eu.id_evento\n"
                      + "     FULL OUTER JOIN calendario.eventos_secciones es\n"
                      + "        ON e.id = es.id_evento\n"
                      + "WHERE (eu.id_usuario =? or es.id_seccion=?) AND e.start_date >= current_date AND start_date < current_date + 1");
      PreparedStatement preparedStatement2 = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) * FROM calendario.eventos WHERE id IN "
                      + "(SELECT e.id_evento FROM calendario.eventos_roles e where e.id_rol IN "
                      + "(SELECT id_rol FROM seguridad.roles_usuarios WHERE id_usuario=? )) AND start_date >= current_date AND start_date < current_date + 1 ");
      preparedStatement.setInt(1, u.getID());
      preparedStatement.setInt(2, u.getIdSeccion());
      preparedStatement2.setInt(1, u.getID());
      ResultSet rs = preparedStatement.executeQuery();
      ResultSet rs2 = preparedStatement2.executeQuery();

      while (rs.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }
      while (rs2.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }

      rs.close();
      rs2.close();
      preparedStatement.close();
      preparedStatement2.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se puede obtener los eventos.");
    }

    return eventos;
  }

  public List<Evento> obtenerEventos_afterhoy(Usuario u) throws SIGIPROException {
    List<Evento> eventos = new ArrayList<Evento>();
    try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) *\n"
                      + "FROM calendario.eventos e\n"
                      + "     FULL OUTER JOIN calendario.eventos_usuarios eu\n"
                      + "        ON e.id = eu.id_evento\n"
                      + "     FULL OUTER JOIN calendario.eventos_secciones es\n"
                      + "        ON e.id = es.id_evento\n"
                      + "WHERE (eu.id_usuario =? or es.id_seccion=?) AND e.start_date >= now()");
      PreparedStatement preparedStatement2 = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) * FROM calendario.eventos WHERE id IN "
                      + "(SELECT e.id_evento FROM calendario.eventos_roles e where e.id_rol IN "
                      + "(SELECT id_rol FROM seguridad.roles_usuarios WHERE id_usuario=? )) AND start_date >= now() ");
      preparedStatement.setInt(1, u.getID());
      preparedStatement.setInt(2, u.getIdSeccion());
      preparedStatement2.setInt(1, u.getID());
      ResultSet rs = preparedStatement.executeQuery();
      ResultSet rs2 = preparedStatement2.executeQuery();

      while (rs.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }
      while (rs2.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }

      rs.close();
      rs2.close();
      preparedStatement.close();
      preparedStatement2.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se puede obtener los eventos.");
    }

    return eventos;
  }

  public List<Evento> obtenerEventos(Usuario u) throws SIGIPROException {
    List<Evento> eventos = new ArrayList<Evento>();
    try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) *\n"
                      + "FROM calendario.eventos e\n"
                      + "     FULL OUTER JOIN calendario.eventos_usuarios eu\n"
                      + "        ON e.id = eu.id_evento\n"
                      + "     FULL OUTER JOIN calendario.eventos_secciones es\n"
                      + "        ON e.id = es.id_evento\n"
                      + "WHERE eu.id_usuario =? or es.id_seccion=?");
      PreparedStatement preparedStatement2 = getConexion().
              prepareStatement("SELECT DISTINCT ON (id) * FROM calendario.eventos WHERE id IN "
                      + "(SELECT e.id_evento FROM calendario.eventos_roles e where e.id_rol IN "
                      + "(SELECT id_rol FROM seguridad.roles_usuarios WHERE id_usuario=?)) ");
      preparedStatement.setInt(1, u.getID());
      preparedStatement.setInt(2, u.getIdSeccion());
      preparedStatement2.setInt(1, u.getID());
      ResultSet rs = preparedStatement.executeQuery();
      ResultSet rs2 = preparedStatement2.executeQuery();

      while (rs.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }
      while (rs2.next()) {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
        eventos.add(evento);
      }

      rs.close();
      rs2.close();
      preparedStatement.close();
      preparedStatement2.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se puede obtener los eventos.");
    }

    return eventos;
  }
  public Evento obtenerEvento(int id_evento) throws SIGIPROException {
    Evento evento = new Evento();
    try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT * FROM calendario.eventos WHERE id = ?");
      preparedStatement.setInt(1, id_evento);
      ResultSet rs = preparedStatement.executeQuery();

      if (rs.next()) {
        evento.setId(rs.getInt("id"));
        evento.setTitle(rs.getString("title"));
        evento.setStart_date(rs.getTimestamp("start_date"));
        evento.setEnd_date(rs.getTimestamp("end_date"));
        evento.setDescription(rs.getString("description"));
        evento.setAllDay(rs.getBoolean("allDay"));
      }
      rs.close();
      preparedStatement.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se puede obtener el evento.");
    }

    return evento;
  }
  //Este metodo retorna una lista en la forma [[usuario1,usuario2...][seccion1...][rol1..]] cuyos contenidos son los elementos con los que está compartido un evento específico.
  public List<List<String>> obtenerShares(int id_evento) throws SIGIPROException {
    List<List<String>> lista = new ArrayList<>();

    lista.add(obtenerSharesU(id_evento));
    lista.add(obtenerSharesS(id_evento));
    lista.add(obtenerSharesR(id_evento));

    return lista;
  }
  
  public List<String> obtenerSharesU(int id_evento) throws SIGIPROException{
    List<String> lista = new ArrayList<String>();
     try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT u.nombre_usuario "
                      + "FROM calendario.eventos_usuarios eu "
                      + "INNER JOIN seguridad.usuarios u ON eu.id_usuario = u.id_usuario "
                      + "WHERE eu.id_evento = ?");
      preparedStatement.setInt(1, id_evento);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        lista.add(rs.getString("nombre_usuario"));
      }
      rs.close();
      preparedStatement.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se pueden obtener los usuarios.");
    }

    return lista;
  }
  public List<String> obtenerSharesS(int id_evento) throws SIGIPROException{
    List<String> lista = new ArrayList<String>();
     try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT s.nombre_seccion "
                      + "FROM calendario.eventos_secciones es "
                      + "INNER JOIN seguridad.secciones s ON es.id_seccion = s.id_seccion "
                      + "WHERE es.id_evento = ?");
      preparedStatement.setInt(1, id_evento);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        lista.add(rs.getString("nombre_seccion"));
      }
      rs.close();
      preparedStatement.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se pueden obtener los usuarios.");
    }

    return lista;
  }
  public List<String> obtenerSharesR(int id_evento) throws SIGIPROException{
    List<String> lista = new ArrayList<String>();
     try {
      PreparedStatement preparedStatement = getConexion().
              prepareStatement("SELECT r.nombre "
                      + "FROM calendario.eventos_roles er "
                      + "INNER JOIN seguridad.roles r ON er.id_rol = r.id_rol "
                      + "WHERE er.id_evento = ?");
      preparedStatement.setInt(1, id_evento);
      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        lista.add(rs.getString("nombre"));
      }
      rs.close();
      preparedStatement.close();
      getConexion().close();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new SIGIPROException("No se pueden obtener los usuarios.");
    }

    return lista;
  }
  public boolean editarEvento(Evento e)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE calendario.eventos "
                    + " SET title=?, description=?, start_date=?, end_date=?, "
                    + " allday=? "
                    + " WHERE id=?; "
            );
            consulta.setInt(6, e.getId());
            consulta.setString(1, e.getTitle());
            consulta.setString(2, e.getDescription());
            consulta.setTimestamp(3, e.getStart_date());
            consulta.setTimestamp(4, e.getEnd_date());
            consulta.setBoolean(5, e.getAllDay());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            getConexion().close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;

    }
  public String getEventos(Usuario u) throws SIGIPROException {
    List<Evento> eventos = obtenerEventos(u);
    String json = new Gson().toJson(eventos);
    return json;
  }

  private Connection getConexion() {
    SingletonBD s = SingletonBD.getSingletonBD();
    if (conexion == null) {
      conexion = s.conectar();
    } else {
      try {
        if (conexion.isClosed()) {
          conexion = s.conectar();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        conexion = null;
      }
    }
    return conexion;
  }
}
