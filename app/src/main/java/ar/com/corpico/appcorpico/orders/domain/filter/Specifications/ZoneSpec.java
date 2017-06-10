package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by User on 10/06/2017.
 */

public class ZoneSpec extends CompositeSpec<Order> {
    private String zona;

    public ZoneSpec(String zona) {
        this.zona = zona;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {
        return zona.equals("Todos") || zona.equals(item.getZona());

    }
}
