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

public class GetOrders extends UseCase<GetOrders.RequestValues, GetOrders.ResponseValue> {
    private IOrdersRepository mOrdersRepository;

    public GetOrders(IOrdersRepository ordersRepository) {
        this.mOrdersRepository = ordersRepository;
    }

    @Override
    public void execute(RequestValues requestValues, final UseCaseCallback callback) {

        IOrdersRepository.OrdersRepositoryCallback findCallback = new IOrdersRepository.OrdersRepositoryCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                ResponseValue responseValue = new ResponseValue(orders);
                callback.onSuccess(responseValue);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        };


        mOrdersRepository.findOrder(findCallback, requestValues.getFilter());
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

        private final List<Order> orders;

        public ResponseValue(List<Order> orders) {
            this.orders = Preconditions.checkNotNull(orders, "La lista de ordenes no puede ser null");
        }

        public List<Order> getOrders() {
            return orders;
        }
    }
}
