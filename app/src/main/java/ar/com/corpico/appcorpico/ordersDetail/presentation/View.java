package ar.com.corpico.appcorpico.ordersDetail.presentation;

import java.util.List;

/**
 * Created by Administrador on 06/01/2017.
 */

public interface View {
    void setPresenter(Presenter presenter);
    void closeDetail();
    void showOrderError(String error);
    void refreshTurno(String turno);
}
