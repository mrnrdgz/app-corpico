package ar.com.corpico.appcorpico.orders.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 22/05/2017.
 */

public class Tipo_Cuadrilla {
    private String tipo_cuadrilla;
    private String servicio;

    public Tipo_Cuadrilla(String tipo_cuadrilla, String servicio) {
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.servicio = servicio;
    }
    public Tipo_Cuadrilla() {

    }
    public String getTipo_cuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipo_cuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

}
