package ar.com.corpico.appcorpico.orders.data;

import android.os.Handler;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRestStore implements OrderStore {
    // TODO: Reemplazar esta fuente falsa por una conexión real al servidor
    private  static final ArrayList<Order> mFakeRestData = new ArrayList<>();

    static {
        mFakeRestData.add(new Order("1", "Malvinas", "Conexion", "Pendiente", "15514", "001", "Luisa Gonzalez", "Pasaje Rivero 957", "", "", "Nada"));
        mFakeRestData.add(new Order("2", "Ranqueles", "Desconexion", "Cerrada", "", "", "Jorgelina Rodriguez", "Calle 531", "", "", "Algo"));
        mFakeRestData.add(new Order("3", "Este", "Colocacion", "Culminada", "", "", "Gustavo Turienzo", "Calle 29", "", "", "Todo"));
        mFakeRestData.add(new Order("4", "Este", "Alumbrado", "No Culminada", "", "", "Gonzalo Fernandez", "Calle 18", "", "", "Siempre"));
        mFakeRestData.add(new Order("5", "Centro", "Medidor", "Pendiente", "", "", "Antonella Privitera", "Calle 28", "", "", "Nunca"));
    }

    @Override
    public void getOrders(final GetCallback callback, final Criteria filter) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: Realizar filtro
                callback.onSuccess(filter.match(mFakeRestData));
            }
        }, 2000);

    }
}
