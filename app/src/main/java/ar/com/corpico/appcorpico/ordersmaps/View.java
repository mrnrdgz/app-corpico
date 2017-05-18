package ar.com.corpico.appcorpico.ordersmaps;

import java.util.List;

import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.presentation.Presenter;

/**
 * Created by sistemas on 17/05/2017.
 */

public interface View {
    void LoadOrderMap(List<Order> listorder);
    void showOrderMap(List<Order> listorder);
    void setPresenter(Presenter presenter);
}
