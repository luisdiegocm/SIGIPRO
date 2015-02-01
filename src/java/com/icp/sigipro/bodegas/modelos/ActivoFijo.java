package com.icp.sigipro.bodegas.modelos;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.TemporalAccessor;

/**
 *
 * @author Walter
 */
public class ActivoFijo {

    int id_activo_fijo;
    int placa;
    int equipo;
    String marca;
    String fecha_movimiento;
    int id_seccion;
    int id_ubicacion;
    String fecha_registro;
    String estado;
    String nombre_seccion;
    String nombre_ubicacion;

    public ActivoFijo() {
    }

    public ActivoFijo(int id_activo_fijo, int placa, int equipo, String marca, String fecha_movimiento, int id_seccion, int id_ubicacion, String fecha_registro, String estado) {
        this.id_activo_fijo = id_activo_fijo;
        this.placa = placa;
        this.equipo = equipo;
        this.marca = marca;
        this.fecha_movimiento = fecha_movimiento;
        this.id_seccion = id_seccion;
        this.id_ubicacion = id_ubicacion;
        this.fecha_registro = fecha_registro;
        this.estado = estado;
    }

  

    public int getId_activo_fijo() {
        return id_activo_fijo;
    }

    public void setId_activo_fijo(int id_activo_fijo) {
        this.id_activo_fijo = id_activo_fijo;
    }

    public int getPlaca() {
        return placa;
    }

    public void setPlaca(int placa) {
        this.placa = placa;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(String fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public int getId_seccion() {
        return id_seccion;
    }

    public void setId_seccion(int id_seccion) {
        this.id_seccion = id_seccion;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_seccion() {
        return nombre_seccion;
    }

    public void setNombre_seccion(String nombre_seccion) {
        this.nombre_seccion = nombre_seccion;
    }

    public String getNombre_ubicacion() {
        return nombre_ubicacion;
    }

    public void setNombre_ubicacion(String nombre_ubicacion) {
        this.nombre_ubicacion = nombre_ubicacion;
    }

}
