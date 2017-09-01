package ar.com.corpico.appcorpico.orders.domain.filter;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;

import static android.R.attr.order;

/**
 * Created by Administrador on 08/01/2017.
 */

public class CriteriaZona implements Criteria<Zona> {
    private final String zona;

    public CriteriaZona(String zona) {
        this.zona = zona;
    }

    @Override
    public List<Zona> match(List<Zona> zonas) {
        List<Zona> filteredZonas = new ArrayList<>();
        if (zona==null){
            filteredZonas = zonas;
            return filteredZonas;
        }
        if (!zona.equals("Todos") && zona!=null ){
            for (Zona zona : zonas) {
                if (zona.getZona().equals(zona)) {
                    filteredZonas.add(zona);
                }
            }
        }if (zona.equals("Todos")){
            filteredZonas = zonas;
        }
        return filteredZonas;
    }


    @Override
    public Object toSql() {
        return null;
    }
}
