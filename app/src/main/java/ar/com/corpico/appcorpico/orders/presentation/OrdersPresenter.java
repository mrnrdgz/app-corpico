package ar.com.corpico.appcorpico.orders.presentation;

import android.widget.GridLayout;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.AndCriteria;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaCuadrillaTipo;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaSearch;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaZona;
import ar.com.corpico.appcorpico.orders.domain.filter.OrCriteria;
import ar.com.corpico.appcorpico.orders.domain.filter.OrderCriteriaFecha;
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
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoCuadrilla;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetOrders;
import ar.com.corpico.appcorpico.orders.domain.usecase.GetTipoTrabajo;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrador on 06/01/2017.
 */

public class OrdersPresenter implements Presenter {
    private AddOrdersState maddOrdersState;
    private GetOrders mgetOrders;
    private GetTipoCuadrilla mGetTipoCuadrilla;
    private GetTipoTrabajo mGetTipoTrabajo;
    private GetCuadrillaxTipo mgetCuadrillaxTipo;
    private View mOrdersView;
    private String mCuadrilla;

    //TODO: COMO MANEJO ACA EL CASO DE USO? SI ESTA MACHEADO EL CASO DE USO...TENGO QUE HACER UN CONSTRUCTOR POR CADA UNO?
    //O LO PUEDO PONER COMO VARIABLE AL TIPO?
    public OrdersPresenter(GetOrders getOrders, AddOrdersState addOrdersState, GetTipoCuadrilla getTipoCuadrilla, GetCuadrillaxTipo getCuadrillaxTipo, GetTipoTrabajo getTipoTrabajo, View ordersView) {
        maddOrdersState = Preconditions.checkNotNull(addOrdersState, "El presentador no puede ser null");
        mgetOrders = Preconditions.checkNotNull(getOrders, "El presentador no puede ser null");
        mGetTipoCuadrilla = Preconditions.checkNotNull(getTipoCuadrilla, "El presentador no puede ser null");
        mGetTipoTrabajo = Preconditions.checkNotNull(getTipoTrabajo, "El presentador no puede ser null");
        mgetCuadrillaxTipo = Preconditions.checkNotNull(getCuadrillaxTipo, "El presentador no puede ser null");
        mOrdersView = Preconditions.checkNotNull(ordersView, "La vista no puede ser null");
        //mOrdersView.setPresenter(this);
        /*mOrdersMapView = ordersMapView;
        mOrdersMapView.setPresenter(this);*/

    }

    @Override
    public void loadOrderList(String estado, List<String> tiposTrabajo, String zona, DateTime desde, DateTime hasta, String search, Boolean estadoActual) {
        // Se reciben valores de cada filtro
        //CriteriaState criteriaState = new CriteriaState(estado);
        /*CriteriaZona criteriaZona = new CriteriaZona(zona);


        // TODO: Crear OR criteria con los tipos de trabajo
        for (String tipoTrabajo: tiposTrabajo){
            CriteriaTipoTrabajo criteria = new CriteriaTipoTrabajo(tipoTrabajo);
            OrCriteria orCriteria = new OrCriteria(criteria, )
        }

        OrderCriteriaFecha criteriaFecha = new OrderCriteriaFecha(estado,desde,hasta,estadoActual);
        CriteriaSearch criteriaSearch = new CriteriaSearch(search);

        AndCriteria andCriteria = new AndCriteria(
                 new AndCriteria(criteriaZona,criteriaTipoTrabajo),
                 new AndCriteria(criteriaFecha,criteriaSearch)
                );*/

        /**
         * Aqui creo dos especificaciones compuestas y luego las compongo con and()
         */
        StateSpec stateSpec = new StateSpec(estado);
        ZoneSpec zoneSpec = new ZoneSpec(zona);
        SearchSpec searchSpec = new SearchSpec(search);
        FechaSpec fechaSpec = new FechaSpec(estado, desde, hasta, estadoActual);

        int i=0;
        CompositeSpec<Order> spec= new TipoTrabajoSpec(tiposTrabajo.get(i));
        do{
            if(i >= 1 && i <= tiposTrabajo.size()){
                spec = (CompositeSpec<Order>) spec.or(new TipoTrabajoSpec(tiposTrabajo.get(i)));
            }
            i=i+1;
        }while(i<tiposTrabajo.size() );


        Specification<Order> resultadoSpec = stateSpec.and(
                zoneSpec.and(
                        searchSpec.and(
                                fechaSpec.and(spec))));

        //Specification<Order> resultadoSpec = spec;

        /**
         * Luego puedes cambiar el parámetro del caso de uso y el repositorio para que
         * en la fuente de datos se reciba la especificación y pueda filtrarse con el método
         * Collections2.filter()
         */
        mOrdersView.showProgressIndicator(true);
        // Parámetro #1
        GetOrders.RequestValues requestValues = new GetOrders.RequestValues(resultadoSpec);

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

        AddOrdersState.RequestValues requestValues = new AddOrdersState.RequestValues(cuadrilla, listorder);
        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                AddOrdersState.ResponseValue responseValue = (AddOrdersState.ResponseValue) response;
                // Actualiza la lista luego de hacer el cambio
                //mOrdersView.setLoadOrderList(mCuadrilla);
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
    public void loadTipoCuadrilla(String servicio) {
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
                    mOrdersView.showTipoCuadrillaList(tipoCuadrilla);
                } else {
                    // Mostrar estado vacío
                    mOrdersView.showTipoCuadrillaList(tipoCuadrilla);
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
        mGetTipoCuadrilla.execute(requestValues, useCaseCallback);
    }

    @Override
    public void loadCuadrillasXTipo(String tipotrabajo) {
        CriteriaCuadrillaTipo criteriaCuadrillaTipo = new CriteriaCuadrillaTipo(tipotrabajo);

        GetCuadrillaxTipo.RequestValues requestValues = new GetCuadrillaxTipo.RequestValues(criteriaCuadrillaTipo);

        UseCase.UseCaseCallback useCaseCallback = new UseCase.UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                // Ocultar indicador de progreso
                //mOrdersView.showProgressIndicator(false);
                // Se obtiene el valor de respuesta del caso de uso
                GetCuadrillaxTipo.ResponseValue responseValue = (GetCuadrillaxTipo.ResponseValue) response;

                // ¿La lista tiene uno o más elementos?
                List<Tipo_Trabajo> tipocuadrilla = responseValue.getCuadrilaxTipo();
                if (tipocuadrilla.size() >= 1) {
                    // Mostrar la lista en la vista
                    // mOrdersView.showCuadrillaxTipoList(tipocuadrilla);
                } else {
                    // Mostrar estado vacío
                    // mOrdersView.showCuadrillaxTipoList(tipocuadrilla);
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
        mgetCuadrillaxTipo.execute(requestValues, useCaseCallback);
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
                    mOrdersView.setTipoTrabajo(tipoTrabajo);
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

}
