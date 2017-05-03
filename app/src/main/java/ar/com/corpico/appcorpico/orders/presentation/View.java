package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    public void showOrderList(List<Order> listorder);
    public void showOrderError(String error);
    public void setPresenter(Presenter presenter);
    public void showOrdesEmpty();
    public void showProgressIndicator(boolean show);
    public void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search,Boolean estadoActual);
    public void setLoadOrderList(String tipo);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    //public void setAsignarOrder(String cuadrilla, String numero);
    public void setAsignarOrder(String cuadrilla, List<String> listorder);
}
