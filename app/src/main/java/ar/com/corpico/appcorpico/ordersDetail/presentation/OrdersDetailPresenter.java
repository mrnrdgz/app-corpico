package ar.com.corpico.appcorpico.ordersDetail.presentation;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersDetailPresenter implements Presenter{
    private AddOrdersState maddOrdersState;
    private ar.com.corpico.appcorpico.ordersDetail.presentation.View mOrdersView;
    private String mCuadrilla;

    public OrdersDetailPresenter(AddOrdersState addOrdersState, View ordersView) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
    }



    @Override
    public void asignarOrder(String cuadrilla, List<String> listorder, String observacion) {
        mCuadrilla = cuadrilla;

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(cuadrilla, listorder,observacion);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Cierra el detalle
                mOrdersView.closeDetail();
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showOrderError(error);
            }
        };

        maddOrdersState.execute(requestValues, useCaseCallback);
    }

}
