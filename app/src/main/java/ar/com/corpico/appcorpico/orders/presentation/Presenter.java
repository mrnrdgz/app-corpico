package ar.com.corpico.appcorpico.orders.presentation;

import org.joda.time.DateTime;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    public void loadOrderList(String estado, String tipo, String sector, DateTime desde, DateTime hasta);

    public void BtnMap();
    public void btnFilter();
}
