package ar.com.corpico.appcorpico.orders.presentation;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.AndCriteria;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaSector;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersPresenter implements Presenter {

    private GetOrders mgetOrders;
    private View mOrdersView;

    public OrdersPresenter(GetOrders getOrders, View ordersView) {
        mgetOrders = Preconditions.checkNotNull(getOrders, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
    }

    @Override
    public void loadOrderList(String stado, String tip, String sec) {
        // TODO: Se reciben valores de cada filtro
        CriteriaState state = new CriteriaState(stado);
        CriteriaSector criteriaSector = new CriteriaSector(sec);
        CriteriaTipo criteriaTipo = new CriteriaTipo(tip);

        AndCriteria andCriteria = new AndCriteria(state, new AndCriteria(criteriaSector, criteriaTipo));

        mOrdersView.showProgressIndicator(true);
        // Parámetro #1
        GetOrders.RequestValues requestValues = new GetOrders.RequestValues(state);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback(){
            @Override
            public void onSuccess(Object response) {
                // TODO: Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetOrders.ResponseValue responseValue = (GetOrders.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<Order> orders = responseValue.getOrders();
                if (orders.size() >= 1) {
                    // Mostrar la lista en la vista
                    mOrdersView.showOrderList(orders);
                } else {
                    // TODO: Mostrar estado vacío
                    mOrdersView.showOrdesEmpty();
                }


            }

            @Override
            public void onError(String error) {
                // TODO : Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };


        mgetOrders.execute(requestValues, useCaseCallback);

    }
    @Override
    public void BtnMap() {
        mOrdersView.showOrderMsgMap();
    }
    @Override
    public void btnFilter() {
        mOrdersView.showOrderMsgFilter();
    }
}
