package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class AndCriteria implements Criteria {

    private Criteria firtCriteria;
    private Criteria secondCriteria;
    private Criteria trhidCriteria;

    public AndCriteria(Criteria firtCriteria, Criteria secondCriteria,Criteria trhidCriteria) {
        this.firtCriteria = firtCriteria;
        this.secondCriteria = secondCriteria;
        this.trhidCriteria = trhidCriteria;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> filteredOrders = firtCriteria.match(orders);
        return trhidCriteria.match(secondCriteria.match(filteredOrders));
    }

    @Override
    public Object toSql() {
        return null;
    }


}
