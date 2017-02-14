package ar.com.corpico.appcorpico.orders.domain.entity;

/**
 * Created by sistemas on 14/02/2017.
 */

public class Etapa {
    private String mFecha;
    private String mEstado;
    private String mObservacion;

    public Etapa(String Fecha, String Estado, String Observacion) {
        this.mFecha = Fecha;
        this.mEstado = Estado;
        this.mObservacion = Observacion;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String Fecha) {
        this.mFecha = Fecha;
    }

    public String getEstado() {
        return mEstado;
    }

    public void setEstado(String Estado) {
        this.mEstado = Estado;
    }

    public String getObservacion() {
        return mObservacion;
    }

    public void setObservacionDelOperario(String Observacion) {
        this.mObservacion = Observacion;
    }
}
