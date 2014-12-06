/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.basededatos;

import com.icp.sigipro.clases.BarraFuncionalidad;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class SingletonBD 
{    
    private static SingletonBD theSingleton = null;
    
    protected SingletonBD(){  }
    
    public static SingletonBD getSingletonBD()
    {
        if (theSingleton == null)
        {
            theSingleton = new SingletonBD();
        }
        return theSingleton;
    }
    
    private Connection conectar()
    {
        Connection conexion = null;
        
        try
        {
            Class.forName("org.postgresql.Driver");
            conexion = 
            DriverManager.getConnection(
                "jdbc:postgresql://localhost/sigipro","postgres","Akr^&Oma92"
            );
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("Clase no encontrada");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return conexion;
        
    }
    
    public boolean validarInicioSesion(String usuario, String contrasenna)
    {
        Connection conexion = conectar();
        boolean resultado = false;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta = conexion.prepareStatement("SELECT 1 "
                                                                     + "FROM seguridad.usuarios us "
                                                                     + "WHERE us.nombreusuario = ? and us.contrasena = ? "
                                                                     + "AND us.fechaactivacion <= current_date "
                                                                     + "AND (us.fechadesactivacion > current_date or us.fechaactivacion = us.fechadesactivacion) "
                                                                     + "AND us.estado = true ");
                consulta.setString(1, usuario);
                String hash = md5(contrasenna);
                consulta.setString(2, hash);
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = resultadoConsulta.next(); //Se verifica si hay resultado de la consulta.
                resultadoConsulta.close();
                consulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = false;
            }
        }
        return resultado;
    }
    
    public List<BarraFuncionalidad> obtenerModulos(String usuario)
    {
        Connection conexion = conectar();
        List<BarraFuncionalidad> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("SELECT modulo,funcionalidad "
                                                   + "FROM usuarios.funcionalidad_usuario "
                                                   + "WHERE id_usuario = ? "
                                                   + "order by modulo asc");
                consulta.setString(1, usuario);
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarBarraFuncionalidad(resultadoConsulta);
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
    
    public boolean insertarUsuario(String nombreUsuario, String nombreCompleto, String correoElectronico,
                                    String cedula, String departamento, String puesto, String fechaActivacion, String fechaDesactivacion)
    {
        boolean resultado = false;
        
        try
        {
            Connection conexion = conectar();
            
            if(conexion != null)
            {
                PreparedStatement consulta = conexion.prepareStatement("INSERT INTO SEGURIDAD.usuarios "
                        + " (nombreusuario, contrasena, correo, nombrecompleto, cedula, departamento, puesto, fechaactivacion, fechadesactivacion, estado) "
                        + " VALUES "
                        + " (?,?,?,?,?,?,?,?,?,TRUE )");
                consulta.setString(1, nombreUsuario);
                consulta.setString(2, md5("hola"));
                consulta.setString(3, nombreCompleto);
                consulta.setString(4, correoElectronico);
                consulta.setString(5, cedula);
                consulta.setString(6, departamento);
                consulta.setString(7, puesto);
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fActivacion = formatoFecha.parse(fechaActivacion);
                java.util.Date fDesactivacion = formatoFecha.parse(fechaDesactivacion);
                java.sql.Date fActivacionSQL = new java.sql.Date(fActivacion.getTime());
                java.sql.Date fDesactivacionSQL = new java.sql.Date(fDesactivacion.getTime());
                
                consulta.setDate(8, fActivacionSQL);
                consulta.setDate(9, fDesactivacionSQL);
                
                int resultadoConsulta = consulta.executeUpdate();
                if (resultadoConsulta == 1)
                {
                    resultado = true;
                }
                consulta.close();
                conexion.close();
            }
        }
        catch(SQLException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        catch(ParseException ex)
        {
            String hola = "hola";
            hola.toCharArray();
        }
        
        return resultado;
    }
    
    @SuppressWarnings("Convert2Diamond")
    private List<BarraFuncionalidad> llenarBarraFuncionalidad(ResultSet resultadoConsulta) throws SQLException
    {
        List<BarraFuncionalidad> resultado = new ArrayList<BarraFuncionalidad>();
        BarraFuncionalidad temp = new BarraFuncionalidad();
        String modulo = null;
        
        while(resultadoConsulta.next())
        {
            if (!resultadoConsulta.getString("modulo").equals(modulo))
            {
                modulo = resultadoConsulta.getString("modulo");
                temp = new BarraFuncionalidad(resultadoConsulta.getString("modulo"));
                resultado.add(temp);
            }
            temp.agregarFuncionalidad(resultadoConsulta.getString("funcionalidad"));
        }
        return resultado;
    }
    
    private String md5(String texto)
    {
        String resultado = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(texto.getBytes());
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            resultado = bigInt.toString(16);
            
            while(resultado.length() < 32 ){
              resultado = "0"+resultado;
            }
        }
        catch(NoSuchAlgorithmException ex)
        {
            //Imprimir error
        }
        
        return resultado;
    }
}
