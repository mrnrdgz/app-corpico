package ar.com.corpico.appcorpico.orders.presentation;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrillaTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.CompositeSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.FechaSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.SearchSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.StateSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.TipoTrabajoSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.ZoneSpec;
import ar.com.corpico.appcorpico.orders.domain.usecase.AddOrdersState;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetCuadrillaxTipo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetZonas;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersPresenter implements OrdersListMvp.Presenter {
    private AddOrdersState maddOrdersState;
    private GetOrders mgetOrders;
    //private GetTipoCuadrilla mGetTipoCuadrilla;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetZonas mGetZona;
    private GetCuadrillaxTipo mgetCuadrillaxTipo;

    private OrdersListMvp.View mOrdersView;
    private String mCuadrilla;

    /*public OrdersPresenter(GetOrders getOrders, AddOrdersState addOrdersState,
                           GetTipoCuadrilla getTipoCuadrilla, GetCuadrillaxTipo getCuadrillaxTipo,
                           GetTipoTrabajo getTipoTrabajo, GetZonas getZona,
                           OrdersListMvp.View ordersView) {*/
    public OrdersPresenter(GetOrders getOrders, OrdersListMvp.View ordersView) {

        //maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mgetOrders = Preconditions.checkNotNull(getOrders, "El presentador no puede ser null");
        //mGetTipoCuadrilla = Preconditions.checkNotNull(getTipoCuadrilla, "El presentador no puede ser null");
        //mGetTipoTrabajo = Preconditions.checkNotNull(getTipoTrabajo, "El presentador no puede ser null");
        //mGetZona = Preconditions.checkNotNull(getZona, "El presentador no puede ser null");
        //mgetCuadrillaxTipo = Preconditions.checkNotNull(getCuadrillaxTipo, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        //mOrdersView.setPresenter(this);
    }

    @Override
    public void loadOrders(String tipoCuadrilla, String estado, List<String> tiposTrabajo,
                           List<String> zona, DateTime desde,
                           DateTime hasta, String search,
                           Boolean estadoActual) {

        mOrdersView.showProgressIndicator(true);

        // Parámetro #1
        GetOrders.RequestValues requestValues = new GetOrders.RequestValues(
                tipoCuadrilla, estado, tiposTrabajo, zona, desde, hasta, search, estadoActual
        );

        // Parámetro #2
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
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
                    //mOrdersMapView.LoadOrderMap(orders);

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
    public void asignarOrder(String cuadrilla, List<String> listorder,String observacion) {
        mCuadrilla = cuadrilla;

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(cuadrilla, listorder,observacion);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Actualiza la lista luego de hacer el cambio

                // loadOrders();
            }

            @Override
            public void onError(String error) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                mOrdersView.showOrderError(error);
            }
        };

        maddOrdersState.execute(requestValues, useCaseCallback);
    }


    @Override
    public void setLoadTipoTrabajos(String cuadrilla) {
        CriteriaTipoTrabajo criteriaTipoTrabajo = new CriteriaTipoTrabajo(cuadrilla);

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
                    //mOrdersView.setTipoTrabajo(tipoTrabajo);

                } else {
                    // Mostrar estado vacío
                    //mOrdersView.setTipoTrabajo(tipoTrabajo);
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
        mGetTipoTrabajo.execute(requestValues, useCaseCallback);
    }

    @Override
    public void setLoadZonas() {
        //CriteriaZona criteriaZona = new CriteriaZona();

        //GetZonas.RequestValues requestValues = new GetZonas.RequestValues(criteriaZona);
        GetZonas.RequestValues requestValues = new GetZonas.RequestValues();

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetZonas.ResponseValue responseValue = (GetZonas.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<String> zonas = responseValue.getZonas();
                if (zonas.size() >= 1) {
                    // Mostrar la lista en la vista
                   // mOrdersView.setZonas(zonas);

                } else {
                    // Mostrar estado vacío
                    //mOrdersView.setTipoTrabajo(tipoTrabajo);
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
        mGetZona.execute(requestValues, useCaseCallback);
    }

}
