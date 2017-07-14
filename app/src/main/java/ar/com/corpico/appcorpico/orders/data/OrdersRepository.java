package ar.com.corpico.appcorpico.orders.data;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRepository implements IOrdersRepository {
    private static OrdersRepository repository;

    // Relaciones de composición
    private FuenteOrdenesServidor mOrdersRestStore;

    private OrdersRepository(FuenteOrdenesServidor ordersRestStore) {
        mOrdersRestStore = Preconditions.checkNotNull(ordersRestStore,
                "La fuente de datos rest de ordenes no puede ser null");
    }

    public static OrdersRepository getInstance(FuenteOrdenesServidor ordersRestStore) {
        if (repository == null) {
            repository = new OrdersRepository(ordersRestStore);
        }
        return repository;
    }

    @Override
    public void findOrder(final OrdersRepositoryCallback callback, Specification filter) {
        /**
         * Estrategia:
         * 1. Se consuta primero el servicio REST
         * 2. Guardar los datos del servidor en SQLite
         * 3. Enviar los datos al invocador
         */

        OrderStore.GetCallback callback1 = new OrderStore.GetCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(orders);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdersRestStore.getOrders(callback1, filter);
    }

    @Override
    public void addOrderState(String estado, ArrayList<String> numero) {
        mOrdersRestStore.addOrderEtape(estado, numero);
    }

    @Override
    public void findCuadrilla(final CuadrillaxTipoRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetCuadrillaxTipoStoreCallBack callback1 = new OrderStore.GetCuadrillaxTipoStoreCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tiposcuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tiposcuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdersRestStore.getCuadrillaxTipo(callback1, filter);
    }

    @Override
    public void findTipoCuadrilla(final TipoCuadrillaRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetTipoCuadrillaStoreCallBack callback1 = new OrderStore.GetTipoCuadrillaStoreCallBack() {
            @Override
            public void onSuccess(List<Tipo_Cuadrilla> tipoCuadrilla) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tipoCuadrilla);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdersRestStore.getTipoCuadrilla(callback1, filter);
    }

    @Override
    public void findTipoTrabajo(final TipoTrabajoRepositoryCallBack callback, Criteria filter) {
        OrderStore.GetTipoTrabajoStoreCallBack callback1 = new OrderStore.GetTipoTrabajoStoreCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(tipoTrabajo);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdersRestStore.getTipoTrabajo(callback1, filter);
    }
    @Override
    //public void findZona(final ZonaRepositoryCallBack callback, Criteria filter) {
    public void findZona(final ZonaRepositoryCallBack callback) {
        OrderStore.GetZonaStoreCallBack callback1 = new OrderStore.GetZonaStoreCallBack() {
            @Override
            public void onSuccess(List<Zona> zona) {
                // TODO: Guardar datos en SQLite. Posible método save()/insert()/add()
                callback.onSuccess(zona);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };

        mOrdersRestStore.getZona(callback1);
    }
}
