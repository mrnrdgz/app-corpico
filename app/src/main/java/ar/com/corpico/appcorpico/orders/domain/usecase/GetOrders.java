package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import org.joda.time.DateTime;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;
import ar.com.corpico.appcorpico.orders.domain.filter.CriteriaTipoTrabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.CompositeSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.FechaSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.SearchSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.Specification;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.StateSpec;
import ar.com.corpico.appcorpico.orders.domain.filter.Specifications.TipoTrabajoSpec;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetOrders extends UseCase<GetOrders.RequestValues, GetOrders.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    private GetTipoTrabajo mGetTipoTrabajo;

    public GetOrders(IOrdersRepository ordersRepository, GetTipoTrabajo getTipoTrabajo) {
        this.mOrdersRepository = ordersRepository;
        mGetTipoTrabajo = Preconditions.checkNotNull(getTipoTrabajo);
        //TODO: XQ NECESITAMOS PASARLE A ESTE PASO DE USO LOS TIPOSDETRABAJO SI LO ESTAMOS SACANDO AHI?
        //mGetTipoTrabajo = getTipoTrabajo;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {
        // TODO: Obtener filtros del paquete de entrada y formar especificación total
        String estado = requestValues.getEstado();
        String tipoCuadrilla = requestValues.getTipoCuadrilla();
        //TODO: Ver la definicion de Zona
        DateTime desde = requestValues.getDesde();
        DateTime hasta = requestValues.getHasta();
        String search = requestValues.getSearch();
        Boolean estadoActual = requestValues.getEstadoActual();

        //Especificacion
        final StateSpec estadoSpec = new StateSpec(estado);
        final SearchSpec busquedaSpec = new SearchSpec(search);
        final FechaSpec fechasSpec = new FechaSpec(estado, desde, hasta, estadoActual);

        // TODO: Consultar tipos de trabajo
        CriteriaTipoTrabajo criteriaTipoTrabajo = new CriteriaTipoTrabajo(tipoCuadrilla);
        mGetTipoTrabajo.execute(new GetTipoTrabajo.RequestValues(criteriaTipoTrabajo), new UseCaseCallback() {
            @Override
            public void onSuccess(Object response) {
                GetTipoTrabajo.ResponseValue responseValue = (GetTipoTrabajo.ResponseValue) response;

                List<String> tiposTrabajo = responseValue.getTipoTrabajo();
                // TODO: Crear especificacion de tipos de trabajo

                int j=0;
                CompositeSpec<Order> tipoSpec= new TipoTrabajoSpec(tiposTrabajo.get(j));
                do{
                    if(j >= 1 && j <= tiposTrabajo.size()){
                        tipoSpec = (CompositeSpec<Order>) tipoSpec.or(new TipoTrabajoSpec(tiposTrabajo.get(j)));
                    }
                    j=j+1;
                }while(j<tiposTrabajo.size() );

                //Especificacion general
                final Specification<Order> resultadoSpec;
                resultadoSpec = estadoSpec.and(
                        busquedaSpec.and(
                                fechasSpec.and(tipoSpec)));
                // TODO: Cargar ordenes
                mOrdersRepository.findOrder(
                        new IOrdersRepository.OrdersRepositoryCallback() {
                            @Override
                            public void onSuccess(List<Order> orders) {
                                ResponseValue responseValue = new ResponseValue(orders);
                                callback.onSuccess(responseValue);
                            }

                            @Override
                            public void onError(String error) {
                                callback.onError(error);
                            }
                        },
                        resultadoSpec); // TODO : Cambiar por espeicficacion total
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Boolean estadoActual;
        private String search;
        private DateTime hasta;
        private DateTime desde;
        private List<String> zona;
        private List<String> tiposTrabajo;
        private String estado;
        private String tipoCuadrilla;

        //private Criteria filter;
        private Specification filter;

        public RequestValues() {
        }

        public RequestValues(Specification filter) {
            this.filter = filter;
        }

        public RequestValues(String tipoCuadrilla, String estado,
                             List<String> tiposTrabajo, List<String> zona,
                             DateTime desde, DateTime hasta,
                             String search, Boolean estadoActual) {
            this.tipoCuadrilla = tipoCuadrilla;
            this.estado = estado;
            this.tiposTrabajo = checkNotNull(tiposTrabajo, "tiposTrabajo no puede ser null");
            this.zona = zona;
            this.desde = desde;
            this.hasta = hasta;
            this.search = search;
            this.estadoActual = estadoActual;
        }

        public Specification getFilter() {
            return filter;
        }

        public Boolean getEstadoActual() {
            return estadoActual;
        }

        public String getSearch() {
            return search;
        }

        public DateTime getHasta() {
            return hasta;
        }

        public DateTime getDesde() {
            return desde;
        }

        public List<String> getZona() {
            return zona;
        }

        public List<String> getTiposTrabajo() {
            return tiposTrabajo;
        }

        public String getEstado() {
            return estado;
        }

        public String getTipoCuadrilla() {
            return tipoCuadrilla;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Order> orders;

        public ResponseValue(List<Order> orders) {
            this.orders = checkNotNull(orders, "La lista de ordenes no puede ser null");
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
}
