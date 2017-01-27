package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaTipo implements Criteria {
    private final String tipo;

    public CriteriaTipo(String tipo) {
        this.tipo=tipo;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();

        for (Order order : orders) {
            if (order.getTipo().equals(tipo)) {
                filteredOrders.add(order);
            }
        }

        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + tipo;
    }
}
