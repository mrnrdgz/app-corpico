package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by Administrador on 21/05/2017.
 */

public class Tipo_Trabajo {
    private String tipo_cuadrilla;
    private String tipo_trabajo;
    private String servicio;

    public Tipo_Trabajo(String tipo_cuadrilla, String tipo_trabajo,String servicio) {
        this.tipo_cuadrilla = tipo_cuadrilla;
        this.tipo_trabajo = tipo_trabajo;
        this.servicio= servicio;
    }
    public String getServicio() {
        return servicio;
    }
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    public String getTipoCuadrilla() {
        return tipo_cuadrilla;
    }

    public void setTipoCuadrilla(String tipo_cuadrilla) {
        this.tipo_cuadrilla = tipo_cuadrilla;
    }

    public String getTipoTrabajo() {
        return tipo_trabajo;
    }

    public void setTipoTrabajo(String tipo_trabajo) {
        this.tipo_trabajo = tipo_trabajo;
    }
}
