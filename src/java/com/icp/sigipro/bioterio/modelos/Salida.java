/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
/**

/**
 *
 * @author Amed
 */
public class Salida {
  private int id_salida;
  private Boolean especie;
  private Date fecha;
  private int cantidad;
  private String razon;
  private String observaciones;

  public int getId_salida() {
    return id_salida;
  }

  public void setId_salida(int id_salida) {
    this.id_salida = id_salida;
  }

  public Boolean isEspecie() {
    return especie;
  }

  public void setEspecie(Boolean especie) {
    this.especie = especie;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public String getRazon() {
    return razon;
  }

  public void setRazon(String razon) {
    this.razon = razon;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
public String parseJSON(){
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try{
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0){
                    JSON.put(field.getName(), field.get(this));
                }else{
                    JSON.put("id_objeto", field.get(this));
                }
            }          
        }catch (Exception e){
            
        }
        return JSON.toString();
    } 
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
  public String getFecha_S() {
    if (this.fecha != null) {
      return formatearFecha(fecha);
    } else {
      return "";
    }
  }

}
