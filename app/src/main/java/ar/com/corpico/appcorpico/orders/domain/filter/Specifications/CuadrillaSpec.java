package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by User on 10/06/2017.
 */

public class CuadrillaSpec extends CompositeSpec<Order>{

    private String cuadrilla;

    public CuadrillaSpec(String estado) {
        this.cuadrilla = cuadrilla;
    }

    @Override
    public boolean isSatisfiedBy(Order item) {

        // Si el estado tiene el valor "Todos", entonces <code>item</code> satisface la especificación
        if (cuadrilla.equals("Todos")) {
            return true;
        }
        /*String mTipoTrabajo= item.getTipo_Trabajo();

        Tipo_Trabajo tipoTrabajo = tipoTrabajo.getTipoTrabajo(mTipoTrabajo);

        return cuadrilla.equals(tipoTrabajo.getTipoCuadrilla());*/
        return false;

    }
}