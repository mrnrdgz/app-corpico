package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetTypes extends UseCase<GetTypes.RequestValues, GetTypes.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetTypes(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.TiposRepositoryCallBack findCallback = new IOrdersRepository.TiposRepositoryCallBack() {
            @Override
            public void onSuccess(List<Tipo_Trabajo> tipos) {
                ResponseValue responseValue = new ResponseValue(tipos);
                callback.onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };


        mOrdersRepository.findType(findCallback, requestValues.getFilter());
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

        private final List<Tipo_Trabajo> tipoTrabajos;

        public ResponseValue(List<Tipo_Trabajo> tipoTrabajos) {
            this.tipoTrabajos = Preconditions.checkNotNull(tipoTrabajos, "La lista de ordenes no puede ser null");
        }

        public List<Tipo_Trabajo> getTipoTrabajos() {
            return tipoTrabajos;
        }
    }
}
