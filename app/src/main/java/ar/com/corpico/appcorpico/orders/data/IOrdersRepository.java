package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo;
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
    void findType(TiposRepositoryCallBack callback, Criteria filter);
    interface TiposRepositoryCallBack {
        void onSuccess(List<Tipo> tipos);
        void onError(String error);
    }
    void findCuadrilla(CuadrillasRepositoryCallBack callback, Criteria filter);
    interface CuadrillasRepositoryCallBack {
        void onSuccess(List<Cuadrilla> cuadrillas);
        void onError(String error);
    }
}
