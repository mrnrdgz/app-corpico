package ar.com.corpico.appcorpico.orders.domain.usecase;

import ar.com.corpico.appcorpico.orders.domain.entity.Tipo_Trabajo;
import ar.com.corpico.appcorpico.orders.domain.entity.Zona;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;


import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;

/**
 * Created by Administrador on 07/01/2017.
 */

public class GetZonas extends UseCase<GetZonas.RequestValues, GetZonas.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetZonas(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.ZonaRepositoryCallBack findCallback = new IOrdersRepository.ZonaRepositoryCallBack() {
            @Override
            public void onSuccess(List<Zona> zona) {
                List<String> zonas = new ArrayList<>();
                for(Zona z: zona){
                    zonas.add(z.getZona());
                }
                ResponseValue responseValue = new ResponseValue(zonas);
                callback.onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };


        mOrdersRepository.findZona(findCallback, requestValues.getFilter());
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

        private final List<String> zona;

        public ResponseValue(List<String> zona) {
            this.zona = Preconditions.checkNotNull(zona, "La lista de ordenes no puede ser null");
        }

        public List<String> getZonas() {
            return zona;
        }
    }
}
