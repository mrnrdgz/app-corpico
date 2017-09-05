package ar.com.corpico.appcorpico.orders.domain.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sistemas on 14/02/2017.
 */

public class Etapa {
    private DateTime mFecha;
    private String mEstado;
    private String mObservacion;
    private String mUsuario;

    public Etapa(DateTime Fecha, String Estado, String Observacion, String Usuario) {
        mFecha = Fecha;
        mEstado = Estado;
        mObservacion = Observacion;
        mUsuario=Usuario;

    }

    public String getUsuario() {
        return mUsuario;
    }
    public void setUsuario(String Usuario) {
        this.mUsuario = Usuario;
    }
    public DateTime getFecha() {
        return mFecha;
    }

    public void setFecha(DateTime Fecha) {
        mFecha = Fecha;
    }

    public String getEstado() {
        return mEstado;
    }

    public void setEstado(String Estado) {
        mEstado = Estado;
    }

    public String getObservacion() {
        return mObservacion;
    }

    public void setObservacion(String Observacion) {
        mObservacion = Observacion;
    }
}
