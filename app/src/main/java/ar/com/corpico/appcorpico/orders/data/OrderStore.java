package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.login.domain.entity.Session;
import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface OrderStore {
    void getOrders(GetCallback callback, Criteria filter);

    interface GetCallback{
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    void addOrderEtape(String estado, ArrayList<String> numero);
    void getTypes(GetTypeStoreCallBack callback, Criteria filter);
    interface GetTypeStoreCallBack{
        void onSuccess(List<Tipo> tipos);
        void onError(String error);
    }
    void getCuadrillas(GetCuadrillaStoreCallBack callback, Criteria filter);
    interface GetCuadrillaStoreCallBack{
        void onSuccess(List<Cuadrilla> cuadrillas);
        void onError(String error);
    }
}
