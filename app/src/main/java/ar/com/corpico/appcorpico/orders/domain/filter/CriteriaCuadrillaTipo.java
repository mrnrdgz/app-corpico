package ar.com.corpico.appcorpico.orders.domain.filter;


import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaCuadrillaTipo implements Criteria<Tipo> {
    private final String cuadrilla;

    public CriteriaCuadrillaTipo(String cuadrilla) {
        this.cuadrilla=cuadrilla;
    }

    @Override
    public List<Tipo> match(List<Tipo> tipos) {
        List<Tipo> filteredTipos = new ArrayList<>();
        if (!cuadrilla.equals("") || cuadrilla !=null) {
            for (Tipo tipo : tipos) {
                if (tipo.getCuadrilla().equals(cuadrilla)) {
                    filteredTipos.add(tipo);
                }
            }
        }else{
            filteredTipos=tipos;
        }
        return filteredTipos;
    }

    @Override
    public Object toSql() {
        return "select ... from order where state = " + cuadrilla;
    }
}
