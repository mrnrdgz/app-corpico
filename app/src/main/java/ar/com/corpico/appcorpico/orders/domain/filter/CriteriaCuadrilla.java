package ar.com.corpico.appcorpico.orders.domain.filter;
import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

import static ar.com.corpico.appcorpico.R.array.tipoTrabajos;

/**
 * Created by Administrador on 26/01/2017.
 */
public class CriteriaCuadrilla implements Criteria<Tipo_Cuadrilla> {
    private final String servicio;

    public CriteriaCuadrilla(String servicio) {
        this.servicio=servicio;
    }

    @Override
    public List<Tipo_Cuadrilla> match(List<Tipo_Cuadrilla> tipoCuadrillas) {
        List<Tipo_Cuadrilla> filteredTipoCuadrillas = new ArrayList<>();
        if (!servicio.equals("") || servicio !=null) {
            for (Tipo_Cuadrilla tipoCuadrilla : tipoCuadrillas) {
                if (tipoCuadrilla.getServicio().equals(servicio)) {
                    filteredTipoCuadrillas.add(tipoCuadrilla);
                }
            }
        }
        return filteredTipoCuadrillas;
    }

    @Override
    public Object toSql() {
        return "select ... from order where servicio = " + servicio;
    }
}
