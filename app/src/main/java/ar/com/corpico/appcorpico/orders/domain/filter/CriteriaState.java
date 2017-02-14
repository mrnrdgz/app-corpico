package ar.com.corpico.appcorpico.orders.domain.filter;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaState implements Criteria<Order> {

    private final String state;

    public CriteriaState(String state) {
        this.state = state;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = new ArrayList<>();

        if (!state.equals("Todos")) {


            Collection<Order> filteredResult = Collections2.filter(orders, new Predicate<Order>() {
                @Override
                public boolean apply(Order input) {
                    ArrayList<Etapa> etapas = input.getEtapas();
                    for (Etapa etapa : etapas) {
                        if (etapa.getEstado().equals(state)) {
                            return true;
                        }
                    }
                    return false;
                }
            });

            filteredOrders = Lists.newArrayList(filteredResult);
        } else {
            filteredOrders = orders;

        }
        return filteredOrders;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + state;
    }
}
