package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    void showOrderList(List<Order> listorder);
    void showOrderError(String error);
    void setPresenter(Presenter presenter);
    void showOrdesEmpty();
    void showProgressIndicator(boolean show);
    void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual);
    void setLoadOrderList(String tipo);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    //public void setAsignarOrder(String cuadrilla, String numero);
    void setAsignarOrder(String cuadrilla, List<String> listorder);
    List<Order> putOrderList();
    // SON FUNCIONES DE MAP
    void LoadOrderMap(List<Order> listorder);
    void showOrderMap(List<Order> listorder);
    Presenter getPresenter();
}
