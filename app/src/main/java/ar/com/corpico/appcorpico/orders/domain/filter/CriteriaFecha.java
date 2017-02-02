package ar.com.corpico.appcorpico.orders.domain.filter;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by sistemas on 30/01/2017.
 */

public class CriteriaFecha implements Criteria {
    private final DateTime mdesde;
    private final DateTime mhasta;

    public CriteriaFecha(DateTime desde, DateTime hasta) {
        this.mdesde = desde;
        this.mhasta = hasta;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();
        final DateTime d = new DateTime(mdesde);
        final DateTime h = new DateTime(mhasta);
        Interval interval = new Interval(d, h);
        for (Order order : orders) {
            if (interval.contains(new DateTime(order.getFecha()))) {
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