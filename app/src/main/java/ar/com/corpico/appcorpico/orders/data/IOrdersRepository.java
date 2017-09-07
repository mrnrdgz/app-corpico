package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface IOrdersRepository {
    //void findOrder(OrdersRepositoryCallback callback, Specification filter);
    void findOrder(OrdersRepositoryCallback callback, Query query);
    interface OrdersRepositoryCallback {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    void addOrderState(String estado, ArrayList<String> numero,String observacion);
    void findCuadrilla(CuadrillaxTipoRepositoryCallBack callback, Criteria filter);
    interface CuadrillaxTipoRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipocuadrilla);
        void onError(String error);
    }
    void findTipoCuadrilla(TipoCuadrillaRepositoryCallBack callback, Criteria filter);
    interface TipoCuadrillaRepositoryCallBack {
        void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla);
        void onError(String error);
    }
    void findTipoTrabajo(TipoTrabajoRepositoryCallBack callback, Criteria filter);
    interface TipoTrabajoRepositoryCallBack {
        void onSuccess(List<Tipo_Trabajo> tipoTrabajo);
        void onError(String error);
    }
    void findZona(ZonaRepositoryCallBack callback);
    interface ZonaRepositoryCallBack {
        void onSuccess(List<Zona> zona);
        void onError(String error);
    }
}
