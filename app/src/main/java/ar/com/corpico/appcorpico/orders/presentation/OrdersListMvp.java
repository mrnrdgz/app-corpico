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
        void showTipoCuadrillaList(List<Tipo_Cuadrilla> listorder);
        void showCuadrillaxTipoList(List<Tipo_Cuadrilla> listcuadrilla);

        void showOrderError(String error);
        void setPresenter(Presenter presenter);

        void showOrdesEmpty();
        void showProgressIndicator(boolean show);

        // TODO: el parametro "numero" luego lo reemplazare por List<Order>?
        //public void setAsignarOrder(String cuadrilla, String numero);
        void setAsignarOrder(String cuadrilla, List<String> listorder);
        // SON FUNCIONES DE MAP
    }



    interface Presenter {
        void loadOrders(String tipoCuadrilla, String estado, List<String> tipoTrabajo,
                        List<String> zona, DateTime desde, DateTime hasta, String search,
                        Boolean estadoActual);
        void asignarOrder(String cuadrilla, List<String> listorder, String observacion);
        void loadTipoCuadrilla(String servicio);
        void loadCuadrillasXTipo(String tipotrabajo);
        void setLoadTipoTrabajos(String cuadrilla);
        void setLoadZonas();

    }
}
