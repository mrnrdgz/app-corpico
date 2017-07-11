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
        if (zona == null){
            return true;
        }
        if (zona.equals("Todos")){
            return true;
        }
        return zona.equals(item.getZona());
    }
}
