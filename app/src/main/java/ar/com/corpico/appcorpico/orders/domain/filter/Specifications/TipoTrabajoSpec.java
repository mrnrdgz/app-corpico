package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by User on 10/06/2017.
 */

public class TipoTrabajoSpec extends CompositeSpec<Order> {

    private String tiposTrabajo;

    public TipoTrabajoSpec(String tiposTrabajo) {

        this.tiposTrabajo = tiposTrabajo;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {
        // Si el tipoTrabajo tiene el valor "Todos", entonces <code>item</code> satisface la especificación
        if(tiposTrabajo==null){
            return true;
        }
        if (tiposTrabajo.equals("Todos")) {
            return true;
        }
        return tiposTrabajo.equals(item.getTipo_Trabajo());
    }
}