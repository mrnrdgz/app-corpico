package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaSector implements Criteria {
    private final String sector;

    public CriteriaSector(String sector) {
        this.sector = sector;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();

        for (Order order : orders) {
            if(order.getSector().equals(sector)){
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
