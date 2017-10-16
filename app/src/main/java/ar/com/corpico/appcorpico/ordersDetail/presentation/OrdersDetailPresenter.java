package ar.com.corpico.appcorpico.ordersDetail.presentation;

import android.support.annotation.Nullable;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddTurno;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersDetailPresenter implements Presenter{
    private AddOrdersState maddOrdersState;
    private AddTurno mAddTurno;
    private ar.com.corpico.appcorpico.ordersDetail.presentation.View mOrdersView;

    public OrdersDetailPresenter(AddOrdersState addOrdersState, AddTurno AddTurno, View ordersView) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mAddTurno = Preconditions.checkNotNull(AddTurno, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
    }

    @Override
    public void asignarOrder(String cuadrilla, List<String> listorder, String observacion) {

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

    /**
     *
     * @param numero
     * @param turno Recibe el turno nevo seleccionado por el usuario.
     *              Puede ser null para eliminar el turno
     */
    @Override
    public void asignarTurno(String numero, @Nullable final DateTime turno) {
        final DateTime mTurno = turno;
        AddTurno.RequestValues requestValues = new AddTurno.RequestValues(numero, turno);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Se obtiene el valor de respuesta del caso de uso
                AddTurno.ResponseValue responseValue = (AddTurno.ResponseValue) response;
                if (mTurno != null){
                    mOrdersView.refreshTurno(mTurno.toString());
                }else{
                    mOrdersView.refreshTurno("");
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                //mOrdersView.showOrderError(error);
            }
        };
        mAddTurno.execute(requestValues, useCaseCallback);
    }
}
