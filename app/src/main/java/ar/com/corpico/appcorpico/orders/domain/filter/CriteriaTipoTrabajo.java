package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaTipoTrabajo implements Criteria<Tipo_Trabajo> {
    private final String servicio;

    public CriteriaTipoTrabajo(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public List<Tipo_Trabajo> match(List<Tipo_Trabajo> tiposTrabajo) {
        List<Tipo_Trabajo> filteredTipoTrabajo = new ArrayList<>();
            for (Tipo_Trabajo tipoTrabajo : tiposTrabajo) {
                if (tipoTrabajo.getServicio().equals(servicio)) {
                    filteredTipoTrabajo.add(tipoTrabajo);
                }
            }

        return filteredTipoTrabajo;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
