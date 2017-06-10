package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by User on 10/06/2017.
 */

public class StateSpec extends CompositeSpec<Order>{

    private String estado;

    public StateSpec(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {

        // Si el estado tiene el valor "Todos", entonces <code>item</code> satisface la especificación
        if (estado.equals("Todos")) {
            return true;
        }

        Etapa etapaActual = item.getCurrentEtapa();

        return estado.equals(etapaActual.getEstado());


    }
}
