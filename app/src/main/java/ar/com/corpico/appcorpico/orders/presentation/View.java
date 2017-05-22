package ar.com.corpico.appcorpico.orders.presentation;

import android.app.Activity;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    void showOrderList(List<Order> listorder);
    void showCuadrillasList(List<Cuadrilla> listorder);
    void showOrderError(String error);
    void setPresenter(Presenter presenter);
    void showOrdesEmpty();
    void showProgressIndicator(boolean show);
    void setOrderFilter(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual);
    void setLoadOrderList(String tipo);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    //public void setAsignarOrder(String cuadrilla, String numero);
    void setAsignarOrder(String cuadrilla, List<String> listorder);
    // SON FUNCIONES DE MAP
}
