package ar.com.corpico.appcorpico.orders.data;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRepository implements IOrdersRepository {
    private static OrdersRepository repository;

    // Relaciones de composición
    private OrdersRestStore mOrdersRestStore;

    public OrdersRepository(OrdersRestStore ordersRestStore) {
        mOrdersRestStore = Preconditions.checkNotNull(ordersRestStore,
                "La fuente de datos rest de ordenes no puede ser null");
    }

    public static OrdersRepository getInstance(OrdersRestStore ordersRestStore) {
        if (repository == null) {
            repository = new OrdersRepository(ordersRestStore);
        }
        return repository;
    }

    @Override
    public void findOrder(final FindCallback callback, Criteria filter) {
        /**
         * Estrategia:
         * 1. Se consuta primero el servicio REST
         * 2. Guardar los datos del servidor en SQLite
         * 3. Enviar los datos al invocador
         */

        mOrdersRestStore.getOrders(new OrderStore.GetCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(orders);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, filter);
    }
}
