package ar.com.corpico.appcorpico.orders.data;

import android.os.Handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
///

/**
 * Created by Administrador on 07/01/2017.
 */

public class OrdersRestStore implements OrderStore {
    // TODO: Reemplazar esta fuente falsa por una conexión real al servidor
    private static final ArrayList<Order> mFakeRestOrder = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa1 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa2 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa3 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa4 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa5 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa6 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa7 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa8 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa9 = new ArrayList<>();
    private static final ArrayList<Etapa> mFakeRestEtapa10 = new ArrayList<>();

    static {
        mFakeRestEtapa.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada"));
        mFakeRestEtapa6.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "xxx"));
        mFakeRestEtapa7.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "zzzz"));
        mFakeRestEtapa8.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "www"));
        mFakeRestEtapa9.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "sss"));
        mFakeRestEtapa10.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "yyy"));

        mFakeRestEtapa1.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada"));
        mFakeRestEtapa1.add(new Etapa("2017-01-25T00:00:00.000-03:00", "Culminada", "Todo"));
        mFakeRestEtapa1.add(new Etapa("2017-01-26T00:00:00.000-03:00", "Cerrada", "Algo"));

        mFakeRestEtapa2.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada"));
        mFakeRestEtapa2.add(new Etapa("2017-01-24T00:00:00.000-03:00", "No Culminada", "Siempre"));

        mFakeRestEtapa3.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", ""));
        mFakeRestEtapa3.add(new Etapa("2017-01-25T00:00:00.000-03:00", "Culminada", "Todo"));

        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:00:00.000-03:00", "Pendiente", ""));
        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:30:00.000-03:00", "Culminada", "Todo"));
        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:20:00.000-03:00", "Cerrada", "Algo"));

        mFakeRestEtapa5.add(new Etapa("2017-01-22T00:00:00.000-03:00", "Pendiente", "Nunca"));
        mFakeRestEtapa5.add(new Etapa("2017-01-25T00:00:00.000-03:00", "No Culminada", "Siempre"));
        mFakeRestEtapa5.add(new Etapa("2017-01-25T00:10:00.000-03:00", "Cerrada", "Algo"));
    }

    static {
        mFakeRestOrder.add(new Order("839127", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa3, "15514", "1", "Luisa Gonzalez", "Pasaje Rivero 957", "General Pico", "", "35.6630S", "63.7608W", "Nada"));
        mFakeRestOrder.add(new Order("839128", "Eléctrico", "3", "Cambio de Medidor", "Trabado", mFakeRestEtapa, "22814", "1", "Jorgelina Rodriguez", "Calle 531", "General Pico", "", "35.6562S", "63.7537W", "Algo"));
        mFakeRestOrder.add(new Order("839129", "Eléctrico", "4", "Colocacion de Medidor", "Suministro Nuevo", mFakeRestEtapa6, "24429", "7", "Gustavo Turienzo", "Calle 29", "General Pico", "", "35.6657S", "63.7494W", "Todo"));
        mFakeRestOrder.add(new Order("839130", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa7, "55472", "1", "Gonzalo Fernandez", "Calle 18", "General Pico", "", "35.6601S", "63.7690W", "Siempre"));
        mFakeRestOrder.add(new Order("839131", "Eléctrico", "1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa8, "40462", "2", "Antonella Privitera", "Calle 28", "General Pico", "", "35.6538S", "63.7528W", "Nunca"));
        mFakeRestOrder.add(new Order("839132", "Eléctrico", "2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa9, "17495", "1", "Juan Perez", "Pasaje Rivero 957", "General Pico", "", "35.6629S", "63.7476W", "Nada"));
        mFakeRestOrder.add(new Order("839133", "Eléctrico", "3", "Cambio de Medidor", "Solic. Energia Prepaga", mFakeRestEtapa4, "6377", "1", "Rodrigo Nieto", "Calle 531", "General Pico", "", "35.6788S", "63.7530W", "Algo"));
        mFakeRestOrder.add(new Order("839134", "Eléctrico", "4", "Colocacion de Medidor", "Regularizacion de Deuda", mFakeRestEtapa10, "44345", "1", "Jose Ferrando", "Calle 29", "General Pico", "", "35.6678S", "63.7555W", "Todo"));
        mFakeRestOrder.add(new Order("839135", "Eléctrico", "4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa5, "42352", "1", "Fabio Gomez", "Calle 18", "General Pico", "", "35.6810S", "63.7491W", "Siempre"));
        mFakeRestOrder.add(new Order("839136", "Eléctrico", "1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa2, "20484", "1", "Maria Gallo", "Calle 28", "General Pico", "", "35.6598S", "63.7498W", "Nunca"));
    }

    @Override
    public void getOrders(final GetCallback callback, final Criteria filter) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: Realizar filtro
                callback.onSuccess(filter.match(mFakeRestOrder));
            }
        }, 2000);

    }

    @Override
    public void addOrderEtape(String estado, ArrayList<String> numero) {
        final Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = df.format(Calendar.getInstance().getTime());

        for (String number : numero) {
            for (Order order : mFakeRestOrder) {
                if (order.getNumero().equals(number)) {
                    Etapa etapa = new Etapa(date, estado, "");
                    order.addEtapas(etapa);
                }
            }
        }

    }
}
