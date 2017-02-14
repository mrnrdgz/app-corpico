package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 04/02/2017.
 */
//Todo: ver
public class CriteriaSearch implements Criteria <Order>{
    private String search;

    public CriteriaSearch(String search) {
        this.search = search;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();

        if (search == null) {
            return orders;
        }

        for (Order order : orders) {
            if (order.getTitular().toUpperCase().contains(search.toUpperCase())) {
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
