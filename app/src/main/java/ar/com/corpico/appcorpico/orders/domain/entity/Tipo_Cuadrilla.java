package ar.com.corpico.appcorpico.orders.domain.entity;

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

    public String getTipoCuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipoCuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
