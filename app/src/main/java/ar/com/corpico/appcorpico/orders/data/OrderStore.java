package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.login.domain.entity.Session;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface OrderStore {
    public void getOrders(GetCallback callback, Criteria filter);

    interface GetCallback{
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    public void addOrderEtape(String estado,ArrayList<String> numero);
}
