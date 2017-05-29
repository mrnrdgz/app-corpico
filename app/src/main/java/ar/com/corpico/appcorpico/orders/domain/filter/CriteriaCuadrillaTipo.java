package ar.com.corpico.appcorpico.orders.domain.filter;


import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaCuadrillaTipo implements Criteria<Tipo_Trabajo> {
    private final String cuadrilla;

    public CriteriaCuadrillaTipo(String cuadrilla) {
        this.cuadrilla=cuadrilla;
    }

    @Override
    public List<Tipo_Trabajo> match(List<Tipo_Trabajo> tipoTrabajos) {
        List<Tipo_Trabajo> filteredTipoTrabajos = new ArrayList<>();
        if (!cuadrilla.equals("") || cuadrilla !=null) {
            for (Tipo_Trabajo tipoTrabajo : tipoTrabajos) {
                if (tipoTrabajo.getTipoTrabajo().equals(cuadrilla)) {
                    filteredTipoTrabajos.add(tipoTrabajo);
                }
            }
        }else{
            filteredTipoTrabajos = tipoTrabajos;
        }
        return filteredTipoTrabajos;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + cuadrilla;
    }
}
