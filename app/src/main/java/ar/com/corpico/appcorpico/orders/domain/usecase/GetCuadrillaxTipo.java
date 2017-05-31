package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Cuadrilla;
import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetCuadrillaxTipo extends UseCase<GetCuadrillaxTipo.RequestValues, GetCuadrillaxTipo.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetCuadrillaxTipo(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.CuadrillaxTipoRepositoryCallBack findCallback = new IOrdersRepository.CuadrillaxTipoRepositoryCallBack() {
            @Override
            public void onSuccess(List<Tipo_Cuadrilla> tipocuadrilla) {
                ResponseValue responseValue = new ResponseValue(tipocuadrilla);
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

        private final List<Tipo_Cuadrilla> tipocuadrilla;

        public ResponseValue(List<Tipo_Cuadrilla> tipocuadrilla) {
            this.tipocuadrilla = Preconditions.checkNotNull(tipocuadrilla, "La lista de ordenes no puede ser null");
        }

        public List<Tipo_Cuadrilla> getCuadrilaxTipo() {
            return tipocuadrilla;
        }
    }
}
