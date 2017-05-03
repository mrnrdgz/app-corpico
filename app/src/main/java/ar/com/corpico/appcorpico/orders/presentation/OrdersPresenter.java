package ar.com.corpico.appcorpico.orders.presentation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.AndCriteria;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaSearch;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaSector;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.OrderCriteriaFecha;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersPresenter implements Presenter {
    private AddOrdersState maddOrdersState;
    private GetOrders mgetOrders;
    private View mOrdersView;
    private String mCuadrilla;

    //TODO: COMO MANEJO ACA EL CASO DE USO? SI ESTA MACHEADO EL CASO DE USO...TENGO QUE HACER UN CONSTRUCTOR POR CADA UNO?
    //O LO PUEDO PONER COMO VARIABLE AL TIPO?
    public OrdersPresenter(GetOrders getOrders, AddOrdersState addOrdersState, View ordersView) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mgetOrders = Preconditions.checkNotNull(getOrders, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        mOrdersView.setPresenter(this);
    }

    @Override
    public void loadOrderList(String estado, String tipo, String sector, DateTime desde, DateTime hasta, String search,Boolean estadoActual) {
        // Se reciben valores de cada filtro
        //CriteriaState criteriaState = new CriteriaState(estado);
        CriteriaSector criteriaSector = new CriteriaSector(sector);
        CriteriaTipo criteriaTipo = new CriteriaTipo(tipo);
        OrderCriteriaFecha criteriaFecha = new OrderCriteriaFecha(estado,desde,hasta,estadoActual);
        CriteriaSearch criteriaSearch = new CriteriaSearch(search);

        AndCriteria andCriteria = new AndCriteria(
                 new AndCriteria(criteriaSector,criteriaTipo),
                 new AndCriteria(criteriaFecha,criteriaSearch));

        mOrdersView.showProgressIndicator(true);
        // Parámetro #1
        GetOrders.RequestValues requestValues = new GetOrders.RequestValues(andCriteria);

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback(){
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetOrders.ResponseValue responseValue = (GetOrders.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<Order> orders = responseValue.getOrders();
                if (orders.size() >= 1) {
                    // Mostrar la lista en la vista
                    mOrdersView.showOrderList(orders);
                } else {
                    // Mostrar estado vacío
                    mOrdersView.showOrderList(orders);
                    mOrdersView.showOrdesEmpty();
                }
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };


        mgetOrders.execute(requestValues, useCaseCallback);

    }

    @Override
    public void asignarOrder(String cuadrilla, List<String> listorder) {
        mCuadrilla = cuadrilla;

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(cuadrilla,listorder);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback(){
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Actualiza la lista luego de hacer el cambio
                mOrdersView.setLoadOrderList(mCuadrilla);
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };

        maddOrdersState.execute(requestValues,useCaseCallback);
    }
}
