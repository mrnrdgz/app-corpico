package ar.com.corpico.appcorpico.orders.data;

import android.os.Handler;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.OrdersSelector;
import ar.com.corpico.appcorpico.orders.domain.Query;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
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
    //private static final ArrayList<Accion_Etapa> mFakeRestTipo_Accion = new ArrayList<>();

    private static final ArrayList<Zona> mFakeRestZona= new ArrayList<>();

    static {
        //TODO: PONER LAS ACCIONES REALIZADAS COMO UNA LISTA DE STRING
        mFakeRestEtapa.add(new Etapa(new DateTime("2017-01-23T00:00:00.000-03:00"), "Pendiente", "Nada",""));
        mFakeRestEtapa6.add(new Etapa(new DateTime("2017-01-23T00:00:00.000-03:00"), "Pendiente", "xxx",""));
        mFakeRestEtapa7.add(new Etapa(new DateTime("2017-02-23T00:00:00.000-03:00"), "Pendiente", "zzzz",""));
        mFakeRestEtapa8.add(new Etapa(new DateTime("2017-03-23T00:00:00.000-03:00"), "Pendiente", "www",""));
        mFakeRestEtapa9.add(new Etapa(new DateTime("2017-03-23T00:00:00.000-03:00"), "Pendiente", "sss",""));
        mFakeRestEtapa10.add(new Etapa(new DateTime("2017-03-23T00:00:00.000-03:00"), "Pendiente", "yyy",""));

        mFakeRestEtapa1.add(new Etapa(new DateTime("2017-01-23T00:00:00.000-03:00"), "Pendiente", "Nada",""));
        mFakeRestEtapa1.add(new Etapa(new DateTime("2017-01-25T00:00:00.000-03:00"), "Culminada", "Todo",""));
        mFakeRestEtapa1.add(new Etapa(new DateTime("2017-01-26T00:00:00.000-03:00"), "Cerrada", "Algo",""));

        mFakeRestEtapa2.add(new Etapa(new DateTime("2017-01-23T00:00:00.000-03:00"), "Pendiente", "Nada",""));
        mFakeRestEtapa2.add(new Etapa(new DateTime("2017-01-24T00:00:00.000-03:00"), "No Culminada", "Siempre",""));

        mFakeRestEtapa3.add(new Etapa(new DateTime("2017-01-23T00:00:00.000-03:00"), "Pendiente", "",""));
        mFakeRestEtapa3.add(new Etapa(new DateTime("2017-01-25T00:00:00.000-03:00"), "Culminada", "Todo",""));

        mFakeRestEtapa4.add(new Etapa(new DateTime("2017-01-24T00:00:00.000-03:00"), "Pendiente", "",""));
        mFakeRestEtapa4.add(new Etapa(new DateTime("2017-01-24T00:30:00.000-03:00"), "Culminada", "Todo",""));
        mFakeRestEtapa4.add(new Etapa(new DateTime("2017-01-24T00:20:00.000-03:00"), "Cerrada", "Algo",""));

        mFakeRestEtapa5.add(new Etapa(new DateTime("2017-01-22T00:00:00.000-03:00"), "Pendiente", "Nunca",""));
        mFakeRestEtapa5.add(new Etapa(new DateTime("2017-01-25T00:00:00.000-03:00"), "No Culminada", "Siempre",""));
        mFakeRestEtapa5.add(new Etapa(new DateTime("2017-01-25T00:10:00.000-03:00"), "Cerrada", "Algo",""));
    }

    static {

        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"), "839127", "Zona 2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa3, "000015514", "001", "Luisa Gonzalez", "Pasaje Rivero 957", "General Pico", "", "Residencial", "Residencial", "1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo", "35.6630S", "63.7608W", "Nada",new DateTime(),1,200,1));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839128", "Zona 3", "Cambio de Medidor", "Trabado", mFakeRestEtapa, "000022814", "001", "Jorgelina Rodriguez", "Calle 531", "General Pico", "", "Residencial", "Residencial", "1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6562S", "63.7537W", "Algo",null,1,200,2));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839129", "Zona 3", "Colocacion de Medidor", "Suministro Nuevo", mFakeRestEtapa6, "000024429", "007", "Gustavo Turienzo", "Calle 29", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6657S", "63.7494W", "Todo",new DateTime("2017-08-29T00:00:00.000-03:00"),1,100,15));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-26T00:00:00.000-03:00"),"839130", "Zona 4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa7, "000055472", "001", "Gonzalo Fernandez", "Calle 18", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6601S", "63.7690W", "Siempre",new DateTime(),1,400,4));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839131", "Zona 1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa8, "000040462", "002", "Antonella Privitera", "Calle 28", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6538S", "63.7528W", "Nunca",new DateTime(),1,500,5));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-25T00:00:00.000-03:00"),"839132", "Zona 2", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa9, "000017495", "001", "Juan Perez", "Pasaje Rivero 957", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6629S", "63.7476W", "Nada",new DateTime(),1,200,6));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839133", "Zona 3", "Cambio de Medidor", "Solic. Energia Prepaga", mFakeRestEtapa4, "000006377", "001", "Rodrigo Nieto", "Calle 531", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6788S", "63.7530W", "Algo",new DateTime(),1,200,7));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-26T00:00:00.000-03:00"),"839134", "Zona 4", "Colocacion de Medidor", "Regularizacion de Deuda", mFakeRestEtapa10, "000044345", "001", "Jose Ferrando", "Calle 29", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6678S", "63.7555W", "Todo",new DateTime("2017-08-28T00:00:00.000-03:00"),1,200,8));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839135", "Zona 4", "Retiro de Medidor", "Solicitud del Cliente", mFakeRestEtapa5, "000042352", "001", "Fabio Gomez", "Calle 18", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6810S", "63.7491W", "Siempre",new DateTime(),1,200,9));
        mFakeRestOrder.add(new Order(new DateTime("2017-01-24T00:00:00.000-03:00"),"839136", "Zona 1", "Retiro de Medidor", "Por Morosidad", mFakeRestEtapa2, "000020484", "001", "Maria Gallo", "Calle 28", "General Pico", "", "Residencial", "Residencial","1100 Watts","2585054","ELESTER","A150","","Rango 005/060 A","Monofásico 220 vo","35.6598S", "63.7498W", "Nunca",new DateTime(),1,200,10));
    }

    static {
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Conexiones","Colocacion de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Desconexiones","Retiro de Medidor","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Cambio de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Inspección-Verificación","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Verificación Lecturas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Desplazamiento de estructura","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Poda de arboles","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Revisión de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Mañana","Cambio de Tapa","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Auxiliar","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Auxiliar","Cambio de Tapa","Electrico"));

        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Cambio de Medidor","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Inspección-Verificación","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Verificación Lecturas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Desplazamiento de estructura","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Poda de arboles","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Reparación de veredas","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Cambio de Tapa","Electrico"));
        mFakeRestTipo_Trabajo.add(new Tipo_Trabajo("Varios Tarde","Revisión de Medidor","Electrico"));
    }
    static {
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Conexiones","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Desconexiones","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Varios Mañana","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Varios Tarde","Electrico"));
        mFakeRestTipo_Cuadrilla.add(new Tipo_Cuadrilla("Auxiliar","Electrico"));

    }
    /*static {
        mFakeRestTipo_Accion.add(new ("Cambio de Medidor","Rotutra de tapa"));
        mFakeRestTipo_Accion.add(new ("Cambio de Medidor","Rotura disyuntor"));
        /*mFakeRestTipo_Accion.add(new Tipo_Cuadrilla("Varios Mañana","Electrico"));
        mFakeRestTipo_Accion.add(new Tipo_Cuadrilla("Varios Tarde","Electrico"));
        mFakeRestTipo_Accion.add(new Tipo_Cuadrilla("Auxiliar","Electrico"));

    }*/
    static {
        mFakeRestZona.add(new Zona("Zona 1"));
        mFakeRestZona.add(new Zona("Zona 2"));
        mFakeRestZona.add(new Zona("Zona 3"));
        mFakeRestZona.add(new Zona("Zona 4"));
        mFakeRestZona.add(new Zona("Zona 5"));

    }
    @Override
    public void getOrders(final GetCallback callback, final Query query) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO: Realizar filtro
                //callback.onSuccess(filter.match(mFakeRestOrder));
                /*callback.onSuccess(Lists.newArrayList(Collections2.filter(mFakeRestOrder, new Predicate<Order>() {
                    @Override
                    public boolean apply(Order input) {
                        return filter.isSatisfiedBy(input);
                    }
                })));*/
                //Query mQuery = new Query(filter,"FechaSolicitud",1,0,0);
                OrdersSelector ordersSelector = new OrdersSelector(query);
                callback.onSuccess(ordersSelector.selectListRows(mFakeRestOrder));
            }
        }, 2000);

    }

    @Override
    public void addOrderEtape(String estado, ArrayList<String> numero, String observacion) {
        final Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date = df.format(Calendar.getInstance().getTime());

        for (String number : numero) {
            for (Order order : mFakeRestOrder) {
                if (order.getNumero().equals(number)) {
                    //TODO: PONER EL DATO DEL USUARIO QUE HIZO ESTA ETAPA.
                    Etapa etapa = new Etapa(new DateTime(date), estado, observacion,"");
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
    @Override
    public void getZona(GetZonaStoreCallBack callback) {
        callback.onSuccess(mFakeRestZona);
    }
}
