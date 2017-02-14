package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class AndCriteria implements Criteria <Order>{

    private Criteria firtCriteria;
    private Criteria secondCriteria;

    public AndCriteria(Criteria firtCriteria, Criteria secondCriteria) {
        this.firtCriteria = firtCriteria;
        this.secondCriteria = secondCriteria;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = firtCriteria.match(orders);
        return secondCriteria.match(filteredOrders);
    }

    @Override
    public Object toSql() {
        return null;
    }


}
