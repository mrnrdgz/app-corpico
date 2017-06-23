package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    void showOrderList(List<Order> listorder);
    void showTipoCuadrillaList(List<Tipo_Cuadrilla> listorder);
    void showCuadrillaxTipoList(List<Tipo_Cuadrilla> listcuadrilla);
    List<String> getTipoTrabajo();
    void showOrderError(String error);
    void setPresenter(Presenter presenter);
    void setTipoTrabajo(List<String> tipoTrabajo);
    void showOrdesEmpty();
    void showProgressIndicator(boolean show);
    void setOrderFilter(String estado, List<String> tipo, String sector, DateTime desde, DateTime hasta, String search, Boolean estadoActual);
    void setLoadOrderList(String tipo);
    // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
    //public void setAsignarOrder(String cuadrilla, String numero);
    void setAsignarOrder(String cuadrilla, List<String> listorder);
    // SON FUNCIONES DE MAP
}
