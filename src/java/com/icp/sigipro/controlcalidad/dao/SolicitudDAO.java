/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.dao;

import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.ControlSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class SolicitudDAO extends DAO
{

    public boolean entregarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.solicitudes (numero_solicitud,id_usuario_solicitante,fecha_solicitud,estado) "
                                                      + " VALUES (?,?,?,?) RETURNING id_solicitud");
            consulta.setString(1, solicitud.getNumero_solicitud());
            consulta.setInt(2, solicitud.getUsuario_solicitante().getId_usuario());
            consulta.setDate(3, solicitud.getFecha_solicitud());
            consulta.setString(4, solicitud.getEstado());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public boolean insertarGrupo(Grupo g) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos (id_solicitud) "
                                                      + "VALUES (?) RETURNING id_grupo; ");
            consulta.setInt(1, g.getSolicitud().getId_solicitud());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                g.setId_grupo(rs.getInt("id_grupo"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarMuestra(Muestra m) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.muestras (identificador, id_tipo_muestra, fecha_descarte_estimada) "
                                                      + "VALUES (?,?,?) RETURNING id_muestra; ");
            consulta.setString(1, m.getIdentificador());
            consulta.setInt(2, m.getTipo_muestra().getId_tipo_muestra());
            consulta.setDate(3, m.getFecha_descarte_estimada());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                m.setId_muestra(rs.getInt("id_muestra"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarMuestrasGrupo(Grupo g) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            this.insertarGrupo(g);

            PreparedStatement consultaBatch = getConexion().prepareStatement(" INSERT INTO control_calidad.grupos_muestras (id_grupo, id_muestra) "
                                                                             + "VALUES (?,?)");

            for (Muestra muestra : g.getGrupos_muestras()) {
                this.insertarMuestra(muestra);
                consultaBatch.setInt(1, g.getId_grupo());
                consultaBatch.setInt(2, muestra.getId_muestra());
                consultaBatch.addBatch();
            }
            consultaBatch.executeBatch();
            resultado = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarAnalisisGrupoSolicitud(AnalisisGrupoSolicitud ags) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO control_calidad.analisis_grupo_solicitud (id_grupo, id_analisis) "
                                                      + "VALUES (?,?) RETURNING id_analisis_grupo_solicitud");
            consulta.setInt(1, ags.getGrupo().getId_grupo());
            consulta.setInt(2, ags.getAnalisis().getId_analisis());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                ags.setId_analisis_grupo_solicitud(rs.getInt("id_analisis_grupo_solicitud"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    //PENDIENTE
    public boolean editarSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                                                      + "SET id_usuario_recibido=?, fecha_recibido=?, estado=? "
                                                      + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, solicitud.getUsuario_recibido().getId_usuario());
            consulta.setDate(2, solicitud.getFecha_recibido());
            consulta.setString(3, solicitud.getEstado());
            consulta.setInt(4, solicitud.getId_solicitud());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean recibirSolicitud(SolicitudCC solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                                                      + "SET id_usuario_recibido=?, fecha_recibido=?, estado='Pendiente' "
                                                      + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, solicitud.getUsuario_recibido().getId_usuario());
            consulta.setDate(2, solicitud.getFecha_recibido());
            consulta.setInt(3, solicitud.getId_solicitud());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<SolicitudCC> obtenerSolicitudes() {
        List<SolicitudCC> resultado = new ArrayList<SolicitudCC>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT s.id_solicitud, s.numero_solicitud, s.fecha_solicitud, s.id_usuario_solicitante, u.nombre_completo, s.estado "
                                                      + "FROM control_calidad.solicitudes as s INNER JOIN seguridad.usuarios as u ON u.id_usuario = s.id_usuario_solicitante ; ");
            rs = consulta.executeQuery();
            while (rs.next()) {
                SolicitudCC solicitud = new SolicitudCC();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setNumero_solicitud(rs.getString("numero_solicitud"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario_solicitante"));
                usuario.setNombre_completo(rs.getString("nombre_completo"));
                solicitud.setUsuario_solicitante(usuario);
                solicitud.setEstado(rs.getString("estado"));
                resultado.add(solicitud);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public SolicitudCC obtenerSolicitud(int id_solicitud) {
        SolicitudCC resultado = new SolicitudCC();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT solicitud.id_solicitud, solicitud.numero_solicitud, solicitud.id_usuario_solicitante, solicitud.id_usuario_recibido, solicitud.fecha_solicitud, solicitud.fecha_recibido, solicitud.estado "
                                                      + "FROM control_calidad.solicitudes as solicitud  "
                                                      + "WHERE id_solicitud=?; ");
            consulta.setInt(1, id_solicitud);
            rs = consulta.executeQuery();
            UsuarioDAO usuariodao = new UsuarioDAO();

            if (rs.next()) {
                resultado.setId_solicitud(rs.getInt("id_solicitud"));
                resultado.setEstado(rs.getString("estado"));
                Usuario usuario_solicitante = usuariodao.obtenerUsuario(rs.getInt("id_usuario_solicitante"));
                Usuario usuario_recibido = usuariodao.obtenerUsuario(rs.getInt("id_usuario_recibido"));
                resultado.setUsuario_recibido(usuario_recibido);
                resultado.setUsuario_solicitante(usuario_solicitante);
                resultado.setFecha_recibido(rs.getDate("fecha_recibido"));
                resultado.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                resultado.setNumero_solicitud(rs.getString("numero_solicitud"));
            }

            consulta = getConexion().prepareStatement(
                    " SELECT ags.id_analisis_grupo_solicitud, a.id_analisis, a.nombre as nombreanalisis, g.id_grupo, m.id_muestra, m.identificador, tm.nombre as nombretipo, tm.id_tipo_muestra "
                    + " FROM control_calidad.analisis_grupo_solicitud as ags "
                    + "   LEFT OUTER JOIN control_calidad.grupos as g ON g.id_grupo = ags.id_grupo "
                    + "   LEFT OUTER JOIN control_calidad.grupos_muestras as gm ON gm.id_grupo = g.id_grupo "
                    + "   LEFT OUTER JOIN control_calidad.muestras as m ON m.id_muestra = gm.id_muestra "
                    + "   LEFT OUTER JOIN control_calidad.tipos_muestras as tm ON tm.id_tipo_muestra = m.id_tipo_muestra "
                    + "   LEFT OUTER JOIN control_calidad.analisis as a ON a.id_analisis = ags.id_analisis "
                    + " WHERE g.id_solicitud = ?"
                    + " ORDER BY g.id_grupo;");

            consulta.setInt(1, id_solicitud);
            rs = consulta.executeQuery();

            List<AnalisisGrupoSolicitud> lista_grupos_analisis_solicitud = new ArrayList<AnalisisGrupoSolicitud>();
            ControlSolicitud cs = new ControlSolicitud();

            AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
            int id_ags;

            while (rs.next()) {

                id_ags = rs.getInt("id_analisis_grupo_solicitud");
                
                if (id_ags != ags.getId_analisis_grupo_solicitud()) {
                    ags = new AnalisisGrupoSolicitud();
                    ags.setId_analisis_grupo_solicitud(id_ags);

                    Analisis a = new Analisis();
                    a.setId_analisis(rs.getInt("id_analisis"));
                    a.setNombre(rs.getString("nombreanalisis"));

                    Grupo g = new Grupo();
                    g.setId_grupo(rs.getInt("id_grupo"));
                    g.setSolicitud(resultado);

                    ags.setGrupo(g);
                    ags.setAnalisis(a);
                    
                    lista_grupos_analisis_solicitud.add(ags);
                }

                Muestra m = new Muestra();
                m.setId_muestra(rs.getInt("id_muestra"));
                m.setIdentificador(rs.getString("identificador"));

                TipoMuestra tm = new TipoMuestra();
                tm.setNombre(rs.getString("nombretipo"));
                tm.setId_tipo_muestra(rs.getInt("id_tipo_muestra"));

                m.setTipo_muestra(tm);

                ags.getGrupo().agregarMuestra(m);
                ags.setId_analisis_grupo_solicitud(id_ags);
                
                cs.agregarCombinacion(ags.getAnalisis(), tm, m);
            }

            resultado.setAnalisis_solicitud(lista_grupos_analisis_solicitud);
            resultado.setControl_solicitud(cs);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean anularSolicitud(int id_solicitud) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement(" UPDATE control_calidad.solicitudes "
                                                      + "SET estado='Anulada' "
                                                      + "WHERE id_solicitud = ?; ");
            consulta.setInt(1, id_solicitud);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean insertarGrupoConAnalisis(Grupo grupo) throws SIGIPROException {
        
        boolean resultado = false;
        
        PreparedStatement consulta_grupo = null;
        ResultSet rs_grupo = null;
        
        PreparedStatement consulta_muestras_grupo = null;
        PreparedStatement consulta_ags = null;
        
        try {
            getConexion().setAutoCommit(false);
            
            consulta_grupo = getConexion().prepareStatement(
                " INSERT INTO control_calidad.grupos (id_solicitud) VALUES (?) RETURNING id_grupo"
            );
            
            consulta_grupo.setInt(1, grupo.getSolicitud().getId_solicitud());
            
            rs_grupo = consulta_grupo.executeQuery();
            
            if (rs_grupo.next()) {
                grupo.setId_grupo(rs_grupo.getInt("id_grupo"));
            }
            
            consulta_muestras_grupo = getConexion().prepareStatement(
                " INSERT INTO control_calidad.grupos_muestras (id_muestra, id_grupo) VALUES (?,?);"
            );
            
            for(Muestra m : grupo.getGrupos_muestras()) {
                consulta_muestras_grupo.setInt(1, m.getId_muestra());
                consulta_muestras_grupo.setInt(2, grupo.getId_grupo());
                consulta_muestras_grupo.addBatch();
            }
            
            consulta_muestras_grupo.executeBatch();
            
            consulta_ags = getConexion().prepareStatement(
                " INSERT INTO control_calidad.analisis_grupo_solicitud (id_analisis, id_grupo) VALUES (?, ?); "
            );
            
            for(Analisis a : grupo.getAnalisis()) {
                consulta_ags.setInt(1, a.getId_analisis());
                consulta_ags.setInt(2, grupo.getId_grupo());
                consulta_ags.addBatch();
            }
            
            consulta_ags.executeBatch();
            
            resultado = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al ingresar el grupo. Inténtelo nuevamente.");
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Notifique al administrador del sistema.");
            }
            cerrarSilencioso(rs_grupo);
            cerrarSilencioso(consulta_grupo);
            cerrarSilencioso(consulta_muestras_grupo);
            cerrarSilencioso(consulta_ags);
            cerrarConexion();
        }
        
        return resultado;
    }
}
