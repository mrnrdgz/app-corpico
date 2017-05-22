package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.orders.domain.entity.Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;


import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetCuadrillas extends UseCase<GetCuadrillas.RequestValues, GetCuadrillas.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetCuadrillas(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.CuadrillasRepositoryCallBack findCallback = new IOrdersRepository.CuadrillasRepositoryCallBack() {
            @Override
            public void onSuccess(List<Cuadrilla> cuadrillas) {
                ResponseValue responseValue = new ResponseValue(cuadrillas);
                callback.onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };


        mOrdersRepository.findCuadrilla(findCallback, requestValues.getFilter());
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

        private final List<Cuadrilla> cuadrilla;

        public ResponseValue(List<Cuadrilla> cuadrilla) {
            this.cuadrilla = Preconditions.checkNotNull(cuadrilla, "La lista de ordenes no puede ser null");
        }

        public List<Cuadrilla> getCuadrilla() {
            return cuadrilla;
        }
    }
}
