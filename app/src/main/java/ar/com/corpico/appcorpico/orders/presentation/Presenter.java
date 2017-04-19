package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    public void loadOrderList(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search,Boolean estadoActual);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    public void asignarOrder(String cuadrilla, String numero);
}
