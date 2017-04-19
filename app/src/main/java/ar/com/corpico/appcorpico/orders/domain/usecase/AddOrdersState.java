package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.List;

import ar.com.corpico.appcorpico.UseCase;
import ar.com.corpico.appcorpico.orders.data.IOrdersRepository;
import ar.com.corpico.appcorpico.orders.domain.entity.Order;
import ar.com.corpico.appcorpico.orders.domain.filter.Criteria;

/**
 * Created by Administrador on 07/01/2017.
 */

public class AddOrdersState extends UseCase<AddOrdersState.RequestValues, AddOrdersState.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public AddOrdersState(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        mOrdersRepository.addOrderState(requestValues.getOrderNumber(), requestValues.getStateName());
        callback.onSuccess(new ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private String orderNumber;
        private String stateName;


        public RequestValues() {
        }

        public RequestValues(String orderNumber, String stateName) {
            this.orderNumber = orderNumber;
            this.stateName = stateName;
            // Validar lógica
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public String getStateName() {
            return stateName;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}
