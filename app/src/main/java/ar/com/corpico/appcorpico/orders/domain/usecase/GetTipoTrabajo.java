package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;


import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetTipoTrabajo extends UseCase<GetTipoTrabajo.RequestValues, GetTipoTrabajo.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetTipoTrabajo(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.TipoTrabajoRepositoryCallBack findCallback = new IOrdersRepository.TipoTrabajoRepositoryCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tipoTrabajo) {
                ResponseValue responseValue = new ResponseValue(tipoTrabajo);
                callback.onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };


        mOrdersRepository.findTipoTrabajo(findCallback, requestValues.getFilter());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private Criteria filter;

        public RequestValues() {
        }

        public RequestValues(Criteria filter){
            this.filter = filter;
        }

        public Criteria getFilter() {
            return filter;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Tipo_Trabajo> tipoTrabajo;

        public ResponseValue(List<Tipo_Trabajo> tipoTrabajo) {
            this.tipoTrabajo = Preconditions.checkNotNull(tipoTrabajo, "La lista de ordenes no puede ser null");
        }

        public List<Tipo_Trabajo> getTipoTrabajo() {
            return tipoTrabajo;
        }
    }
}
