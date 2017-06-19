package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Criterio con OR inclusivo
 */

public class OrCriteria implements Criteria <Order>{

    private Criteria primerCriterio;
    private Criteria segundoCriterio;

    public OrCriteria(Criteria primerCriterio, Criteria segundoCriterio) {
        this.primerCriterio = primerCriterio;
        this.segundoCriterio = segundoCriterio;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        List<Order> primerResultado = primerCriterio.match(orders);
        List<Order> segundoResultado = segundoCriterio.match(orders);

        for(Order order: primerResultado){
            if(!segundoResultado.contains(order)){
                segundoResultado.add(order);
            }
        }
        return segundoResultado;
    }

    @Override
    public Object toSql() {
        return null;
    }


}
