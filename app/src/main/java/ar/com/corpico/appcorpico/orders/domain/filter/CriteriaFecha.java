package ar.com.corpico.appcorpico.orders.domain.filter;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by sistemas on 30/01/2017.
 */

public class CriteriaFecha implements Criteria {
    private final DateTime mdesde;
    private final DateTime mhasta;

    public CriteriaFecha(DateTime desde, DateTime hasta) {
        this.mdesde = desde;
        this.mhasta = hasta;
    }

    @Override
    public List<Etapa> matchDate(List<Etapa> etapas) {
        List<Etapa> filteredEtapas = new ArrayList<>();
        if(mdesde != null && mhasta != null) {
            final DateTime d = new DateTime(mdesde);
            final DateTime h = new DateTime(mhasta);
            Interval interval = new Interval(d.withTimeAtStartOfDay(), h.withTimeAtStartOfDay());
            for (Etapa etapa : etapas) {
                if (interval.contains(new DateTime(etapa.getFecha()))) {
                    filteredEtapas.add(etapa);
                }
            }
        }else{
            filteredEtapas=etapas;
        }

        return filteredEtapas;
    }

    @Override
    public List<Order> match(List<Order> orders) {
        return null;
    }

    @Override
    public Object toSql() {
        return null;
    }
}
