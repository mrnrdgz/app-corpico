package ar.com.corpico.appcorpico.orders.data;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public interface IOrdersRepository {
    public void findOrder(FindCallback callback, Criteria filter);
    interface FindCallback{
        void onSuccess(List<Order> orders);

        void onError(String error);
    }
}
