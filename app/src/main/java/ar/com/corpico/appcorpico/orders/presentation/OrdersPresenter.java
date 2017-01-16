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
    public void loadOrderList() {
        // TODO: Se reciben valores de cada filtro
        CriteriaState state = new CriteriaState("Pendiente");
        CriteriaSector sector = new CriteriaSector("Este");

        AndCriteria andCriteria=new AndCriteria(state,sector);

        mgetOrders.execute(new GetOrders.RequestValues(state),
                new UseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        // TODO: Ocultar indicador de progreso

                        // Se obtiene el valor de respuesta del caso de uso
                        GetOrders.ResponseValue responseValue = (GetOrders.ResponseValue) response;

                        // ¿La lista tiene uno o más elementos?
                        List<Order> orders = responseValue.getOrders();
                        if (orders.size() >= 1){
                            // Mostrar la lista en la vista
                            mOrdersView.showOrderList(orders);
                        }else {
                            // TODO: Mostrar estado vacío
                        }


                    }

                    @Override
                    public void onError(String error) {
                        // TODO : Ocultar indicador de progreso
                        mOrdersView.showOrderError(error);
                    }
                });
    }
}
