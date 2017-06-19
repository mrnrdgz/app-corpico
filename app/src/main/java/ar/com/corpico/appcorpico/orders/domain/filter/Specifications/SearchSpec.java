package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.order;

/**
 * Created by User on 10/06/2017.
 */

public class SearchSpec extends CompositeSpec<Order>{

    private String search;

    public SearchSpec(String search) {
        this.search = search;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {

        // Si el estado tiene el valor "Todos", entonces <code>item</code> satisface la especificación
        if (search == null){
            return true;
        }
        if (search.equals("Todos") || item.getTitular().toUpperCase().contains(search.toUpperCase())
                || item.getNumero().toUpperCase().contains(search.toUpperCase())
                || item.getObservacion().toUpperCase().contains(search.toUpperCase())
                || item.getAsociado().toUpperCase().contains(search.toUpperCase())
                || item.getDomicilio().toUpperCase().contains(search.toUpperCase())) {
            return true;
        }
        return false;
    }
}