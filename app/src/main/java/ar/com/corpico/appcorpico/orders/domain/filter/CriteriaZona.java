package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaZona implements Criteria<Order> {
    private final String zona;

    public CriteriaZona(String zona) {
        this.zona = zona;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();
        if (zona==null){
            filteredOrders = orders;
            return filteredOrders;
        }
        if (!zona.equals("Todos") && zona!=null ){
            for (Order order : orders) {
                if (order.getZona().equals(zona)) {
                    filteredOrders.add(order);
                }
            }
        }if (zona.equals("Todos")){
            filteredOrders = orders;
        }
        return filteredOrders;
    }


    @Override
    public Object toSql() {
        return null;
    }
}
