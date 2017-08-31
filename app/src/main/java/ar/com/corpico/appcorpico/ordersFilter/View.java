package ar.com.corpico.appcorpico.ordersFilter;

import java.util.List;

/**
 * Created by sistemas on 30/08/2017.
 */

public interface View {
    void setPresenter(Presenter presenter);
    void showTiposTrabajo(List<String> listTiposTrabajo);
    void showZonas(List<String> listZonas);
    void showOrderError(String error);
    void showOrdesEmpty();
}
