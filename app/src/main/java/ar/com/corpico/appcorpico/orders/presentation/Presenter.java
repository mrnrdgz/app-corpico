package ar.com.corpico.appcorpico.orders.presentation;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface Presenter {
    public void loadOrderList(String estado, String tipo, String sector, String desde, String hasta);
    public void BtnMap();
    public void btnFilter();
}
