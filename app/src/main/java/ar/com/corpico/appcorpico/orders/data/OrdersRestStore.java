package ar.com.corpico.appcorpico.orders.data;

import android.os.Handler;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
///
/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRestStore implements OrderStore {
    // TODO: Reemplazar esta fuente falsa por una conexión real al servidor
    private  static final ArrayList<Order> mFakeRestData = new ArrayList<>();

    static {
        mFakeRestData.add(new Order("2017-01-23T00:00:00.000-03:00","839127", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad", "Pendiente", "15514", "1", "Luisa Gonzalez", "Pasaje Rivero 957", "General Pico","","35.6630S", "63.7608W", "Nada",""));
        mFakeRestData.add(new Order("2017-01-23T00:00:00.000-03:00","839128", "Eléctrico", "3", "Cambio de Medidor", "Trabado","Cerrada", "22814", "1", "Jorgelina Rodriguez", "Calle 531", "General Pico","", "35.6562S", "63.7537W", "Algo",""));
        mFakeRestData.add(new Order("2017-01-23T00:00:00.000-03:00","839129", "Eléctrico", "4", "Colocacion de Medidor", "Suministro Nuevo", "Culminada", "24429", "7", "Gustavo Turienzo", "Calle 29", "General Pico","","35.6657S", "63.7494W", "Todo",""));
        mFakeRestData.add(new Order("2017-01-23T00:00:00.000-03:00","839130", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente", "No Culminada", "55472", "1", "Gonzalo Fernandez", "Calle 18", "General Pico","","35.6601S", "63.7690W", "Siempre",""));
        mFakeRestData.add(new Order("2017-01-23T00:00:00.000-03:00","839131", "Eléctrico", "1", "Retiro de Medidor", "Por Morosidad", "Pendiente", "40462", "2", "Antonella Privitera", "Calle 28", "General Pico","","35.6538S", "63.7528W", "Nunca",""));
        mFakeRestData.add(new Order("2017-01-24T00:00:00.000-03:00","839132", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad","Pendiente", "17495", "1", "Juan Perez", "Pasaje Rivero 957", "General Pico","","35.6629S", "63.7476W", "Nada",""));
        mFakeRestData.add(new Order("2017-01-24T00:00:00.000-03:00","839133", "Eléctrico", "3", "Cambio de Medidor", "Solic. Energia Prepaga","Cerrada", "6377", "1", "Rodrigo Nieto", "Calle 531", "General Pico","","35.6788S", "63.7530W", "Algo",""));
        mFakeRestData.add(new Order("2017-01-24T00:00:00.000-03:00","839134", "Eléctrico", "4", "Colocacion de Medidor", "Regularizacion de Deuda", "Culminada", "44345", "1", "Jose Ferrando", "Calle 29", "General Pico","","35.6678S", "63.7555W", "Todo",""));
        mFakeRestData.add(new Order("2017-01-24T00:00:00.000-03:00","839135", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente","No Culminada", "42352", "1", "Fabio Gomez", "Calle 18", "General Pico","","35.6810S", "63.7491W", "Siempre",""));
        mFakeRestData.add(new Order("2017-01-24T00:00:00.000-03:00","839136", "Eléctrico","1", "Retiro de Medidor", "Por Morosidad","Pendiente", "20484", "1", "Maria Gallo", "Calle 28", "General Pico","","35.6598S", "63.7498W", "Nunca",""));
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
