package ar.com.corpico.appcorpico.orders.domain.usecase;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
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
    public void execute(RequestValues requestValues, final UseCaseCallback callb) {

        mOrdersRepository.addOrderState(requestValues.getStateName(), (ArrayList<String>) requestValues.getOrderNumber());
        callb.onSuccess(new ResponseValue());
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private List<String> orderNumber;
        private String stateName;


        public RequestValues() {
        }

        public RequestValues(String stateName,List<String> orderNumber) {
            this.orderNumber = orderNumber;
            this.stateName = stateName;
            // Validar lógica
        }

        public List<String> getOrderNumber() {
            return orderNumber;
        }

        public String getStateName() {
            return stateName;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {


    }
}
