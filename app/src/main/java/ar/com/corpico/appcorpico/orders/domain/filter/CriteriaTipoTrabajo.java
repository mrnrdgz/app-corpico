package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaTipoTrabajo implements Criteria<Order> {
    private final String tipo_trabajo;

    public CriteriaTipoTrabajo(String tipo_trabajo) {
        this.tipo_trabajo=tipo_trabajo;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();
        if (tipo_trabajo==null){
            return filteredOrders;
        }
        if (!tipo_trabajo.equals("Todos") && !tipo_trabajo.equals("Varios")&& tipo_trabajo!=null) {
            for (Order order : orders) {
                if (order.getTipo_Trabajo().equals(tipo_trabajo)) {
                    filteredOrders.add(order);
                }
            }
        }
        /*if (tipo.equals("Varios")) {
            for (Order order : orders) {
                if (order.getTipo() != "Colocacion de Medidor" && order.getTipo() != "Retiro de Medidor") {
                    filteredOrders.add(order);
                }
            }
        }*/
        if (tipo_trabajo.equals("Todos") ) {
            filteredOrders = orders;
        }
        return filteredOrders;
    }


    @Override
    public Object toSql() {
        return "select ... from order where state = " + tipo_trabajo;
    }
}
