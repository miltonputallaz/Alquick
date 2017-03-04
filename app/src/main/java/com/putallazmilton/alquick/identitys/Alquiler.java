package com.putallazmilton.alquick.identitys;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Milton on 24/01/2017.
 */

public class Alquiler implements Serializable{

    private String direccion,propietario,tipo,idface;
    private int ambientes;
    private Long telefono,celular;

    public Alquiler(){

    }


    public Alquiler(String direccion, String tipo, int ambientes, Long telefono, Long celular,String propietario,String idface) {
        this.direccion = direccion;
        this.propietario=propietario;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.telefono = telefono;
        this.celular = celular;
        this.idface=idface;
    }


    public Alquiler(String direccion, String tipo, int ambientes, Long telefono,String propietario,String idface) {
        this.direccion = direccion;
        this.propietario=propietario;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.telefono = telefono;
        this.idface=idface;
    }

    public Alquiler( int ambientes,String direccion, String tipo, Long celular,String propietario,String idface) {
        this.direccion = direccion;
        this.propietario=propietario;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.celular = celular;
        this.idface=idface;
    }

    public String getIdface() {
        return idface;
    }

    public void setIdface(String idface) {
        this.idface = idface;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getTipo() {
        return tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public long getTelefono() {
        return telefono;
    }

    public long getCelular() {
        return celular;
    }
}
