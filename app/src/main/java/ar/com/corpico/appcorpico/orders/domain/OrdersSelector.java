package ar.com.corpico.appcorpico.orders.domain;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.CompositeSpec;

/**
 * Consulta de productos
 */

public class OrdersSelector {

    private final Query mQuery;

    public OrdersSelector(Query query) {
        mQuery = query;
    }

    public List<Order> selectListRows(List<Order> items) {
        final CompositeSpec<Order> memorySpecification
                = (CompositeSpec<Order>) mQuery.getSpecification();
        List<Order> affectedOrders;
        Comparator<Order> comparator = mFechaSolicitudComparator;

        // Filtrar Por...
        affectedOrders = new ArrayList<>(
                Collections2.filter(items, new Predicate<Order>() {
                    @Override
                    public boolean apply(Order order) {
                        return memorySpecification == null
                                || memorySpecification.isSatisfiedBy(order);
                    }
                }));

        // Ordenar Por...
        if (mQuery.getFieldSort() != null) {
            switch (mQuery.getFieldSort()) {
                case "FechaSolicitud":
                    comparator = mFechaSolicitudComparator;
                    break;
                // TODO: Añade un caso por cada comparador
                case "Turno":
                    comparator = mTurnoComparator;
                    break;
            }
        }
        Collections.sort(affectedOrders, comparator);

        // Elegir Página...
        /*affectedOrders = CollectionsUtils.getPage(affectedOrders,
                mQuery.getPageNumber(), mQuery.getPageSize());*/

        return affectedOrders;
    }


    private Comparator<Order> mFechaSolicitudComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getFechaSolicitud().compareTo(o2.getFechaSolicitud());
            } else {
                return o2.getFechaSolicitud().compareTo(o1.getFechaSolicitud());
            }
        }
    };

    private Comparator<Order> mTurnoComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getTurno().compareTo(o2.getTurno());
            } else {
                return o2.getTurno().compareTo(o1.getTurno());
            }
        }
    };

    // TODO: Agrega más comparadores si los necesitas
}
