package ar.com.corpico.appcorpico.orders.domain.filter.Specifications;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;

import ar.com.corpico.appcorpico.orders.domain.entity.Etapa;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;

import static android.R.attr.order;

/**
 * Created by Administrador on 14/06/2017.
 */

public class FechaSpec extends CompositeSpec<Order>  {
    private final String estado;
    private final DateTime desde;
    private final DateTime hasta;
    private final Boolean estadoActual;

    public FechaSpec(String estado, DateTime desde, DateTime hasta,Boolean estadoActual) {
        this.estado = estado;
        this.desde = desde;
        this.hasta = hasta;
        this.estadoActual = estadoActual;
    }

     @Override
    public boolean isSatisfiedBy(Order item) {
        /*if (desde != null && hasta != null) {
            Interval interval = new Interval(desde,hasta);
            if (!estadoActual){
                ArrayList<Etapa> etapas = item.getEtapas();
                // ¿El estado de la etapa y la fecha coinciden con los parámetros de entrada?
                for (Etapa etapa : etapas) {
                    if (estado.equals(etapa.getEstado())|| estado.equals("Todos")) {
                        DateTime fechaEtapa = new DateTime(etapa.getFecha());
                        if (interval.contains(fechaEtapa)) {
                           return true;
                        }
                    }
                }

             }else{
                Etapa currentEtapa = item.getCurrentEtapa(item.getEtapas());
                String estadoEtapa = currentEtapa.getEstado();
                DateTime fechaEtapa = new DateTime(currentEtapa.getFecha());
                if (estado.equals(estadoEtapa)|| estado.equals("Todos")) {
                    if (interval.contains(fechaEtapa)) {
                        return true;
                    }
                }
            }
        }
        if (desde == null && hasta == null && estadoActual) {
            Etapa currentEtapa = item.getCurrentEtapa(item.getEtapas());
            String estadoEtapa = currentEtapa.getEstado();
            if (estado.equals(estadoEtapa) || estado.equals("Todos")) {
                return true;
            }
        }
        return false;*/
         Etapa etapaActual = item.getCurrentEtapa(item.getEtapas());
         if (desde == null && hasta == null && estadoActual && etapaActual.getEstado().equals(estado) ){
             return true;
         }
        Interval interval = new Interval(desde,hasta);
        DateTime fechaEtapa = new DateTime(etapaActual.getFecha());
        return interval.contains(fechaEtapa) && estadoActual && etapaActual.getEstado().equals(estado) ;
    }
}
