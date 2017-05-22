package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by Administrador on 22/05/2017.
 */

public class Cuadrilla {
    private String cuadrilla;
    private String turno;
    private String servicio;

    public Cuadrilla(String cuadrilla, String turno, String servicio) {
        this.cuadrilla = cuadrilla;
        this.turno = turno;
        this.servicio = servicio;
    }

    public String getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
