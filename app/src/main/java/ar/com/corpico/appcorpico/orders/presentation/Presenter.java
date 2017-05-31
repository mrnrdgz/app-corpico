package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    void loadOrderList(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    void asignarOrder(String cuadrilla, List<String> listorder);
    void loadTipoTrabajo(String servicio);
    void loadCuadrillasXTipo(String tipotrabajo);

}
