package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaState implements Criteria {

    private final String state;

    public CriteriaState(String state) {
        this.state = state;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();
        if (!state.equals("Todos")) {
            for (Order order : orders) {
                if (order.getmEstado().equals(state)) {
                    filteredOrders.add(order);
                }
            }
        }else{
            for (Order order : orders) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + state;
    }
}
