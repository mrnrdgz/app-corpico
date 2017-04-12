package ar.com.corpico.appcorpico.orders.domain.filter;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.order;

/**
 * Created by sistemas on 30/01/2017.
 */

public class OrderCriteriaFecha implements Criteria<Order> {
    private final String mEstado;
    private final DateTime mDesde;
    private final DateTime mHasta;
    private final Boolean mEstadoActual;

    public OrderCriteriaFecha(String estado, DateTime desde, DateTime hasta,Boolean estadoActual) {
        mEstado = estado;
        mDesde = desde;
        mHasta = hasta;
        mEstadoActual=estadoActual;
    }

    @Override
    public List<Order> match(List<Order> itemsDeEntrada) {
        // Buscar todas las ordenes cuya fecha se encuentre en el rango determinado
        List<Order> filteredOrders = new ArrayList<>();
        if (mDesde != null && mHasta != null) {
            Interval interval = new Interval(mDesde,mHasta);
            // Barrido de ordernes de entrada
            for (Order order : itemsDeEntrada) {
                if(!mEstadoActual) {
                    ArrayList<Etapa> etapas = order.getEtapas();
                    // ¿El estado de la etapa y la fecha coinciden con los parámetros de entrada?
                    for (Etapa etapa : etapas) {
                        if (mEstado.equals(etapa.getEstado())|| mEstado.equals("Todos")) {
                            DateTime fechaEtapa = new DateTime(etapa.getFecha());
                            if (interval.contains(fechaEtapa)) {
                                filteredOrders.add(order);
                            }
                        }
                    }
                }else{
                    /*ArrayList<Etapa> etapasOrdenadas=  order.sortEtapas(order.getEtapas());
                    String estadoEtapa = etapasOrdenadas.get(etapasOrdenadas.size()-1).getEstado();
                    DateTime fechaEtapa = new DateTime(etapasOrdenadas.get(etapasOrdenadas.size()-1).getFecha());*/
                    Etapa currentEtapa = order.getCurrentEtapa(order.getEtapas());
                    String estadoEtapa = currentEtapa.getEstado();
                    DateTime fechaEtapa = new DateTime(currentEtapa.getFecha());
                    if (mEstado.equals(estadoEtapa)|| mEstado.equals("Todos")) {
                        if (interval.contains(fechaEtapa)) {
                            filteredOrders.add(order);
                        }
                    }
                }
            }

        }if (mDesde == null && mHasta == null && mEstadoActual) {
            for (Order order : itemsDeEntrada) {
                Etapa currentEtapa = order.getCurrentEtapa(order.getEtapas());
                String estadoEtapa = currentEtapa.getEstado();
                if (mEstado.equals(estadoEtapa) || mEstado.equals("Todos")) {
                    filteredOrders.add(order);
                }
            }
            //filteredOrders = itemsDeEntrada;
        }

        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
