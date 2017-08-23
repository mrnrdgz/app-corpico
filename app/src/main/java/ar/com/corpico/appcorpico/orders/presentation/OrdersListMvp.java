package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;

/**
 * Created by Administrador on 06/08/2017.
 */

public interface OrdersListMvp {


    interface View {
        void showOrderList(List<Order> listorder);
        void showOrderError(String error);
        void setPresenter(Presenter presenter);
        void showOrdesEmpty();
        void showProgressIndicator(boolean show);
        void setAsignarOrder(String cuadrilla, List<String> listorder);
        void close();
    }



    interface Presenter {
        void loadOrders(String tipoCuadrilla, String estado, List<String> tipoTrabajo,
                        List<String> zona, DateTime desde, DateTime hasta, String search,
                        Boolean estadoActual);
        void asignarOrder(String cuadrilla, List<String> listorder, String observacion);
    }
}
