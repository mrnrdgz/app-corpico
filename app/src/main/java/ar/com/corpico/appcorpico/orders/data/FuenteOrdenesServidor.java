package ar.com.corpico.appcorpico.orders.data;

import android.os.Handler;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;

import static android.R.attr.order;
///

/**
 * Created by Administrador on 07/01/2017.
 */

public class FuenteOrdenesServidor implements OrderStore {
    // TODO: Reemplazar esta fuente falsa por una conexión real al servidor
    private static final ArrayList<Order> mFakeRestOrder = new ArrayList<>();
    private static final ArrayList<Tipo_Trabajo> mFakeRestTipo_Trabajo = new ArrayList<>();
    private static final ArrayList<Tipo_Cuadrilla> mFakeRestTipo_Cuadrilla = new ArrayList<>();
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
        mFakeRestEtapa.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada",""));
        mFakeRestEtapa6.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "xxx",""));
        mFakeRestEtapa7.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "zzzz",""));
        mFakeRestEtapa8.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "www",""));
        mFakeRestEtapa9.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "sss",""));
        mFakeRestEtapa10.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Culminada", "yyy",""));

        mFakeRestEtapa1.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada",""));
        mFakeRestEtapa1.add(new Etapa("2017-01-25T00:00:00.000-03:00", "Culminada", "Todo",""));
        mFakeRestEtapa1.add(new Etapa("2017-01-26T00:00:00.000-03:00", "Cerrada", "Algo",""));

        mFakeRestEtapa2.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "Nada",""));
        mFakeRestEtapa2.add(new Etapa("2017-01-24T00:00:00.000-03:00", "No Culminada", "Siempre",""));

        mFakeRestEtapa3.add(new Etapa("2017-01-23T00:00:00.000-03:00", "Pendiente", "",""));
        mFakeRestEtapa3.add(new Etapa("2017-01-25T00:00:00.000-03:00", "Culminada", "Todo",""));

        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:00:00.000-03:00", "Pendiente", "",""));
        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:30:00.000-03:00", "Culminada", "Todo",""));
        mFakeRestEtapa4.add(new Etapa("2017-01-24T00:20:00.000-03:00", "Cerrada", "Algo",""));

        mFakeRestEtapa5.add(new Etapa("2017-01-22T00:00:00.000-03:00", "Pendiente", "Nunca",""));
        mFakeRestEtapa5.add(new Etapa("2017-01-25T00:00:00.000-03:00", "No Culminada", "Siempre",""));
        mFakeRestEtapa5.add(new Etapa("2017-01-25T00:10:00.000-03:00", "Cerrada", "Algo",""));
    }

    static {
        mFakeRestOrder.add(new Order("839127", "2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa3, "15514", "1", "Luisa Gonzalez", "Pasaje Rivero 957", "General Pico", "", "35.6630S", "63.7608W", "Nada"));
        mFakeRestOrder.add(new Order("839128", "3", "Cambio de Medidor", "Trabado", mFakeRestEtapa, "22814", "1", "Jorgelina Rodriguez", "Calle 531", "General Pico", "", "35.6562S", "63.7537W", "Algo"));
        mFakeRestOrder.add(new Order("839129", "3", "Colocacion de Medidor", "Suministro Nuevo", mFakeRestEtapa6, "24429", "7", "Gustavo Turienzo", "Calle 29", "General Pico", "", "35.6657S", "63.7494W", "Todo"));
        mFakeRestOrder.add(new Order("839130", "4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa7, "55472", "1", "Gonzalo Fernandez", "Calle 18", "General Pico", "", "35.6601S", "63.7690W", "Siempre"));
        mFakeRestOrder.add(new Order("839131", "1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa8, "40462", "2", "Antonella Privitera", "Calle 28", "General Pico", "", "35.6538S", "63.7528W", "Nunca"));
        mFakeRestOrder.add(new Order("839132", "2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa9, "17495", "1", "Juan Perez", "Pasaje Rivero 957", "General Pico", "", "35.6629S", "63.7476W", "Nada"));
        mFakeRestOrder.add(new Order("839133", "3", "Cambio de Medidor", "Solic. Energia Prepaga", mFakeRestEtapa4, "6377", "1", "Rodrigo Nieto", "Calle 531", "General Pico", "", "35.6788S", "63.7530W", "Algo"));
        mFakeRestOrder.add(new Order("839134", "4", "Colocacion de Medidor", "Regularizacion de Deuda", mFakeRestEtapa10, "44345", "1", "Jose Ferrando", "Calle 29", "General Pico", "", "35.6678S", "63.7555W", "Todo"));
        mFakeRestOrder.add(new Order("839135", "4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa5, "42352", "1", "Fabio Gomez", "Calle 18", "General Pico", "", "35.6810S", "63.7491W", "Siempre"));
        mFakeRestOrder.add(new Order("839136", "1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa2, "20484", "1", "Maria Gallo", "Calle 28", "General Pico", "", "35.6598S", "63.7498W", "Nunca"));
    }

    static {
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Conexiones","Colocacion de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Desconexiones","Retiro de Medidor","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Cambio de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Inspección-Verificación","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Verificación Lecturas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Desplazamiento de estructura","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Poda de arboles","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Revisión de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Mañana","Cambio de Tapa","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Auxiliar","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Auxiliar","Cambio de Tapa","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Cambio de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Inspección-Verificación","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Verificación Lecturas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Desplazamiento de estructura","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Poda de arboles","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("varios Tarde","Cambio de Tapa","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Revisión de Medidor","Electrico"));
    }
    static {
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Conexiones","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Desconexiones","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("varios Mañana","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("varios Tarde","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Auxiliar","Electrico"));

    }
    @Override
    public void getOrders(final GetCallback callback, final Specification filter) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: Realizar filtro
                //callback.onSuccess(filter.match(mFakeRestOrder));
                callback.onSuccess(Lists.newArrayList(Collections2.filter(mFakeRestOrder, new Predicate<Order>() {
                    @Override
                    public boolean apply(Order input) {
                        return filter.isSatisfiedBy(input);
                    }
                })));
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
                    //TODO: PONER EL DATO DEL USUARIO QUE HIZO ESTA ETAPA.
                    Etapa etapa = new Etapa(date, estado, "","");
                    order.addEtapas(etapa);
                }
            }
        }

    }

    @Override
    public void getCuadrillaxTipo(final GetCuadrillaxTipoStoreCallBack callback, final Criteria filter) {
        callback.onSuccess(filter.match(mFakeRestTipo_Trabajo));
    }

    @Override
    public void getTipoCuadrilla(GetTipoCuadrillaStoreCallBack callback, Criteria filter) {
        callback.onSuccess(filter.match(mFakeRestTipo_Cuadrilla));
    }
    @Override
    public void getTipoTrabajo(GetTipoTrabajoStoreCallBack callback, Criteria filter) {
        callback.onSuccess(filter.match(mFakeRestTipo_Trabajo));
    }
}
