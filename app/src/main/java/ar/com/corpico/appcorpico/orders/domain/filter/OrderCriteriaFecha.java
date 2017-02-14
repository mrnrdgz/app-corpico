package ar.com.corpico.appcorpico.orders.domain.filter;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by sistemas on 30/01/2017.
 */

public class OrderCriteriaFecha implements Criteria<Order> {
    private final String mEstado;
    private final DateTime mDesde;
    private final DateTime mHasta;

    public OrderCriteriaFecha(String estado, DateTime desde, DateTime hasta) {
        mEstado = estado;
        mDesde = desde;
        mHasta = hasta;
    }

    /*@Override
    public List<Etapa> match(List<Etapa> etapas) {
        List<Etapa> filteredEtapas = new ArrayList<>();
        if(mDesde != null && mHasta != null) {
            final DateTime d = new DateTime(mDesde);
            final DateTime h = new DateTime(mHasta);
            Interval interval = new Interval(d.withTimeAtStartOfDay(), h.withTimeAtStartOfDay());
            for (Etapa etapa : etapas) {
                if (interval.contains(new DateTime(etapa.getFecha()))) {
                    filteredEtapas.add(etapa);
                }
            }
        }else{
            filteredEtapas=etapas;
        }

        return filteredEtapas;
    }*/

    @Override
    public List<Order> match(List<Order> itemsDeEntrada) {
        // Buscar todas las ordenes cuya fecha se encuentre en el rango determinado
        List<Order> filteredOrders = new ArrayList<>();

        if (mDesde != null && mHasta != null) {
            final DateTime d = new DateTime(mDesde);
            final DateTime h = new DateTime(mHasta);

            Interval interval = new Interval(d.withTimeAtStartOfDay(), h.withTimeAtStartOfDay());

            // Barrido de ordernes de entrada
            for (Order order : itemsDeEntrada) {
                ArrayList<Etapa> etapasActuales = order.getEtapas();

                // ¿El estado de la etapa y la fecha coinciden con los parámetros de entrada?
                for (Etapa etapa : etapasActuales) {
                    if (mEstado.equals(etapa.getEstado())) {
                        DateTime fechaActual = new DateTime(etapa.getFecha());

                        if (interval.contains(fechaActual)) {
                            filteredOrders.add(order);
                        }
                    }
                }
            }


        } else {
            filteredOrders = itemsDeEntrada;
        }

        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
