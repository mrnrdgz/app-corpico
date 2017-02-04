package ar.com.corpico.appcorpico.orders.domain.filter;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

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
        if(mdesde != null && mhasta != null) {
            final DateTime d = new DateTime(mdesde);
            final DateTime h = new DateTime(mhasta);
            Interval interval = new Interval(d.withTimeAtStartOfDay(), h.withTimeAtStartOfDay());
            for (Order order : orders) {
                if (interval.contains(new DateTime(order.getFecha()))) {
                    filteredOrders.add(order);
                }
            }
        }else{
            filteredOrders=orders;
        }

        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
