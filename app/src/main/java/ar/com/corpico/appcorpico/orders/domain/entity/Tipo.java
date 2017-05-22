package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by Administrador on 21/05/2017.
 */

public class Tipo {
    private String cuadrilla;
    private String tipo;

    public Tipo(String cuadrilla, String tipo) {
        this.cuadrilla = cuadrilla;
        this.tipo = tipo;
    }

    public String getCuadrilla() {
        return cuadrilla;
    }

    public void setCuadrilla(String cuadrilla) {
        this.cuadrilla = cuadrilla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
