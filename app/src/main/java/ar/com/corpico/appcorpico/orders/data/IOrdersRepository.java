package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface IOrdersRepository {
    void findOrder(OrdersRepositoryCallback callback, Criteria filter);
    interface OrdersRepositoryCallback {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    void addOrderState(String estado, ArrayList<String> numero);
    void findCuadrilla(CuadrillaxTipoRepositoryCallBack callback, Criteria filter);
    interface CuadrillaxTipoRepositoryCallBack {
        void onSuccess(List<Tipo_Cuadrilla> tipocuadrilla);
        void onError(String error);
    }
    void findTipoTrabajo(TipoTrabajoRepositoryCallBack callback, Criteria filter);
    interface TipoTrabajoRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }
}
