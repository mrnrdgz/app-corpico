package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo;

import static android.R.attr.order;
import static ar.com.corpico.appcorpico.R.id.cuadrilla;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaCuadrilla implements Criteria<Cuadrilla> {
    private final String servicio;

    public CriteriaCuadrilla(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public List<Cuadrilla> match(List<Cuadrilla> cuadrillas) {
        List<Cuadrilla> filteredCuadrillas = new ArrayList<>();

            for (Cuadrilla cuadrilla : cuadrillas) {
                if (cuadrilla.getServicio().equals(servicio)) {
                    filteredCuadrillas.add(cuadrilla);
                }
            }

        return filteredCuadrillas;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
