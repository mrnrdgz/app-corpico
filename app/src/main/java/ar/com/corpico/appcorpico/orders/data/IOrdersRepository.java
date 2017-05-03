package ar.com.corpico.appcorpico.orders.data;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface IOrdersRepository {
    public void findOrder(OrdersRepositoryCallback callback, Criteria filter);
    interface OrdersRepositoryCallback {
        void onSuccess(List<Order> orders);
        void onError(String error);
    }
    public void addOrderState(String estado,ArrayList<String> numero);
}
