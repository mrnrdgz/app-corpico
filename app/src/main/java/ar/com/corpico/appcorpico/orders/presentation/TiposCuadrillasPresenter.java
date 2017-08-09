package ar.com.corpico.appcorpico.orders.presentation;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrador on 09/08/2017.
 */

public class TiposCuadrillasPresenter implements TiposCuadrillaToolbarMvp.Presenter {

    private TiposCuadrillaToolbarMvp.View mView;
    private GetTipoCuadrilla mGetTiposCuadrillas;

    public TiposCuadrillasPresenter(TiposCuadrillaToolbarMvp.View mView, GetTipoCuadrilla mGetTiposCuadrillas) {
        this.mView = checkNotNull(mView, "mView no puede ser null");
        this.mGetTiposCuadrillas = checkNotNull(mGetTiposCuadrillas,
                "mGetTiposCuadrillas no puede ser null");

        mView.setPresenter(this);
    }

    @Override
    public void loadTiposCuadrilla(String servicio) {
        CriteriaCuadrilla criteriaTipoCuadrilla = new CriteriaCuadrilla(servicio);

        GetTipoCuadrilla.RequestValues requestValues = new GetTipoCuadrilla.RequestValues(criteriaTipoCuadrilla);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetTipoCuadrilla.ResponseValue responseValue = (GetTipoCuadrilla.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<Tipo_Cuadrilla> tipoCuadrilla = responseValue.getTipoCuadrilla();
                if (tipoCuadrilla.size() >= 1) {
                    // Mostrar la lista en la vista
                    mView.showTiposCuadrilla(tipoCuadrilla);
                } else {
                    // Mostrar estado vacío
                    mView.showTiposCuadrilla(tipoCuadrilla);
                    mView.showOrdesEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mView.showProgressIndicator(false);
                mView.showOrderError(error);
            }
        };
        mGetTiposCuadrillas.execute(requestValues, useCaseCallback);

    }
}
