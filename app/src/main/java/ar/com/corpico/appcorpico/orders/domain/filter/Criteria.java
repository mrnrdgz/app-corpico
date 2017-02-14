package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 08/01/2017.
 */

public interface Criteria {
    public List<Etapa> matchDate(List<Etapa> etapas);
    public List<Order> match(List<Order> orders);
    public Object toSql();
}
