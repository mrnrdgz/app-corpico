package ar.com.corpico.appcorpico.orders.domain.filter;


import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaCuadrillaTipo implements Criteria<Tipo_Trabajo> {
    private final String tipotrabajo;

    public CriteriaCuadrillaTipo(String tipotrabajo) {
        this.tipotrabajo=tipotrabajo;
    }

    @Override
    public List<Tipo_Trabajo> match(List<Tipo_Trabajo> tipoTrabajos) {
        List<Tipo_Trabajo> filteredTipoTrabajos = new ArrayList<>();
        if (!tipotrabajo.equals("") || tipotrabajo !=null) {
            for (Tipo_Trabajo tipoTrabajo : tipoTrabajos) {
                if (tipoTrabajo.getTipoTrabajo().equals(tipotrabajo)) {
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
        return "select ... from order where state = " + tipotrabajo;
    }
}
