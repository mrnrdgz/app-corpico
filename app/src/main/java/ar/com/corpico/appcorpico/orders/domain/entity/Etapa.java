package ar.com.corpico.appcorpico.orders.domain.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sistemas on 14/02/2017.
 */

public class Etapa {
    private String mFecha;
    private String mEstado;
    private String mObservacion;

    public Etapa(String Fecha, String Estado, String Observacion) {
        mFecha = Fecha;
        mEstado = Estado;
        mObservacion = Observacion;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String Fecha) {
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
    public static ArrayList<Etapa> sortEtapa(ArrayList etapas) {
        Collections.sort(etapas, new Comparator<Etapa>() {
            @Override
            public int compare(Etapa p1, Etapa p2) {
                return new DateTime(p1.getFecha()).compareTo(new DateTime(p2.getFecha()));
            }
        });
        return etapas;
    }
}
