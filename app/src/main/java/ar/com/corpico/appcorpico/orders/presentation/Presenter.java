package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    public void loadOrderList(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search);
}
