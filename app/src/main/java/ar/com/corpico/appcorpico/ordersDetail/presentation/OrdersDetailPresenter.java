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

    //TODO: COMO MANEJO ACA EL CASO DE USO? SI ESTA MACHEADO EL CASO DE USO...TENGO QUE HACER UN CONSTRUCTOR POR CADA UNO?
    //O LO PUEDO PONER COMO VARIABLE AL TIPO?
    public OrdersDetailPresenter(AddOrdersState addOrdersState, View ordersView) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
        //mOrdersMapView = ordersMapView;
        //mOrdersMapView.setPresenter(this);

    }



    @Override
    public void asignarOrder(String cuadrilla, List<String> listorder) {
        mCuadrilla = cuadrilla;

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(cuadrilla, listorder);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Actualiza la lista luego de hacer el cambio
                //mOrdersView.setLoadOrderList(mCuadrilla);
                mOrdersView.closeDetail(mCuadrilla);

            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                //mOrdersView.showOrderError(error);
            }
        };

        maddOrdersState.execute(requestValues, useCaseCallback);
    }

}
