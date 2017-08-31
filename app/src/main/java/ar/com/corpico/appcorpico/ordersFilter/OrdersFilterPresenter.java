package ar.com.corpico.appcorpico.ordersFilter;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import com.google.common.base.Preconditions;
import java.util.List;
import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetZonas;

/**
 * Created by sistemas on 30/08/2017.
 */

public class OrdersFilterPresenter implements Presenter{
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetZonas mGetZonas;
    private View mOrdersView;

    public OrdersFilterPresenter(GetTipoTrabajo getTipoTrabajo, GetZonas getZonas,View ordersView) {
        mGetTipoTrabajo = Preconditions.checkNotNull(getTipoTrabajo, "El presentador no puede ser null");
        mGetZonas = Preconditions.checkNotNull(getZonas, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
    }

    @Override
    public void loadTiposTrabajo(String tipoCuadrilla) {
        CriteriaTipoTrabajo criteriaTipoTrabajo = new CriteriaTipoTrabajo(tipoCuadrilla);

        GetTipoTrabajo.RequestValues requestValues = new GetTipoTrabajo.RequestValues(criteriaTipoTrabajo);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetTipoTrabajo.ResponseValue responseValue = (GetTipoTrabajo.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<String> tipoTrabajo = responseValue.getTipoTrabajo();
                if (tipoTrabajo.size() >= 1) {
                    // Mostrar la lista en la vista
                    mOrdersView.showTiposTrabajo(tipoTrabajo);

                } else {
                    // Mostrar estado vacío
                    mOrdersView.showOrdesEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };
        mGetTipoTrabajo.execute(requestValues, useCaseCallback);
    }

    @Override
    public void loadZonas() {
        GetZonas.RequestValues requestValues = new GetZonas.RequestValues();

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                GetZonas.ResponseValue responseValue = (GetZonas.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<String> zonas = responseValue.getZonas();
                if (zonas.size() >= 1) {
                    // Mostrar la lista en la vista
                    mOrdersView.showZonas(zonas);

                } else {
                    // Mostrar estado vacío
                    mOrdersView.showOrdesEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showOrderError(error);
            }
        };
        mGetZonas.execute(requestValues, useCaseCallback);
    }
}