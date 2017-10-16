package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    void asignarOrder(String cuadrilla, List<String> listorder,String observacion);
    void asignarTurno(String numero, @Nullable DateTime turno);
}
