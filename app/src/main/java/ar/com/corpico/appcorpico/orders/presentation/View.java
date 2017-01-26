package ar.com.corpico.appcorpico.orders.presentation;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    public void showOrderList(List<Order> listorder);
    public void showOrderError(String error);
    public void setPresenter(Presenter presenter);
    public void showOrderMsgMap();
    public void clickbtnMap();
    public void clickbtnFilter();
    public void showOrderMsgFilter();
}
