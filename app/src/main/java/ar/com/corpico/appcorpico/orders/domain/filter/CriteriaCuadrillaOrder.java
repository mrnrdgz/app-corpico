package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaCuadrillaOrder implements Criteria<Order> {
    private final String cuadrilla;

    public CriteriaCuadrillaOrder(String tipo) {
        this.cuadrilla=tipo;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();
        CriteriaCuadrillaTipo filteredTipo = new CriteriaCuadrillaTipo(cuadrilla);

        /*for (Order order : orders) {
            for(int x=0;x<filteredTipo.size();x++) {
                System.out.println(filteredTipo.get(x));
            }
            if (order.getTipo().equals(cuadrilla)) {
                filteredOrders.add(order);
            }
        }*/

        return filteredOrders;
    }


    @Override
    public Object toSql() {
        return "select ... from order where state = " + cuadrilla;
    }
}
